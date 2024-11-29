import React, {useCallback, useEffect, useReducer} from 'react';
import { MdOutlineArrowForwardIos } from 'react-icons/md';
import styled from 'styled-components';

const Box = styled.div`
    ${({theme}) => theme.flex.center};
    padding : 5px;
`;

const Button = styled.div`
    color : ${({theme}) => theme.palette.text.primary};
    ${({theme}) => theme.flex.center};
    cursor : pointer;
    &:hover{
        color : ${({theme}) => theme.palette.text.default};
    };
`;

function reducer(state,{type}){
    switch (type){
        case "INCREASE":
            return state+1;
        case "INIT":
            return 1;
    }
}

function DirectionToggle({degree = 0, onToggle}) {
    //console.log(Box);
    const [count, increaseDispatch] = useReducer(reducer, 0)
    const onClick = () => {
        increaseDispatch({type:"INCREASE"});
        onToggle(count % 2);
    }
    return (
        <Box style={{
            transform : `rotate(${((count % 2) * 180) + degree}deg
            )`,
        }}>
            <Button onClick={onClick}>
                <MdOutlineArrowForwardIos size={30}/>
            </Button>
        </Box>
    );
}

export default DirectionToggle;