import React, {useEffect, useState} from 'react';
import styled from "styled-components";
import {AreaChart, Area, CartesianGrid, Legend, Line, Tooltip, XAxis, YAxis, ReferenceLine, Label} from "recharts";
import Title from "../../atoms/Title";

const Box = styled.div`
`;

const separateComma = (num) => num.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");

const rpmAvgFormat = (rpmAvg) => separateComma(Math.floor(rpmAvg));

const RpmLineChart = ({jobList = [], data = [], rpmSum = {}}) => {
    //const [data, setData] = useState(initData);
    return (
        <Box>
            <Title text={"RPM 지수"} color={({theme}) => theme.palette.default}/>
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

                        rpmSum[job.id] && data.length > 2 &&
                        <ReferenceLine y={rpmSum[job.id] / (data.length - 1)} stroke={job.color} strokeWidth={2} isFront={true}>
                            <Label position="insideBottomRight" stroke={job.color}>
                                {`${rpmAvgFormat(rpmSum[job.id] / (data.length - 1))}rpm`}
                            </Label>
                        </ReferenceLine>
                    </>))
                }
            </AreaChart>
        </Box>
    );
};

export default RpmLineChart;