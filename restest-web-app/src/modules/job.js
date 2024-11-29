import {createAction, handleActions} from "redux-actions";
import jobApi from "../api/jobApi";
import {createRequestThunk, createChangeDataSpreaderThunk} from "../lib/thunkCreator";

const CREATE = 'job/CREATE';
const READ_ALL = 'job/READ_ALL';
const DELETE_BY_ID = 'job/DELETE_BY_ID';
const UPDATE = 'job/UPDATE';

const jobCreateRequestAction = createAction(CREATE, response => response.data.data);
const jobCreateAction = createAction(CREATE);
const jobReadAllAction = createAction(READ_ALL, response => response.data.data);
const jobDeleteRequestByIdAction = createAction(DELETE_BY_ID, ({id}) => id);
const jobDeleteByIdAction = createAction(DELETE_BY_ID);
const jobUpdateAction = createAction(UPDATE);


export const jobCreateThunk = createRequestThunk(jobApi.create, jobCreateRequestAction);
export const jobUpdateThunk = createRequestThunk(jobApi.update, jobUpdateAction);
export const jobDeleteThunk = createRequestThunk(jobApi.deleteById, jobDeleteRequestByIdAction);
export const jobReadAllThunk = createRequestThunk(jobApi.readAll, jobReadAllAction);
export const jobChangeDataSpreaderThunk = createChangeDataSpreaderThunk(jobCreateAction, jobUpdateAction, jobDeleteByIdAction);


const initialState = [];

const job = handleActions({
    [CREATE]: (state, {payload}) => state.some(({id}) => id === payload.id) ? [...state] : [payload, ...state],
    [READ_ALL]: (state, {payload}) => [...payload],
    [DELETE_BY_ID]: (state, {payload}) => state.filter(s => s.id !== payload),
    [UPDATE]: (state, {payload}) => state.map(s => s.id === payload.id ? payload : s)
}, initialState);

export default job;