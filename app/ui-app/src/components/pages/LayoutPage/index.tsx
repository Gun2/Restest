import React from 'react';
import LayoutTemplate from '../../templates/LayoutTemplate';
import MenuBar from '../../organisms/MenuBar';
import DashBoardPage from '../DashBoardPage';
import styled from 'styled-components';
import {Route, Routes} from 'react-router-dom';
import Test from '../../../Test';
import JobPage from "../JobPage";
import SchedulePage from "../SchedulePage";
import {useDispatch, useSelector} from "react-redux";
import PerformancePage from "../PerformancePage";
import AlertModal from "../../organisms/AlertModal";
import {remove} from "../../../modules/alertModal";
import GlobalLoading from "../../organisms/GlobalLoading";
import GlobalDraggableDialog from "../../organisms/GlobalDraggableDialog";
import {RootState} from "store";

const Box = styled.div`
`;

function LayoutPage() {
    const dispatch = useDispatch();

    const alertModal = useSelector((store: RootState) => store.alertModal);
    return (
        <Box>
            {
                alertModal.map(modal => (
                    <AlertModal
                        key={modal.key}
                        id={modal.key}
                        text={modal.text}
                        onClickConfirm={(id) => dispatch(remove(id))}
                    />
                ))
            }
            <GlobalLoading/>
            <GlobalDraggableDialog/>
            <LayoutTemplate
                menu={<MenuBar/>}
                content={
                    <Routes>
                        {['/dashboard', '/'].map(path => (
                            <Route key={path} path={path} element={<DashBoardPage/>}/>
                        ))}
                        <Route path='/job' element={<JobPage/>}/>
                        <Route path='/scheduler' element={<SchedulePage/>}/>
                        <Route path='/test' element={<Test/>}/>
                        <Route path='/performance' element={<PerformancePage/>}/>
                    </Routes>
                }/>
        </Box>

    );
}

export default LayoutPage;