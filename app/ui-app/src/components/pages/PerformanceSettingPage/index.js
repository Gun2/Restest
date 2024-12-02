import React, {useCallback, useEffect, useState} from 'react';
import styled from "styled-components";
import PerformanceSettingTemplate from "../../templates/PerformanceSettingTemplate";
import LabelInputText from "../../molecules/LabelInputText";
import JobList from "../../organisms/JobList";
import {useDispatch, useSelector} from "react-redux";
import {jobReadAllThunk} from "../../../modules/job";
import {readMaxInstanceThunk, readMaxJobThunk} from "../../../modules/performanceSetting";
import Button from "../../atoms/Button";
import ValidationMessage from "../../atoms/ValidationMessage";
import {createPerformanceThunk} from "../../../modules/performance";

const Box = styled.div`
`;
const PerformanceSettingPage = ({}) => {
    const dispatch = useDispatch();
    const jobList = useSelector(store => store.job);
    const [checkedJobList, setCheckedJobList] = useState([]);
    const maxJob = useSelector(store => store.performanceSetting.maxJob);
    const maxInstance = useSelector(store => store.performanceSetting.maxInstance);
    const [instanceValue, setInstanceValue] = useState(0);
    const validationGroup = 'performance';
    const onClickRegistryBtn = useCallback(() => {
        dispatch(createPerformanceThunk({
            param: {
                instance: instanceValue,
                jobIdList: checkedJobList
            },
            validationGroup: validationGroup
        }));
    }, [instanceValue, checkedJobList]);
    useEffect(() => {
        if (jobList.length == 0) {
            dispatch(jobReadAllThunk({}));
        }
        dispatch(readMaxJobThunk({}));
        dispatch(readMaxInstanceThunk({}));
    }, []);
    useEffect(() => {
        setInstanceValue(maxInstance > 1 ? 1 : 0);
    }, [maxInstance]);
    useEffect(() => {
        //체크된 항목이 존재하는지 확인

        const jobIdSet = new Set(jobList.map( d => d.id));
        setCheckedJobList(checkedJobList.filter(checkedId => jobIdSet.has(checkedId)));
    }, [jobList]);
    const instanceValueOnChange = useCallback((value) => {
        var value = value.replaceAll(/[^0-9]/g, '');
        if (value > maxInstance) {
            value = maxInstance;
        }
        setInstanceValue(value);
    }, [instanceValue]);
    return (
        <Box>
            <PerformanceSettingTemplate
                instanceArea={
                    <ValidationMessage field={"instance"} validationGroup={validationGroup}>
                        <LabelInputText labelText={"Instance"} inputValue={instanceValue}
                                        onChange={instanceValueOnChange}/>
                    </ValidationMessage>
                }
                jobCountArea={
                    <ValidationMessage field={"jobIdList"} validationGroup={validationGroup}>
                        {`${checkedJobList.length}/${maxJob}`}
                    </ValidationMessage>
                }
                jobListArea={
                    <JobList
                        data={jobList}
                        checkedIdSet={new Set(checkedJobList)}
                        onCheckCallback={(set) => {
                            if (set.size <= maxJob) {
                                setCheckedJobList([...set]);
                            }
                        }}
                        readonly={true}
                    />
                }
                controlArea={
                    <Button form={"primary"}
                            onClick={onClickRegistryBtn}
                    >시작</Button>
                }
            />
        </Box>
    );
};

export default PerformanceSettingPage;