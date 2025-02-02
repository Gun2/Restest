import React from 'react';
import styled from "styled-components";

const Box = styled.div<{$size: number}>`
    font-size : ${({$size}) => $size ? $size : 12}px;
    font-weight:bold
`
type LabelProps = {
    text: React.ReactNode;
    size: number;
}
const Label = (
    {
        text,
        size
    }: LabelProps
) => {
    return (
        <Box $size={size}>
            {text}
        </Box>
    );
};

export default Label;