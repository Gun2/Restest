import {createSlice, PayloadAction} from "@reduxjs/toolkit";


//미리 정의된 다이얼로그 ID
export const DIALOG_ID = {
    //스케줄러 성공 로그
    SCHEDULER_SUCCESS_LOG : "schedulerSuccessLog",
    SCHEDULER_FAILURE_LOG : "schedulerFailureLog"
}


export type DialogState = Record<string, boolean>
const initialState : DialogState = {};
const dialogSlice = createSlice({
    name: 'dialog',
    initialState,
    reducers : {
        show: (state, {payload}: PayloadAction<string>) => ({...state, [payload] : true}),
        hide: (state, {payload} : PayloadAction<string>) => {
            delete state[payload];
        }
    }
});

export const {show, hide} = dialogSlice.actions;
export default dialogSlice.reducer;