import React from 'react';
import styled from "styled-components";

const Box = styled.div`
    font-size : ${({size}) => size ? size : 12}px;
    font-weight:bold
`
const Label = ({text, size}) => {
    return (
        <Box size={size}>
            {text}
        </Box>
    );
};

export default Label;