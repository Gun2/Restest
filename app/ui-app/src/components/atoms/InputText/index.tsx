import React from 'react';
import styled from 'styled-components';

const Input = styled.input<{$widthFull?: boolean, $readonly?: boolean}>`
    height: 30px;
    font-size: 14px;
    border: none;
    border-radius: 0.3em;
    ${({$widthFull}) => $widthFull && "width : 100%;"}
    ${({theme, $readonly}) => $readonly && theme.style.readonly}
    
`;
type InputTextProps = {
    placeholder?: any;
    value?: any;
    onChange : (value : any) => void;
    widthFull?: boolean;
    readonly?: boolean
}
function InputText(
    {
       placeholder,
       value,
       widthFull,
       onChange,
       readonly
   }: InputTextProps
) {

    return (
        <Input
            placeholder={placeholder}
            value={value}
            onChange={({target}) => onChange(target.value)}
            $widthFull
            $readonly={readonly}
        />
    );
}

export default InputText;