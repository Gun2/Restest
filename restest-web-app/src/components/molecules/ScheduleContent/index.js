import React, {useCallback, useEffect, useReducer, useState} from 'react';
import styled from "styled-components";
import InputText from "../../atoms/InputText";
import Select from "../../atoms/Select";
import Button from "../../atoms/Button";
import IncreaseTable from "../../atoms/IncreaseTable";
import TabBar from "../TabBar";
import axios from "axios";
import JobList from "../../organisms/JobList";
import {useDispatch, useSelector} from "react-redux";
import {scheduleCreateThunk, scheduleUpdateThunk} from "../../../modules/schedule";
import ValidationMessage from "../../atoms/ValidationMessage";

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
    const dispatch = useDispatch();
    const validationGroup = `scheduleValidation-${data.id}`;
    const onSave = useCallback(() => {
        if (scheduleData.id) {
            dispatch(scheduleUpdateThunk({
                param : scheduleData,
                validationGroup : validationGroup,
                successCallback : onSaveCallback,
            }));

        } else {
            dispatch(scheduleCreateThunk({
                param : scheduleData,
                validationGroup : validationGroup,
                successCallback : onSaveCallback,
            }));
        }
    }, [scheduleData]);

    const onCancel = useCallback(() => {
        onCancelCallback();
    });

    const onChange = useCallback((action) => {
        scheduleDispatch(action);
    }, [scheduleData]);

    const onDelete = useCallback(() => {
        axios.delete(`/api/v1/schedules/${scheduleData.id}`).then(
            () => {
                onDeleteCallback()
            }
        )
    }, [scheduleData])
    return (
        <Box>
            <ColContain>
                <ValidationMessage field={"title"} validationGroup={validationGroup}>
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
                <ValidationMessage field={"delay"} validationGroup={validationGroup}>
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