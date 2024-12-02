import React, {useCallback} from 'react';
import styled, {css} from "styled-components";

const Box = styled.div`
    ${({theme}) => theme.map.tab('primary')};
    background-color : ${({theme}) => theme.palette.secondary};
    cursor:pointer;
    color : #fff;
    padding : 5px 10px;
    ${({active}) => active&&css`
    background-color : ${({theme}) => theme.palette.primary};
    color : ${({theme}) => theme.palette.background};
    `};
`;
function Tab({children, onTab, item, active}) {
    const onClick = useCallback(() => {
        onTab(item);
    }, [item]);
    return (
        <Box onClick={onClick} active={active}>
            {children}
        </Box>
    );
}

export default Tab;