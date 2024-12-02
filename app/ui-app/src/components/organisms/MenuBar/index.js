import React, {useCallback, useState} from 'react';
import styled from 'styled-components';
import MenuList from '../../molecules/MenuList';
import MenuTop from '../../molecules/MenuTop';

const Box = styled.div`
    background-color : ${({theme}) => theme.palette.panel};
    height: 100vh;
`;


function MenuBar(props) {
    const [menuTextHide, setMenuTextHide] = useState(true);
    const onToggle = useCallback((id) => {
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