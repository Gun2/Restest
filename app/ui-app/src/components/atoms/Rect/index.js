import React from 'react';
import styled from "styled-components";

const Box = styled.div`
    background-color : ${({color})=>color};
    width : ${({width}) => width}px;
    height : ${({height}) => height == 'full' ? '100%' : height + 'px'};
    flex:none;
`

const Rect = ({color,width=10,height= 10}) => {
    return (
        <Box color={color} width={width} height={height}></Box>
    );
};

export default Rect;