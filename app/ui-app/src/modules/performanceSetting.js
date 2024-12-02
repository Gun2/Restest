import {createAction, handleActions} from "redux-actions";
import {createRequestThunk} from "../lib/thunkCreator";
import performanceApi from "../api/performanceApi";

const READ_MAX_JOB = 'performance/READ_MAX_JOB';
const READ_MAX_INSTANCE = 'performance/READ_MAX_INSTANCE';

const readMaxJobAction = createAction(READ_MAX_JOB);
const readMaxInstanceAction = createAction(READ_MAX_INSTANCE);

export const readMaxJobThunk = createRequestThunk(performanceApi.readMaxJob,readMaxJobAction);
export const readMaxInstanceThunk = createRequestThunk(performanceApi.readMaxInstance,readMaxInstanceAction);



const initialData = {
    maxJob : 0,
    maxInstance : 0,
}

const performanceSetting = handleActions({
    [READ_MAX_JOB] : (state, {payload}) => ({...state, ["maxJob"] : payload.data.data}),
    [READ_MAX_INSTANCE] : (state, {payload}) => ({...state, ["maxInstance"] : payload.data.data}),
},initialData)

export default performanceSetting;