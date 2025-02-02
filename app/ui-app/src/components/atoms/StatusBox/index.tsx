import React, {useMemo} from 'react';
import styled from "styled-components";

const Box = styled.div`
    background-color : ${({color}) => color};
    color: ${({theme}) => theme.palette.text.default};
    padding:8px 10px;
    font-weight: bold;
    border-radius: 20px;
    min-width: 60px;

`;

const getStatusColor = (color: number | string) => {
    if(color >= 100 && color < 300){
        return "#00aa00";
    }else if(color >= 300 && color < 400){
        return "#aaaa00";
    }else if(color >= 400 && color < 600){
        return "#aa0000";
    }else {
        return "#646464";
    }
}

type StatusBoxProps = {
    //status code
    status: number | string
}
/**
 * http status를 출력하는 box 컴포넌트
 * @return {JSX.Element}
 * @constructor
 */
const StatusBox = (
    {
        status
    }: StatusBoxProps
) => {
    const statusColor = useMemo(() => getStatusColor(status), [status]);
    return (
        <Box color={statusColor}>
            {status}
        </Box>
    );
};

export default StatusBox;