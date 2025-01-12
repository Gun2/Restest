import React, {useCallback, useReducer} from 'react';
import styled from "styled-components";
import InputText from "../../atoms/InputText";
import Button from "../../atoms/Button";
import axios from "axios";
import JobList from "../../organisms/JobList";
import {useDispatch, useSelector} from "react-redux";
import {useCreateMutation, useDeleteByIdMutation, useUpdateMutation} from "modules/schedule";
import ValidationMessage from "../../atoms/ValidationMessage";
import {useDetectValidationError} from "hooks/useDetectValidationError";

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

const initData = {
    title: "",
    delay: "1000",
    jobList: [],
}

const getJobList = () => {
    return axios.get('/api/v1/jobs');
}

const reducer = (state, {type, name, value}) => {
    switch (type) {
        case "INIT":
            return {...initData};
        case "CHANGE":
            return {
                ...state,
                [name]: value
            }
        case "JOBS/CHANGE":
            return {
                ...state,
                jobIdList: [...value]
            }
    }
}

function ScheduleContent({
                            data = initData,
                             onSaveCallback = () => {
                             },
                             onChangeCallback = () => {
                             },
                             onDeleteCallback = () => {
                             },
                             onCancelCallback = () => {
                             },
                             showDeleteBtn,
                             showSaveBtn,
                             showCancelBtn,
                             showTestBtn,
                         }) {
    const jobData = useSelector(store => store.job);
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
            detectValidationError.bindResponse(e?.data)
        }
    }, [scheduleData]);

    const onCancel = useCallback(() => {
        onCancelCallback();
    });

    const onChange = useCallback((action) => {
        scheduleDispatch(action);
    }, [scheduleData]);

    const onDelete = useCallback(() => {
        deleteScheduleById(scheduleData.id).then(data => {
            onDeleteCallback();
        });
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
                <JobList
                    data={jobData}
                    checkedIdSet={new Set(scheduleData.jobIdList)}
                    onCheckCallback={(set) => {
                        scheduleDispatch({
                            type:"JOBS/CHANGE",
                            value : [...set]
                        })
                    }}
                    readonly={true}
                />
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