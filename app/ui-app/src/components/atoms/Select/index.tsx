import React from 'react';
import styled from 'styled-components';


const SelectBox = styled.select<{$readonly?: boolean}>`
    height: 32px;
    border-radius: 0.3em;
    ${({theme, $readonly}) => $readonly && theme.style.readonly}
`

type SelectProps = {
    children?: React.ReactNode;
    value: any
    onChange: (value: any) => void;
    readonly?: boolean;
}
function Select(
    {
        children,
        value,
        onChange,
        readonly
    }: SelectProps) {
    return (
        <SelectBox
            value={value}
            onChange={({target}) => onChange(target.value)}
            $readonly={readonly}
        >
            {children}
        </SelectBox>
    );
}

export default Select;