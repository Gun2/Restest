import React from 'react';
import styled, {useTheme} from "styled-components";
import {Area, AreaChart, CartesianGrid, Label, Legend, ReferenceLine, Tooltip, XAxis, YAxis} from "recharts";
import Title from "../../atoms/Title";
import {Job} from "@_types/job.types";
import {PerformanceDataOfJobs} from "@_modules/performance";

const Box = styled.div`
`;

const separateComma = (num: number) => num.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");

const rpmAvgFormat = (rpmAvg: number) => separateComma(Math.floor(rpmAvg));
type RpmLineChartProps = {
    jobList: Job[];
    data: PerformanceDataOfJobs[];
    rpmSum: Record<Job["id"], number>;
}
const RpmLineChart = (
    {
        jobList = [],
        data = [],
        rpmSum = {}
    }: RpmLineChartProps
) => {
    const theme = useTheme();
    return (
        <Box>
            <Title text={"RPM 지수"} color={theme.palette.text.default}/>
            <AreaChart width={700} height={300} data={data}
                       margin={{top: 5, right: 30, left: 20, bottom: 5}}>
                <CartesianGrid strokeDasharray="3 3"/>
                <XAxis dataKey="measureTime" tick={{
                    stroke: "#797979",
                    strokeWidth: 0.5,
                }}/>
                <YAxis tick={{
                    stroke: "#797979",
                    strokeWidth: 0.5,
                }}/>
                <Tooltip contentStyle={{backgroundColor: "#000"}} labelFormatter={(value) => `${value}ms`}/>
                <Legend/>
                {
                    jobList.map(job => (<>
                        <Area key={job.id} type="monotone" dataKey={job.id} stroke={job.color} name={job.title}
                              dot={false} opacity={0.5} fill={job.color}/>
                        {
                            rpmSum[job.id] && data.length > 2 &&
                            (
                                <ReferenceLine y={rpmSum[job.id] / (data.length - 1)} stroke={job.color} strokeWidth={2}
                                               isFront={true}>
                                    <Label position="insideBottomRight" stroke={job.color}>
                                        {`${rpmAvgFormat(rpmSum[job.id] / (data.length - 1))}rpm`}
                                    </Label>
                                </ReferenceLine>
                            )
                        }
                    </>))
                }
            </AreaChart>
        </Box>
    );
};

export default RpmLineChart;