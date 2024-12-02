import {createAction, handleActions} from "redux-actions";
import {createRequestThunk} from "../lib/thunkCreator";
import performanceApi from "../api/performanceApi";
import {removeTopicAction} from "./topic";

const CREATE_PERFORMANCE = 'performance/CREATE_PERFORMANCE';
const REMOVE_PERFORMANCE = 'performance/REMOVE_PERFORMANCE';
const ADD_PERFORMANCE_DATA = 'performance/ADD_PERFORMANCE_DATA';
const START_PERFORMANCE = 'performance/START_PERFORMANCE';
const STOP_PERFORMANCE = 'performance/STOP_PERFORMANCE';

const createPerformanceAction = createAction(CREATE_PERFORMANCE);
export const removePerformanceAction = createAction(REMOVE_PERFORMANCE);
const startPerformanceAction = createAction(START_PERFORMANCE);
export const addPerformanceDataAction = createAction(ADD_PERFORMANCE_DATA);
const stopPerformanceAction = createAction(STOP_PERFORMANCE);

export const createPerformanceThunk = createRequestThunk(performanceApi.createPerformance, createPerformanceAction);
export const startPerformanceActionThunk = createRequestThunk(performanceApi.startPerformance, startPerformanceAction);
//성능측정 강제 중지 요청
export const forceStopPerformanceActionThunk = createRequestThunk(performanceApi.stopPerformance, stopPerformanceAction);
export const stopPerformanceActionThunk = () => (dispatch, getState) => {
    dispatch(stopPerformanceAction());
    dispatch(removeTopicAction(`/performance/${getState().performance.uuid}`));
    dispatch(removeTopicAction(`/performance/${getState().performance.uuid}/stop`));
}


const initialData = {
    uuid: null,
    run: false,
    jobList: [],
    data : [],
    countData : [],
    rpmData : [],
    rpmSum:{},
}

//측정 시간 순 정렬
const sortMeasureTime = (array = [], putItem) => {
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
const dataPreProcessing = (state, item) => {

    return {
        data : sortMeasureTime(state.data, item),
        countData :sortMeasureTime(state.countData, countDataPreProcessing(item)),
        rpmData :sortMeasureTime(state.rpmData, rpmDataPreProcessing(item)),
        rpmSum : rpmSumPreProcessing(state.rpmSum, item),
    }
}

/**
 * count정보 전처리
 * @param measureTime : Number
 * @param performanceTaskMeasureList : Array
 */
const countDataPreProcessing = ({measureTime, performanceTaskMeasureList}) => {
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
const rpmDataPreProcessing = ({measureTime, performanceTaskMeasureList}) => {
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
const rpmSumPreProcessing = (rpmSum, {performanceTaskMeasureList}) => {
    return performanceTaskMeasureList.reduce((obj, data) => ({...obj, [data.jobId] : elseGetZero(rpmSum[data.jobId])+ data.rpm}), {});
}

const elseGetZero = (num) => {
    return num || 0;
}


const performance = handleActions({
    [CREATE_PERFORMANCE]: (state, {payload}) => ({
        ...state,
        ["uuid"]: payload.data.data.uuid,
        ["jobList"]: payload.data.data.jobList,
        ["instance"]: payload.data.data.instance,
    }),
    [REMOVE_PERFORMANCE]: (state) => ({...initialData}),
    [START_PERFORMANCE] : (state) => ({...state, ["run"] : true}),
    [ADD_PERFORMANCE_DATA] : (state, {payload}) => ({...state, ...dataPreProcessing(state, payload)}),
    [STOP_PERFORMANCE] : (state, {payload}) => ({...state, ["run"] : false}),
}, initialData)

export default performance;