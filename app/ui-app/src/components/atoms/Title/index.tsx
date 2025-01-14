import React from 'react';
import styled from 'styled-components';

const Box = styled.div`

`

const Text = styled.div<{$fontSize?: number, $color?: string}>`
    font-size : ${({$fontSize}) => $fontSize ? `${$fontSize}px;` : `20px;`}
    font-weight : bold;
    color : ${({theme, $color}) => ($color || theme.palette.background)};
`;

type TitleProps = {
    text?: React.ReactNode,
    fontSize?: number,
    children?: React.ReactNode,
    color?: string,
}
function Title(
    {
        text,
        fontSize,
        children,
        color
    }
    : TitleProps
) {
    return (
        <Box>
            <Text $fontSize={fontSize} $color={color}>
                {children || text}
            </Text>
        </Box>
    );
}

export default Title;