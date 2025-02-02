import React, {useCallback, useState} from 'react';
import styled from "styled-components";
import PerformanceSettingTemplate from "../../templates/PerformanceSettingTemplate";
import LabelInputText from "../../molecules/LabelInputText";
import JobList from "../../organisms/JobList";
import {useDispatch} from "react-redux";
import {useReadMaxInstanceQuery, useReadMaxJobQuery} from "modules/performanceSetting";
import Button from "../../atoms/Button";
import ValidationMessage from "../../atoms/ValidationMessage";
import {create, useCreateMutation} from "modules/performance";
import {useDetectValidationError} from "hooks/useDetectValidationError";
import {isFetchBaseQueryError} from "types/rtkQuery.types";

const Box = styled.div`
`;
const PerformanceSettingPage = ({}) => {
    const dispatch = useDispatch();
    const [checkedJobList, setCheckedJobList] = useState<number[]>([]);
    const {data : { data : maxInstance} = {data : 0}} = useReadMaxInstanceQuery();
    const [instanceValue, setInstanceValue] = useState(0);
    const detectValidationError = useDetectValidationError({validationGroup: "performance"});
    const [createPerformance] = useCreateMutation();
    const {data: {data : maxJob} = {data : 0}} = useReadMaxJobQuery();

    const onClickRegistryBtn = useCallback(() => {
        createPerformance({
            instance: instanceValue,
            jobIdList: checkedJobList,
        }).then(r => {
            if (r.data){
                dispatch(create(r.data.data));
            }else {
                throw r.error;
            }
        }).catch(e => {
            if (isFetchBaseQueryError(e)){
                detectValidationError.bindResponse(e?.data);
            }else {
                console.error(e);
            }
        });
    }, [createPerformance, instanceValue, checkedJobList]);
    const instanceValueOnChange = useCallback((instanceValue: string) => {
        const valueString = instanceValue.replaceAll(/[^0-9]/g, '');
        let value: number = !isNaN(Number(valueString)) ? Number(valueString) : 0;
        if (value > maxInstance) {
            value = maxInstance;
        }
        setInstanceValue(value);
    }, [instanceValue, maxInstance]);
    if (maxInstance == undefined || maxJob == undefined) {
        return null;
    }
    return (
        <Box>
            <PerformanceSettingTemplate
                instanceArea={
                    <ValidationMessage field={"instance"} {...detectValidationError}>
                        <LabelInputText labelText={"Instance"} inputValue={instanceValue}
                                        onChange={instanceValueOnChange}/>
                    </ValidationMessage>
                }
                jobCountArea={
                    <ValidationMessage field={"jobIdList"} {...detectValidationError}>
                        {`${checkedJobList.length}/${maxJob}`}
                    </ValidationMessage>
                }
                jobListArea={
                    <JobList
                        checkedIdSet={new Set(checkedJobList)}
                        onCheckCallback={(set) => {
                            if (set.size <= maxJob) {
                                setCheckedJobList(Array.from(set));
                            }
                        }}
                        readonly={true}
                    />
                }
                controlArea={
                    <Button
                        form={"primary"}
                        onClick={onClickRegistryBtn}
                    >
                        시작
                    </Button>
                }
            />
        </Box>
    );
};

export default PerformanceSettingPage;