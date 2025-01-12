import React, {useMemo, useState} from 'react';
import {useSelector} from "react-redux";
import {DIALOG_ID} from "modules/dialog";
import LogDialog from "../LogDialog";
import {RootState} from "store";

const GlobalDraggableDialog = () => {
    const schedulerFailureLog = useSelector((store: RootState) => store.schedulerFailureLog.data);
    const schedulerSuccessLog = useSelector((store: RootState) => store.schedulerSuccessLog.data);
    const [dialogFocusId, setDialogFocusId] = useState<string>('');

    const dialogOnClick = (id: string) => {
        setDialogFocusId(id);
    }
    const dialogData = useMemo(() => {
        const dialogList = [
            {
                id: DIALOG_ID.SCHEDULER_FAILURE_LOG,
                title: "실패 내역 조회",
                data: schedulerFailureLog,
                startPoint: {
                    x: 0,
                    y: 200,
                }
            },
            {
                id: DIALOG_ID.SCHEDULER_SUCCESS_LOG,
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
    return (
        <>
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
        </>
    );
};

export default GlobalDraggableDialog;