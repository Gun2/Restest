import React, {useCallback, useEffect} from 'react';
import styled from "styled-components";
import Button from "../../../components/atoms/Button";
import PerformanceRunningTemplate from "./components/PerformanceRunningTemplate";
import RequestCountLineChart from "./components/RequestCountLineChart";
import RpmLineChart from "./components/RpmLineChart";
import JobCardList from "../../JobPage/components/JobCardList";
import StopIcon from '@mui/icons-material/Stop';
import {usePerformanceRunning} from "hooks/usePerformanceRunning";

const Box = styled.div`
`;
type PerformanceRunningPageProps = {
    uuid: string
}
const PerformanceRunningPage = (
    {
        uuid
    }: PerformanceRunningPageProps
) => {
    const {data: performanceState, init, changeUuid, forceStop} = usePerformanceRunning();
    const {jobList, data, countData, rpmData, rpmSum, run} = performanceState;

    const onClickBackBtn = useCallback(() => {
        init();
    }, []);

    const onClickStopBtn = useCallback(() => {
        forceStop();
    }, [forceStop]);
    useEffect(() => {
        changeUuid(uuid);
    }, [uuid]);
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