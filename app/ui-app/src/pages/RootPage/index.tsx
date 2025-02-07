import React from 'react';
import LayoutTemplate from './components/RootTemplate';
import MenuBar from '../../components/organisms/MenuBar';
import DashBoardPage from '../DashBoardPage';
import styled from 'styled-components';
import {Route, Routes} from 'react-router-dom';
import Test from '../../Test';
import JobPage from "../JobPage";
import SchedulePage from "../SchedulePage";
import {useDispatch, useSelector} from "react-redux";
import PerformancePage from "../PerformancePage";
import AlertModal from "../../components/organisms/AlertModal";
import {remove} from "modules/alertModal";
import GlobalLoading from "../../components/organisms/GlobalLoading";
import GlobalDraggableDialog from "../../components/organisms/GlobalDraggableDialog";
import {RootState} from "../../store";

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