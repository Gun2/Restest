import React, {useCallback, useState} from 'react';
import styled from 'styled-components';
import MenuList from '../../molecules/MenuList';
import MenuTop from '../../molecules/MenuTop';

const Box = styled.div`
    background-color : ${({theme}) => theme.palette.panel};
    height: 100vh;
`;


function MenuBar() {
    const [menuTextHide, setMenuTextHide] = useState(true);
    const onToggle = useCallback((id: number) => {
        setMenuTextHide(
            (id === 1 ? true : false)
        );
    }, []);
    return (
        <Box style={{
            width : (menuTextHide ? "50px" : "200px")
        }}>
            <MenuTop onToggle={onToggle}/>
            <MenuList textHide={menuTextHide} />
        </Box>
    );
}

export default MenuBar;