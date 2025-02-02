import React from 'react';
import styled from "styled-components";

const Box = styled.pre`
    background-color : ${({theme}) => theme.palette.disabled};
    margin:0;
    overflow-y:auto;
    max-height:300px;
    text-align:left;
`;
type ReadOnlyTextProps = {
    text: React.ReactNode;
}
const ReadOnlyText = (
    {
        text
    }: ReadOnlyTextProps
) => {
    return (
        <Box>
            {text}
        </Box>
    );
};

export default ReadOnlyText;