import React, {useCallback, useReducer} from 'react';
import styled from "styled-components";
import InputText from "../../atoms/InputText";
import Select from "../../atoms/Select";
import Button from "../../atoms/Button";
import IncreaseTable from "../../atoms/IncreaseTable";
import TabBar from "../TabBar";
import axios from "axios";
import ValidationMessage from "../../atoms/ValidationMessage";
import {jobCreateThunk, jobDeleteThunk, jobReadAllThunk, jobUpdateThunk} from "../../../modules/job";
import {useDispatch, useSelector} from "react-redux";
import store from "../../../store";

const Box = styled.div`
    display: flex;
    flex-direction : column;
    gap:5px;
    padding 10px;
`;

const Bottom = styled.div`
    ${({theme}) => theme.flex.endCenter};
    gap : 5px;
`

const UrlContain = styled.div`
    display: flex;
    gap:5px;
`

const initData = {
    title: "",
    method: "GET",
    url: "",
    jobHeaderList: [],
    jobBodyList: [],
}

const jobReducer = (state, {type, name, value}) => {
    switch (type) {
        case "INIT":
            return {...initData};
        case "CHANGE":
            return {
                ...state,
                [name]: value
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

function JobContent({
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
                        readonly
                    }) {
    const successCallback = () => {
        onSaveCallback();
        //dispatch(jobReadAllThunk({}));
    }
    const [jobData, jobDispatch] = useReducer(jobReducer, data);
    const validationGroup = `jobValidation-${jobData.id}`;
    const dispatch = useDispatch();
    const onSave = useCallback(() => {
        if (jobData.id) {
            dispatch(jobUpdateThunk({
                param : jobData,
                validationGroup : validationGroup,
                successCallback : () => {successCallback()},
            }));
        } else {
            dispatch(jobCreateThunk({
                param : jobData,
                validationGroup : validationGroup,
                successCallback : () => {successCallback()}
            }));
        }
    }, [jobData]);

    const onCancel = useCallback(() => {
        onCancelCallback();
    });

    const onChange = useCallback((action) => {
        jobDispatch(action);
        onChangeCallback(action);
    }, [jobData]);

    const onDelete = useCallback(() => {
        dispatch(jobDeleteThunk({
            param: jobData.id,
            validationGroup : validationGroup,
            successCallback : () => {onDeleteCallback()}
        }));
    }, [jobData])
    return (
        <Box>
            {
                !readonly &&
                <ValidationMessage field={"title"} validationGroup={validationGroup}>
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
                <ValidationMessage field={"method"} validationGroup={validationGroup}>
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
                <ValidationMessage style={{flex:"1"}} field={"url"} validationGroup={validationGroup}>
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
                {showTestBtn && <Button form="primary">테스트</Button>}
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
                                       onChange={(header) => onChange({
                                           type: "HEADERS/CHANGE",
                                           value: header
                                       })}
                                       readonly={readonly}
                        />
                    )
                }, {
                    key: "Body",
                    content: (
                        <IncreaseTable cols={[
                            {
                                key: "usable",
                                name: "",
                                type: "checkbox",
                                increaseIgnore : true,
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
                        >
                        </IncreaseTable>
                    )
                }
            ]}>
            </TabBar>
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