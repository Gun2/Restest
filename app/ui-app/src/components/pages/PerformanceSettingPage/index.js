import React, {useCallback, useEffect, useState} from 'react';
import styled from "styled-components";
import PerformanceSettingTemplate from "components/templates/PerformanceSettingTemplate";
import LabelInputText from "components/molecules/LabelInputText";
import JobList from "../../organisms/JobList";
import {useDispatch, useSelector} from "react-redux";
import {useReadAllQuery} from "modules/job";
import {
    readMaxInstanceThunk,
    readMaxJobThunk,
    useReadMaxInstanceQuery,
    useReadMaxJobQuery
} from "modules/performanceSetting";
import Button from "components/atoms/Button";
import ValidationMessage from "../../atoms/ValidationMessage";
import {create, useCreateMutation} from "modules/performance";

const Box = styled.div`
`;
const PerformanceSettingPage = ({}) => {
    const dispatch = useDispatch();
    const [checkedJobList, setCheckedJobList] = useState([]);
    const {data : { data : maxInstance} = {data : 0}} = useReadMaxInstanceQuery();
    const [instanceValue, setInstanceValue] = useState(0);
    const validationGroup = 'performance';
    const [createPerformance] = useCreateMutation();
    const {data: {data : maxJob} = {data : 0}} = useReadMaxJobQuery();
    const {jobList = []} = useReadAllQuery();
    const onClickRegistryBtn = useCallback(() => {
        createPerformance({
            instance: instanceValue,
            jobIdList: checkedJobList,
        }).then(r => {
            dispatch(create(r?.data?.data));
        });
    }, [instanceValue, checkedJobList]);
    /*useEffect(() => {
        setInstanceValue(maxInstance > 1 ? 1 : 0);
    }, [maxInstance]);*/
    /*useEffect(() => {
        //체크된 항목이 존재하는지 확인
        const jobIdSet = new Set(jobList.map( d => d.id));
        setCheckedJobList(checkedJobList.filter(checkedId => jobIdSet.has(checkedId)));
    }, [jobList]);*/
    const instanceValueOnChange = useCallback((value) => {
        var value = value.replaceAll(/[^0-9]/g, '');
        if (value > maxInstance) {
            value = maxInstance;
        }
        setInstanceValue(value);
    }, [instanceValue]);
    if (maxInstance == undefined || maxJob == undefined) {
        return null;
    }
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