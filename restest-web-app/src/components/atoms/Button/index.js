import React from 'react';
import style, {css} from 'styled-components';

const Button = style.button`
    width : 100px;
    height : 30px;
    color : #ffffff;
    border: none;
    border-radius : 5px;
    border-width : 0px;
    ${({theme, form='primary'}) => theme.map.button(form)};
    cursor:pointer;
    ${({width}) => width ? `width:${width}px;` : ''}
    ${({height}) => height ? `height:${height}px;` : ''}
    ${({circle}) => circle ? `border-radius:50%;` : ''}
`;

export default Button;