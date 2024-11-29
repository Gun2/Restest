import React, {useCallback, useEffect} from 'react';
import styled from "styled-components";
import Button from "../../atoms/Button";
import PerformanceRunningTemplate from "../../templates/PerformanceRunningTemplate";
import RequestCountLineChart from "../../organisms/RequestCountLineChart";
import {useDispatch, useSelector} from "react-redux";
import {
    addPerformanceDataAction, forceStopPerformanceActionThunk,
    removePerformanceAction,
    startPerformanceActionThunk,
    stopPerformanceAction,
    stopPerformanceActionThunk
} from "../../../modules/performance";
import {addTopicAction} from "../../../modules/topic";
import RpmLineChart from "../../organisms/RpmLineChart";
import JobCardList from "../../organisms/JobCardList";
import StopIcon from '@mui/icons-material/Stop';

const Box = styled.div`
`;
const PerformanceRunningPage = ({uuid}) => {
    const dispatch = useDispatch();
    const {jobList, data, countData, rpmData, rpmSum, run} = useSelector(store => store.performance);
    useEffect(() => {
        if(uuid){
            dispatch(addTopicAction({
                topic : `/performance/${uuid}`,
                actionCallback : (r) => addPerformanceDataAction(r),
            }));
            dispatch(addTopicAction({
                topic : `/performance/${uuid}/stop`,
                actionCallback : (r) => stopPerformanceActionThunk(),
            }));
            dispatch(startPerformanceActionThunk({
                param:{
                    uuid: uuid
                }
            }));
        }

    }, [uuid])
    const onClickBackBtn = useCallback(() => {
        dispatch(removePerformanceAction());
    }, []);

    const onClickStopBtn = useCallback(() => {
        dispatch(forceStopPerformanceActionThunk({
            param: {
                uuid : uuid
            }
        }));
    }, []);
    return (
        <Box>
            <PerformanceRunningTemplate
                cardArea={
                    <JobCardList jobList={jobList} data={data}/>
                }
                chartArea={
                    <>
                        <RequestCountLineChart jobList={jobList} data={countData}/>
                        <RpmLineChart jobList={jobList} data={rpmData} rpmSum={rpmSum}/>
                    </>
                }
                controlArea={
                        run ?
                        <Button form={"danger"}
                                onClick={onClickStopBtn}
                        ><StopIcon/></Button>
                        :
                        <Button form={"primary"}
                                onClick={onClickBackBtn}
                        >뒤로</Button>
                }
            />
        </Box>
    );
};

export default PerformanceRunningPage;