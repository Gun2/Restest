import React, {useCallback, useReducer} from 'react';
import styled from "styled-components";
import InputText from "../../../../components/atoms/InputText";
import Select from "../../../../components/atoms/Select";
import Button from "../../../../components/atoms/Button";
import IncreaseTable from "../../../../components/atoms/IncreaseTable";
import TabBar from "../../../../components/molecules/TabBar";
import ValidationMessage from "../../../../components/atoms/ValidationMessage";
import {useCreateMutation, useDeleteByIdMutation, useUpdateMutation} from "modules/job";
import {useDetectValidationError} from "hooks/useDetectValidationError";
import {Job, JobCreateOrUpdateRequest} from "types/job.types";
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

const UrlContain = styled.div`
    display: flex;
    gap:5px;
`

type JobForm = JobCreateOrUpdateRequest;

const initData: JobForm = {
    title: "",
    method: "GET",
    url: "",
    jobHeaderList: [],
    jobBodyList: [],
    jobParamList: [],
}
type Action = {
    type: "INIT" | "CHANGE";
    name: string,
    value: any,
} | {
    type: "HEADERS/CHANGE" | "BODY/CHANGE";
    value: any,
}
const jobReducer = (state: JobForm, action: Action) => {
    const {type, value} = action;
    switch (type) {
        case "INIT":
            return {...initData};
        case "CHANGE":
            return {
                ...state,
                [action.name]: value
            }
        case "HEADERS/CHANGE":
            return {
                ...state,
                ["jobHeaderList"]: [...value]
            }
        case "BODY/CHANGE":
            return {
                ...state,
                ["jobBodyList"]: [...value]
            }
    }
}
type JobContentProps = {
    //초기 job 입력값 정보
    data?: JobForm;
    //저장 버튼 클릭 시 callback
    onSaveCallback?: () => void;
    //정보 변경 시 callback
    onChangeCallback?: (action: Action) => void;
    //삭제 버튼 클릭 시 callback
    onDeleteCallback?: () => void;
    //취소 버튼 클릭 시 callback
    onCancelCallback?: () => void;
    //삭제 버튼 표시 유무
    showDeleteBtn?: boolean;
    //저장 버튼 표시 유무
    showSaveBtn?: boolean;
    //취소 버튼 표시 유무
    showCancelBtn?: boolean;
    //읽기 모드
    readonly?: boolean;
}
function JobContent(
    {
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
        readonly
    }: JobContentProps) {
    const [createJob, {data : createJobData}] = useCreateMutation();
    const [updateJob, {data: updateJobData, error}] = useUpdateMutation();
    const [deleteJobById] = useDeleteByIdMutation();
    const [jobData, jobDispatch] = useReducer(jobReducer, data);
    const detectValidationError = useDetectValidationError({validationGroup: "jobValidation"});
    const onSave = useCallback(async () => {
        try {
            if (jobData.id) {
                await updateJob(jobData).unwrap();
            } else {
                await createJob(jobData).unwrap();
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
    }, [jobData]);

    const onCancel = useCallback(() => {
        onCancelCallback();
    }, [onCancelCallback]);

    const onChange = useCallback((action: Action) => {
        jobDispatch(action);
        onChangeCallback(action);
    }, [jobData]);

    const onDelete = useCallback(() => {
        if (jobData.id){
            deleteJobById(jobData.id);
        }
    }, [jobData])
    return (
        <Box>
            {
                !readonly &&
                <ValidationMessage field={"title"} {...detectValidationError}>
                    <InputText onChange={
                        (value) => onChange({
                            type: "CHANGE",
                            name: "title",
                            value: value
                        })}
                               value={jobData.title}
                               widthFull placeholder={"업무 제목을 입력하세요."}
                    />
                </ValidationMessage>
            }
            <UrlContain>
                <ValidationMessage field={"method"} {...detectValidationError}>
                    <Select value={jobData.method}
                            onChange={
                                (value) => onChange({
                                    type: "CHANGE",
                                    name: "method",
                                    value: value
                                })
                            }
                            readonly={readonly}>
                        <option value={"GET"}>GET</option>
                        <option value={"POST"}>POST</option>
                        <option value={"PUT"}>PUT</option>
                        <option value={"DELETE"}>DELETE</option>
                    </Select>
                </ValidationMessage>
                <ValidationMessage style={{flex:"1"}} field={"url"}  {...detectValidationError}>
                    <InputText onChange={
                        (value) => onChange({
                            type: "CHANGE",
                            name: "url",
                            value: value
                        })}
                               value={jobData.url}
                               placeholder={"URL을 입력하세요"}
                               readonly={readonly}
                    />
                </ValidationMessage>
            </UrlContain>
            <TabBar tabData={[
                {
                    key: "Headers",
                    content: (
                        <IncreaseTable cols={[
                            {
                                key: "usable",
                                name: "",
                                type: "checkbox",
                                increaseIgnore : true,
                                //default: true,
                            }, {
                                key: "keyName",
                                name: "key",
                                type: "input"
                            }, {
                                key: "value",
                                name: "value",
                                type: "input"
                            }, {
                                key: "description",
                                name: "description",
                                type: "input"
                            },
                        ]}
                                       data={jobData.jobHeaderList}
                                       onChange={(header: any) => onChange({
                                           type: "HEADERS/CHANGE",
                                           value: header
                                       })}
                                       readonly={readonly}
                        />
                    )
                }, {
                    key: "Body",
                    content: (
                        <IncreaseTable
                            cols={[
                                {
                                    key: "usable",
                                    name: "",
                                    type: "checkbox",
                                    increaseIgnore: true,
                                }, {
                                    key: "body",
                                    name: "Body",
                                    type: "textarea"
                                }
                            ]}
                            data={jobData.jobBodyList}
                            onChange={(header) => onChange({
                                type: "BODY/CHANGE",
                                value: header
                            })}
                            readonly={readonly}
                        />
                    )
                }
            ]}/>
            <Bottom>
                {
                    !readonly &&
                    showSaveBtn &&
                    <Button form={"primary"} onClick={onSave}>저장</Button>
                }
                {
                    !readonly &&
                    showCancelBtn &&
                    <Button form={"warning"} onClick={onCancel}>취소</Button>
                }
                {
                    !readonly &&
                    showDeleteBtn &&
                    <Button form={"danger"} onClick={onDelete}>삭제</Button>
                }
            </Bottom>

        </Box>
    );
}

export default JobContent;