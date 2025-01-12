import {HttpResponse} from "../types/httpResponse.types";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {createApi} from "@reduxjs/toolkit/query/react";

export type SchedulerSuccessLogState = {
    data: HttpResponse[],
    topic: string | null;
}

const initialState: SchedulerSuccessLogState = {
    data : [],
    topic : null,
}

const schedulerSuccessLogSlice = createSlice({
    name: 'schedulerSuccessLog',
    initialState,
    reducers: {
        addAll: (state, {payload} : PayloadAction<HttpResponse[]>) => ({
            ...state,
            data: [...state.data, ...payload]
        }),
        clear: () => ({
            ...initialState
        }),
        setTopic: (state, {payload} : PayloadAction<SchedulerSuccessLogState["topic"]>) => ({
            topic: payload,
            //topic이 달라진 경우 data 초기화
            data: state.topic != payload ? [] : state.data
        }),
        add: (state, {payload} : PayloadAction<HttpResponse>) => ({
            ...state,
            data: [...state.data, payload]
        }),
    }
})

export const {
    addAll,
    setTopic,
    clear,
    add
} = schedulerSuccessLogSlice.actions

export default schedulerSuccessLogSlice.reducer;