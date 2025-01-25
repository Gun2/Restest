import React from 'react';
import styled from "styled-components";
import ScheduleRow from "../../molecules/ScheduleRow";
import {useScheduleSuccessDialog} from "hooks/useScheduleSuccessDialog";
import {useReadAllQuery as useReadAllScheduleQuery} from "../../../modules/schedule";
import {useReadAllQuery as useReadAllJobQuery} from "../../../modules/job";
import {useScheduleFailureDialog} from "hooks/useScheduleFailureDialog";
import {useFindAllQuery} from "modules/scheduler";

const Box = styled.div`
    
`
const List = styled.div`
    display: flex;
    flex-direction : column;
    gap:5px;
    padding: 10px;
`

function ScheduleList(
    {
    }
) {
    const useFindAllSchedulerResult = useFindAllQuery();
    const {data: scheduleData , ...readAllScheduleQuery} = useReadAllScheduleQuery();
    const {data: jobData = [], ...readAllJobQuery} = useReadAllJobQuery();
    const {showModal : showModalOfSuccessDialog, hideModal : hideModalOfSuccessDialog} = useScheduleSuccessDialog();
    const {showModal : showModalOfFailureDialog, hideModal : hideModalOfFailureDialog} = useScheduleFailureDialog();
    return (
        <Box>
            <List>
                {
                    scheduleData && scheduleData?.data?.map((d,i) => {
                        const schedulerStateInfo = useFindAllSchedulerResult?.data?.data ? useFindAllSchedulerResult?.data?.data.find(s => s.id == d.id) : null;
                        return (
                            <ScheduleRow
                                title={d.title}
                                schedulerStateInfo={schedulerStateInfo}
                                data={d}
                                onSaveCallback={() => {
                                    readAllScheduleQuery.refetch();
                                    readAllJobQuery.refetch();
                                }}
                                onDeleteCallback={() => {
                                    readAllScheduleQuery.refetch();
                                    readAllJobQuery.refetch();
                                }}
                                onClickSuccessIcon={() => showModalOfSuccessDialog(d.id)}
                                onClickFailureIcon={() => showModalOfFailureDialog(d.id)}
                                key={d.id}
                                _key={d.id}
                            />
                        )
                    })
                }
            </List>
        </Box>    );
}

export default ScheduleList;