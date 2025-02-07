import React from 'react';
import styled, {useTheme} from "styled-components";
import {CartesianGrid, Legend, Line, LineChart, Tooltip, XAxis, YAxis} from "recharts";
import Title from "../../../../../components/atoms/Title";
import {Job} from "types/job.types";
import {PerformanceDataOfJobs} from "modules/performance";

const Box = styled.div`
`;
type RequestCountLineChartProps = {
    jobList: Job[];
    data: PerformanceDataOfJobs[]
}
const RequestCountLineChart = (
    {
        jobList = [],
        data = []
    }: RequestCountLineChartProps
) => {
    const theme = useTheme();
    return (
        <Box>
            <Title text={"요청 완료 횟수"} color={theme.palette.text.default}/>
            <LineChart width={700} height={300} data={data}
                       margin={{ top: 5, right: 30, left: 20, bottom: 5 }}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="measureTime" tick={{
                    stroke: "#aaa",
                    strokeWidth : 0.5,
                }}/>
                <YAxis tick={{
                    stroke: "#aaa",
                    strokeWidth : 0.5,
                }}/>
                <Tooltip  contentStyle={{backgroundColor:"#000"}} labelFormatter={(value) => `${value}ms`}/>
                <Legend />
                {
                    jobList.map( job => <Line key={job.id} type="monotone" dataKey={job.id} stroke={job.color} name={job.title} dot={false}/>)
                }
            </LineChart>
        </Box>
    );
};

export default RequestCountLineChart;