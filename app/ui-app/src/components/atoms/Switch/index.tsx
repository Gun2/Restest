import React, {useCallback, useEffect, useState} from 'react';
import styled, {css, DefaultTheme, useTheme} from "styled-components";

const Box = styled.div`
    width:40px;
    height:25px;
    position:relative;
    ${({theme}) => theme.flex.middle}
    cursor:pointer;
`;

const Thumb = styled.div<{switchOn: boolean; $color: string;}>`
    
    border-radius:50%;
    width:20px;
    height:20px;
    position:absolute;
    transition-duration:0.1s;
    ${({switchOn, $color}) => switchOn ? css`
        transform: translate(20px, 0px);
        background-color:${$color};
    ` : css`
        background-color:#fff;
    `}
`;

const Track = styled.div<{switchOn: boolean; $color: string; thumbColor: string;}>`
    ${({$color, thumbColor}) => css`
    background-color:${$color};
    background: linear-gradient(to left, ${$color} 50%, ${thumbColor} 50%) right;
    `}
    background-size:200%;
    transition-duration:0.1s;
    ${({switchOn}) => switchOn ? 'background-position:left;' : ''}    
    width:100%;
    height:60%;
    border-radius:20px;
`;
type SwitchProps = {
    checked?: boolean;
    thumbColor?: string;
    trackColor?: string;
    onChange?: (arg: {checked : boolean}) => void;
}
const Switch = (
    {
        checked = false,
        thumbColor = "#9037aa",
        trackColor = "#252525",
        onChange = () => {},
    }: SwitchProps
) => {
    const theme = useTheme();
    const [switchOn, setSwitchOn] = useState(false);
    useEffect(() => {
        setSwitchOn(checked)
    }, [checked]);
    const onClick = useCallback(() => {
        setSwitchOn(prevState => {
            const nextState = !prevState;
            onChange({checked : nextState});
            return nextState;
        });
    }, [switchOn, checked]);
    return (
        <Box onClick={onClick}>
            <Thumb $color={thumbColor} switchOn={switchOn}></Thumb>
            <Track $color={trackColor} thumbColor={theme.colorAdd(thumbColor, -30)} switchOn={switchOn}></Track>
        </Box>
    );
};

export default Switch;