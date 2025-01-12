import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {HttpResponse} from "../types/httpResponse.types";


export type SchedulerFailureLogState = {
    data: HttpResponse[],
    topic: string | null;
}

const initialState: SchedulerFailureLogState = {
    data : [],
    topic : null,
}

const schedulerFailureLogSlice = createSlice({
    name: 'schedulerFailureLog',
    initialState,
    reducers: {
        addAll: (state, {payload} : PayloadAction<HttpResponse[]>) => ({
            ...state,
            data: [...state.data, ...payload]
        }),
        clear: () => ({
            ...initialState
        }),
        setTopic: (state, {payload} : PayloadAction<SchedulerFailureLogState["topic"]>) => ({
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
} = schedulerFailureLogSlice.actions

export default schedulerFailureLogSlice.reducer;