import {PerformanceData} from "types/performanceData.types";
import {Job} from "types/job.types";
import { createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'
import {PerformanceCreateRequest, Performance, PerformanceRequest} from "types/performance.types";
import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";
import {SuccessResponse} from "types/api.types";

/**
 * 측정 시간값과 해당 시간의 job들의 값
 */
export type PerformanceDataOfJobs = {measureTime: number} & Record<Job["id"], number>;

export type PerformanceState = Omit<Performance, "uuid"> & {
    uuid: null | string,
    run: boolean;
    data: PerformanceData[];
    countData: PerformanceDataOfJobs[];
    rpmData: PerformanceDataOfJobs[]
    rpmSum: Record<number, number>;

}

const initialState: PerformanceState = {
    uuid: null,
    run: false,
    jobList: [],
    data : [],
    countData : [],
    rpmData : [],
    rpmSum:{},
    instance: 0,
}

//측정 시간 순 정렬
const sortPerformanceData = (array: PerformanceData[] = [], putItem: PerformanceData):  PerformanceData[] => {
    const lastIndex = array.length -1;
    for (let i = lastIndex; i >= 0; i--) {
        if(array[i].measureTime <= putItem.measureTime){
            return [...array.slice(0,i+1), putItem, ...array.slice(i+1)];
        }
    }
    return [putItem, ...array];
}

const sortPerformanceDataOfJobs = (array: PerformanceDataOfJobs[] = [], putItem: PerformanceDataOfJobs):  PerformanceDataOfJobs[] => {
    const lastIndex = array.length -1;
    for (let i = lastIndex; i >= 0; i--) {
        if(array[i].measureTime <= putItem.measureTime){
            return [...array.slice(0,i+1), putItem, ...array.slice(i+1)];
        }
    }
    return [putItem, ...array];
}

/**
 * 측정 정보 전처리
 * @param state : state
 * @param item : Object
 */
const dataPreProcessing = (state: PerformanceState, item: PerformanceData): Pick<PerformanceState, "data" | "countData" | "rpmData" | "rpmSum"> => {

    return {
        data : sortPerformanceData(state.data, item),
        countData :sortPerformanceDataOfJobs(state.countData, countDataPreProcessing(item)),
        rpmData :sortPerformanceDataOfJobs(state.rpmData, rpmDataPreProcessing(item)),
        rpmSum : rpmSumPreProcessing(state.rpmSum, item),
    }
}

/**
 * count정보 전처리
 * @param measureTime : Number
 * @param performanceTaskMeasureList : Array
 */
const countDataPreProcessing = ({measureTime, performanceTaskMeasureList}: PerformanceData): PerformanceDataOfJobs => {
    return {
        measureTime,
        ...performanceTaskMeasureList.reduce((obj, data) => ({...obj, [data.jobId] : data.cnt}), {})
    }
}

/**
 * rpm정보 전처리
 * @param measureTime : Number
 * @param performanceTaskMeasureList : Array
 * @return {*&{measureTime}}
 */
const rpmDataPreProcessing = ({measureTime, performanceTaskMeasureList}: PerformanceData): PerformanceDataOfJobs => {
    return {
        measureTime,
        ...performanceTaskMeasureList.reduce((obj, data) => ({...obj, [data.jobId] : data.rpm}), {})
    }
}

/**
 * rpm 수치 핪산
 * @param rpmSum
 * @param performanceTaskMeasureList
 * @return {*}
 */
const rpmSumPreProcessing = (rpmSum : PerformanceState["rpmSum"], {performanceTaskMeasureList}: PerformanceData) => {
    return performanceTaskMeasureList.reduce((obj, data) => ({...obj, [data.jobId] : elseGetZero(rpmSum[data.jobId])+ data.rpm}), {});
}

const elseGetZero = (num : number | null) => {
    return num || 0;
}

export const performanceApi = createApi({
    reducerPath: 'performanceApi',
    baseQuery: fetchBaseQuery({ baseUrl: '/api/v1/performances' }),
    endpoints: (builder) => ({
        create: builder.mutation<SuccessResponse<Performance>, PerformanceCreateRequest>({
            query: (arg) => ({
                url: '',
                method: 'POST',
                body: arg
            }),
        }),
        stop: builder.mutation<SuccessResponse<PerformanceRequest["uuid"]>, PerformanceRequest>({
            query: (arg) => ({
                url: '/stop',
                method: 'POST',
                body: arg
            }),
        }),
        start: builder.mutation<SuccessResponse<PerformanceRequest["uuid"]>, PerformanceRequest>({
            query: (arg) => ({
                url: '/start',
                method: 'POST',
                body: arg
            }),
        }),

    }),
});

export const performanceSlice = createSlice({
    name: 'performance',
    initialState,
    reducers: {
        create: (state, {payload}: PayloadAction<Performance>) => ({
            ...initialState,
            uuid: payload.uuid,
            jobList: payload.jobList,
            instance: payload.instance,
        }),
        remove: (state) => ({...initialState}),
        start: (state) => ({...state, run: true }),
        stop: (state) => ({...state, run: false}),
        addData: (state, {payload}: PayloadAction<PerformanceData>) => (
            {...state, ...dataPreProcessing(state, payload)}
        ),
    },
});


export const {
    start,
    stop,
    remove,
    create,
    addData
} = performanceSlice.actions;

export const {
    useCreateMutation,
    useStopMutation,
    useStartMutation
} = performanceApi

