import React from 'react';
import styled from 'styled-components';
import DirectionToggle from '../../atoms/DirectionToggle';

const Box = styled.div`
    ${({theme}) => theme.flex.endCenter};
    background-color : ${({theme}) => theme.palette.panel};
`;
function MenuTop({onToggle}) {
    return (
        <Box>
            <DirectionToggle degree={0} onToggle={onToggle}/>
        </Box>
    );
}

export default MenuTop;