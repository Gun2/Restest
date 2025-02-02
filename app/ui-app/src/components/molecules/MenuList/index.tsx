import React from 'react';
import { MdHistory, MdOutlineLocalFireDepartment, MdOutlineMonitor, MdWork } from 'react-icons/md';
import styled from 'styled-components';
import MenuItem from '../../atoms/MenuItem';

const Box = styled.div` 
    
`;
type MenuListProps = {
    //text 숨김 여부
    textHide: boolean;
}
function MenuList(
    {
        textHide
    }: MenuListProps
) {
    return (
        <Box>
            <MenuItem text={"대시보드"} textHide={textHide} to={"dashboard"}>
                <MdOutlineMonitor />
            </MenuItem>
            <MenuItem text={"업무 관리"} textHide={textHide} to={"job"}>
                <MdWork />
            </MenuItem>
            <MenuItem text={"스케줄러"} textHide={textHide} to={"scheduler"}>
                <MdHistory />
            </MenuItem>
            <MenuItem text={"성능 측정"} textHide={textHide} to={"performance"}>
                <MdOutlineLocalFireDepartment />
            </MenuItem>
        </Box>
    );
}

export default MenuList;