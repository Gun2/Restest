import React, {useState} from 'react';
import styled from "styled-components";
import PerformanceSettingPage from "../PerformanceSettingPage";
import PerformanceRunningPage from "../PerformanceRunningPage";
import {useSelector} from "react-redux";
import {RootState} from "store";

const Box = styled.div`
    ${({theme}) => theme.flex.center};
`
const PerformancePage = () => {
    const uuid = useSelector((store: RootState) => store.performance.uuid);
    return (
        <Box>
            {
                !uuid &&
                <PerformanceSettingPage/>
            }
            {
                uuid &&
                <PerformanceRunningPage uuid={uuid}/>
            }
        </Box>
    );
};

export default PerformancePage;