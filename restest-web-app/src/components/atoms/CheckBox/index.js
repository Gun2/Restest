import React from 'react';
import styled from "styled-components";

const CheckInput = styled.input`
`

function CheckBox({onChange, checked}) {
    return (
        <CheckInput
            type={"checkbox"}
            onChange={onChange}
            checked={checked}
        ></CheckInput>
    );
}

export default CheckBox;