import type {PayloadAction} from '@reduxjs/toolkit'
import {createSlice} from '@reduxjs/toolkit'
import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";
import {SuccessResponse} from "types/api.types";
import {SchedulerState} from "types/scheduler.types";
import {WebSocketClientFactory} from "utils/webSocketClientFactory";

export type Scheduler = {
    id:  number;
};

const initialState: Scheduler[] = [];

const schedulerSlice = createSlice({
    name: 'scheduler',
    initialState,
    reducers: {
        update: (state, action: PayloadAction<Scheduler>) => {
            return state.map((s) => (s.id === action.payload.id ? action.payload : s));
        },
        insert: (state, action: PayloadAction<Scheduler>) => {
            state.push(action.payload);
        },
        delete: (state, action: PayloadAction<string | number>) => {
            return state.filter((s) => s.id !== action.payload);
        },
        init: (state, action: PayloadAction<Scheduler[]>) => {
            return [...action.payload];
        },
    },
});



export const schedulerApi = createApi({
    reducerPath: 'schedulerApi',
    baseQuery: fetchBaseQuery({ baseUrl: '/api/v1/schedulers' }),
    endpoints: (builder) => ({
        findAll: builder.query<SuccessResponse<SchedulerState[]>, void>({
            query: () => ``,
            async onCacheEntryAdded(
                arg,
                api,
            ) {
                const {cacheDataLoaded, cacheEntryRemoved } = api;
                const instance = WebSocketClientFactory.getInstance();
                const client = await instance.getClient();

                try {
                    // 초기 데이터가 로드될 때까지 대기
                    await cacheDataLoaded;
                    //추가 구독
                    client.subscribe('/scheduler/insert', (message) => {
                        if (message?.body){
                            const changeData = JSON.parse(message?.body) as unknown as SchedulerState;
                            api.updateCachedData(draft => {
                                draft.data = [...draft.data, changeData]
                            })
                        }else {
                            console.warn(`response data is not SchedulerState message: ${message}`)
                        }
                    });
                    //수정 구독
                    client.subscribe('/scheduler/update', (message) => {
                        if (message?.body){
                            const changeData = JSON.parse(message?.body) as unknown as SchedulerState;
                            api.updateCachedData(draft => {
                                draft.data = draft.data.map(d => d.id == changeData.id ? changeData : d);
                            })
                        }else {
                            console.warn(`response data is not SchedulerState message: ${message}`)
                        }
                    });
                    //삭제 구동
                    client.subscribe('/scheduler/delete', (message) => {
                        if (message?.body){
                            const changeData = message?.body as unknown as Scheduler["id"];
                            api.updateCachedData(draft => {
                                draft.data = draft.data.filter(d => d.id != changeData);
                            })
                        }else {
                            console.warn(`response data is not number message: ${message}`)
                        }
                    });
                } catch (e) {
                    console.error(e);
                }

                await cacheEntryRemoved;
            },
        })
    }),
});

export const {
    useFindAllQuery
} = schedulerApi;

export const {
    update,
    insert,
    delete: remove,
    init
} = schedulerSlice.actions;
export default schedulerSlice.reducer;
