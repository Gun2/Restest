import React, {useCallback, useReducer} from 'react';
import styled from "styled-components";
import InputText from "../../atoms/InputText";
import Button from "../../atoms/Button";
import axios from "axios";
import JobList from "../../organisms/JobList";
import {useCreateMutation, useDeleteByIdMutation, useUpdateMutation} from "modules/schedule";
import ValidationMessage from "../../atoms/ValidationMessage";
import {useDetectValidationError} from "hooks/useDetectValidationError";
import {Schedule} from "@_types/schedule.types";
import {isFetchBaseQueryError} from "types/rtkQuery.types";

const Box = styled.div`
    display: flex;
    flex-direction : column;
    gap:5px;
    padding: 10px;
`;

const Bottom = styled.div`
    ${({theme}) => theme.flex.endCenter};
    gap : 5px;
`

const ColContain = styled.div`
    display: flex;
    flex-direction : column;
    gap:5px;
`

const JobBox = styled.div`
    background-color: ${({theme}) => theme.palette.secondary}
`

const initData : ScheduleForm = {
    title: "",
    delay: 1000,
    jobList: [],
    jobIdList: [],
    run: false,
}

const getJobList = () => {
    return axios.get('/api/v1/jobs');
}
type Action = {
    type: "INIT" | "CHANGE";
    name: string;
    value: any;
} | {
    type: "JOBS/CHANGE";
    value: Schedule["jobIdList"];
}
const reducer = (state: ScheduleForm, action: Action) => {
    switch (action.type) {
        case "INIT":
            return {...initData};
        case "CHANGE":
            return {
                ...state,
                [action.name]: action.value
            }
        case "JOBS/CHANGE":
            return {
                ...state,
                jobIdList: [...action.value]
            }
    }
}

type ScheduleForm = Omit<Schedule, "id" | "createdAt" | "updatedAt"> & {
    id ?: Schedule["id"];
}

type ScheduleContentProps = {
    //초기값
    data?: ScheduleForm;
    //저장 진행 후 callback
    onSaveCallback?: () => void;
    //삭제 진행 후 callback
    onDeleteCallback?: () => void;
    //취소 진행 후 callback
    onCancelCallback?: () => void;
    //삭제 버튼 표시 여부
    showDeleteBtn?: boolean;
    //저장 버튼 표시 여부
    showSaveBtn?: boolean;
    //취소 버튼 표시 여부
    showCancelBtn?: boolean;


}
//스케줄 내용
function ScheduleContent(
    {
        data = initData,
        onSaveCallback = () => {
        },
        onDeleteCallback = () => {
        },
        onCancelCallback = () => {
        },
        showDeleteBtn,
        showSaveBtn,
        showCancelBtn,
    }: ScheduleContentProps
    ) {
    const [scheduleData, scheduleDispatch] = useReducer(reducer, data);
    const detectValidationError = useDetectValidationError({validationGroup: "scheduleValidation"});
    const [updateSchedule] = useUpdateMutation();
    const [createSchedule] = useCreateMutation();
    const [deleteScheduleById] = useDeleteByIdMutation();
    const onSave = useCallback(async () => {
        try {
            if (scheduleData.id) {
                await updateSchedule(scheduleData).unwrap();
            } else {
                await createSchedule(scheduleData).unwrap();
            }
            detectValidationError.bindResponse(undefined);
            onSaveCallback();
        }catch (e){
            if (isFetchBaseQueryError(e)){
                detectValidationError.bindResponse(e?.data);
            }else {
                console.error(e);
            }
        }
    }, [scheduleData]);

    const onCancel = useCallback(() => {
        onCancelCallback();
    }, [onCancelCallback]);

    const onChange = useCallback((action: Action) => {
        scheduleDispatch(action);
    }, [scheduleData, scheduleDispatch]);

    const onDelete = useCallback(() => {
        if (scheduleData.id){
            deleteScheduleById(scheduleData.id).then(data => {
                onDeleteCallback();
            });
        }
    }, [scheduleData])
    return (
        <Box>
            <ColContain>
                <ValidationMessage field={"title"} {...detectValidationError}>
                    <InputText onChange={
                        (value) => onChange({
                            type: "CHANGE",
                            name: "title",
                            value: value
                        })}
                               value={scheduleData.title}
                               widthFull
                               placeholder={"스케줄 제목을 입력하세요."}
                    />
                </ValidationMessage>
                <ValidationMessage field={"delay"} {...detectValidationError}>
                    <InputText onChange={
                        (value) => onChange({
                            type: "CHANGE",
                            name: "delay",
                            value: value
                        })}
                               value={scheduleData.delay}
                               placeholder={"동작 주기를 입력하세요, (ms)"}/>
                </ValidationMessage>
            </ColContain>
            <JobBox>
                {
                    <JobList
                        checkedIdSet={new Set(scheduleData.jobIdList)}
                        onCheckCallback={(set) => {
                            scheduleDispatch({
                                type:"JOBS/CHANGE",
                                value : Array.from(set)
                            })
                        }}
                        readonly={true}
                    />
                }

            </JobBox>
            <Bottom>
                {
                    showSaveBtn &&
                    <Button form={"primary"} onClick={onSave}>저장</Button>
                }
                {
                    showCancelBtn &&
                    <Button form={"warning"} onClick={onCancel}>취소</Button>
                }
                {
                    showDeleteBtn &&
                    <Button form={"danger"} onClick={onDelete}>삭제</Button>
                }
            </Bottom>

        </Box>
    );
}

export default ScheduleContent;