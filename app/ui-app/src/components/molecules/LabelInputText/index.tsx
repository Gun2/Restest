import React from 'react';
import styled from "styled-components";
import Label from "../../atoms/Label";
import InputText from "../../atoms/InputText";

const Box = styled.div`
    ${({theme}) => theme.flex.startCenter};
    gap:10px;
`
type LabelInputTextProps = {
    labelText: string;
    inputValue: any;
    onChange: (value: any) => void;
}
const LabelInputText = (
    {
        labelText,
        inputValue,
        onChange
    }: LabelInputTextProps
) => {
    return (
        <Box>
            <Label size={20} text={labelText}/>
            <InputText value={inputValue} onChange={onChange}/>
        </Box>
    );
};

export default LabelInputText;