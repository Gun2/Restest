import React from 'react';
import styled from "styled-components";

const Box = styled.div<{$color: string; $width: number; $height: number | 'full';}>`
    background-color : ${({$color})=>$color};
    width : ${({$width}) => $width}px;
    height : ${({$height}) => $height === 'full' ? '100%' : $height + 'px'};
    flex:none;
`
type RectProps = {
    color : string;
    width?: number;
    height?: number | 'full';
}
const Rect = (
    {
        color,
        width=10,
        height= 10
    }: RectProps
) => {
    return (
        <Box $color={color} $width={width} $height={height}></Box>
    );
};

export default Rect;