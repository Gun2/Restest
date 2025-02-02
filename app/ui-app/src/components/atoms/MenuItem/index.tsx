import React from 'react';
import styled, {useTheme} from 'styled-components';
import {NavLink} from 'react-router-dom';

MenuItem.propTypes = {
    
};

const Box = styled.div<{textHide: boolean}>`
    .active &{
        background-color : ${({theme}) => theme.palette.background} !important;
        color : ${({theme}) => theme.palette.text.default};

    }
    background-color : ${({theme}) => theme.palette.panel};
    color : ${({theme}) => theme.palette.text.primary};
    height : 40px;
    ${({theme, textHide}) => textHide ? theme.flex.center : theme.flex.startCenter};
    padding : 5px;
    gap : 5px;
    font-size : 25px;
    &:hover{
        background-color : ${({theme}) => theme.palette.text.primary};
        color : ${({theme}) => theme.palette.panel};
    }
    cursor : pointer;
`
const navLinkStyle = ({isActive} : {isActive: boolean}): React.CSSProperties => ({
    textDecoration: 'none',
    backgroundColor: isActive ? '#fff' : undefined,
})

type MenuItemProps = {
    children: React.ReactNode;
    text: string;
    textHide: boolean;
    to: string;

}
function MenuItem(
    {
        children,
        text,
        textHide,
        to,
    }: MenuItemProps
) {
    const theme = useTheme();
    return (
        <NavLink style={navLinkStyle} to={to}>
            <Box textHide={textHide}>
                <div>
                {children}
                </div>
                {
                    !textHide && <div>{text}</div>
                }

            </Box>

        </NavLink>
    );
}

export default MenuItem;