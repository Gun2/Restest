import React from 'react';
import styled from "styled-components";
import {CartesianGrid, Legend, Line, LineChart, Tooltip, XAxis, YAxis} from "recharts";
import Title from "../../atoms/Title";

const Box = styled.div`
`;
const RequestCountLineChart = ({jobList = [], data = []}) => {
    //const [data, setData] = useState(initData);
    return (
        <Box>
            <Title text={"요청 완료 횟수"} color={({theme}) => theme.palette.default}/>
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