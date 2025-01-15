import React, {ChangeEventHandler} from 'react';
import styled from "styled-components";

const CheckInput = styled.input`
`
type CheckBoxProps = {
    onChange?: ChangeEventHandler<HTMLInputElement>;
    checked?: boolean
}
function CheckBox(
    {
        onChange,
        checked
    }: CheckBoxProps
) {
    return (
        <CheckInput
            type={"checkbox"}
            onChange={onChange}
            checked={checked}
        ></CheckInput>
    );
}

export default CheckBox;