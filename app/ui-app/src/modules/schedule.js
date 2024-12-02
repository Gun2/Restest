import {createAction, handleActions} from "redux-actions";
import scheduleApi from "../api/scheduleApi";
import {createRequestThunk, createChangeDataSpreaderThunk} from "../lib/thunkCreator";

const CREATE = 'schedule/CREATE';
const READ_ALL = 'schedule/READ_ALL';
const DELETE_BY_ID = 'schedule/DELETE_BY_ID';
const UPDATE = 'schedule/UPDATE';

const scheduleCreateRequestAction = createAction(CREATE, response => response.data.data);
const scheduleCreateAction = createAction(CREATE);
const scheduleReadAllAction = createAction(READ_ALL, response => response.data.data);
const scheduleDeleteRequestByIdAction = createAction(DELETE_BY_ID, ({id}) => id);
const scheduleDeleteByIdAction = createAction(DELETE_BY_ID);
const scheduleUpdateAction = createAction(UPDATE);


export const scheduleCreateThunk = createRequestThunk(scheduleApi.create, scheduleCreateRequestAction);
export const scheduleUpdateThunk = createRequestThunk(scheduleApi.update, scheduleUpdateAction);
export const scheduleDeleteThunk = createRequestThunk(scheduleApi.deleteById, scheduleDeleteRequestByIdAction);
export const scheduleReadAllThunk = createRequestThunk(scheduleApi.readAll, scheduleReadAllAction);
export const scheduleChangeDataSpreaderThunk = createChangeDataSpreaderThunk(scheduleCreateAction, scheduleUpdateAction, scheduleDeleteByIdAction);


const initialState = [];

const schedule = handleActions({
    [CREATE]: (state, {payload}) => state.some(({id}) => id === payload.id) ? [...state] : [payload, ...state],
    [READ_ALL]: (state, {payload}) => [...payload],
    [DELETE_BY_ID]: (state, {payload}) => state.filter(s => s.id !== payload),
    [UPDATE]: (state, {payload}) => state.map(s => s.id === payload.id ? payload : s)
}, initialState);

export default schedule;