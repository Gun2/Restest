import { createSlice } from '@reduxjs/toolkit';

const initialState = false;

const loadingSlice = createSlice({
    name: 'loading',
    initialState,
    reducers: {
        show: () => true,
        hide: () => false,
    },
});

export const { show, hide } = loadingSlice.actions; // 액션 생성자
export default loadingSlice.reducer;
