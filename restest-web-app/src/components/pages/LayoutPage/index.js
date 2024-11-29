import React, {useMemo, useState} from 'react';
import LayoutTemplate from '../../templates/LayoutTemplate';
import MenuBar from '../../organisms/MenuBar';
import DashBoardPage from '../DashBoardPage';
import styled from 'styled-components';
import {Route, Routes} from 'react-router-dom';
import Test from '../../../Test';
import JobPage from "../JobPage";
import SchedulePage from "../SchedulePage";
import LoadingBox from "../../molecules/LoadingBox";
import {useDispatch, useSelector} from "react-redux";
import LogDialog from "../../organisms/LogDialog";
import PerformancePage from "../PerformancePage";
import AlertModal from "../../organisms/AlertModal";
import {removeAlertAction} from "../../../modules/alertModal";

const Box = styled.div`
`;

function LayoutPage(props) {
    const dispatch = useDispatch();
    const loadingShow = useSelector(state => state.loading);
    const schedulerFailureLog = useSelector(store => store.schedulerFailureLog.data);
    const schedulerSuccessLog = useSelector(store => store.schedulerSuccessLog.data);
    const [dialogFocusId, setDialogFocusId] = useState('');

    const dialogOnClick = (id) => {
        setDialogFocusId(id);
    }
    const dialogData = useMemo(() => {
        const dialogList = [
            {
                id: "schedulerFailureLog",
                title: "실패 내역 조회",
                data: schedulerFailureLog,
                startPoint: {
                    x: 0,
                    y: 200,
                }
            },
            {
                id: 'schedulerSuccessLog',
                title: '성공 내역 조회',
                data: schedulerSuccessLog,
                startPoint: {
                    x: 30,
                    y: 230
                },
            }
        ];
        const focusingDialog = dialogList.find(d => d.id == dialogFocusId);
        if(focusingDialog){
            return [...dialogList.filter(d => d.id != dialogFocusId), focusingDialog];
        }else{
            return dialogList;
        }
    }, [schedulerFailureLog, schedulerSuccessLog, dialogFocusId])
    const alertModal = useSelector(store => store.alertModal);
    return (
        <Box>
            {
                alertModal.map(modal => <AlertModal
                    key={modal.key}
                    id={modal.key}
                    text={modal.text}
                    onClickConfirm={(id) => dispatch(removeAlertAction(id))}/>)
            }
            <LoadingBox show={loadingShow}></LoadingBox>
            {
                dialogData.map(
                    dialog => <LogDialog
                        key={dialog.id}
                        id={dialog.id}
                        title={dialog.title}
                        data={dialog.data}
                        startPoint={dialog.startPoint}
                        onClick={dialogOnClick}
                    />
                )
            }
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