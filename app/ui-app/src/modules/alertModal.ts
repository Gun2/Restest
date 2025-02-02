import { createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'

export type AlertModal = {
    key: number,
    text: string
}

const initialState : AlertModal[] = [];
let key = 1;

const alertModalSlice = createSlice({
    name: 'alertModal',
    initialState,
    reducers: {
        add : (state, action : PayloadAction<AlertModal>) => {
            state.push({
                key: key++,
                text: action.payload.text
            });
        },
        remove : (state, action : PayloadAction<AlertModal["key"]>) => {
            return state.filter(alertModal => alertModal.key !== action.payload);
        },
    }
})
export const {add, remove} = alertModalSlice.actions;
export default alertModalSlice.reducer;