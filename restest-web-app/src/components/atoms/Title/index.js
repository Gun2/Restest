import React from 'react';
import styled from 'styled-components';

const Box = styled.div`

`

const Text = styled.div`
    font-size : ${({fontSize}) => fontSize ? `${fontSize}px;` : `20px;`}
    font-weight : bold;
    color : ${({theme, color}) => (color || theme.palette.background)};
`;


function Title({text, fontSize, children, color}) {
    return (
        <Box>
            <Text fontSize={fontSize} color={color}>
                {children || text}
            </Text>
        </Box>
    );
}

export default Title;