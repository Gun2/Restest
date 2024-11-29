import React from 'react';
import styled from 'styled-components';
import PropTypes from 'prop-types';


const SelectBox = styled.select`
    height: 32px;
    border-radius: 0.3em;
    ${({theme, readonly}) => readonly && theme.style.readonly}
`

function Select({
                    children,
                    value,
                    onChange,
                    readonly
                }) {
    return (
        <SelectBox
            value={value}
            onChange={({target}) => onChange(target.value)}
            readonly={readonly}
        >
            {children}

        </SelectBox>
    );
}

export default Select;