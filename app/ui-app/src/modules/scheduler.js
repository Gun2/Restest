import {createAction, handleActions} from 'redux-actions'

const SCHEDULER_UPDATE = 'scheduler/info/UPDATE';
const SCHEDULER_INSERT = 'scheduler/info/INSERT';
const SCHEDULER_DELETE = 'scheduler/info/DELETE';
const SCHEDULER_INIT = 'scheduler/info/INIT';

export const schedulerUpdate = createAction(SCHEDULER_UPDATE);
export const schedulerInsert = createAction(SCHEDULER_INSERT);
export const schedulerDelete = createAction(SCHEDULER_DELETE);
export const schedulerInit = createAction(SCHEDULER_INIT);

const initialState = [];

const scheduler = handleActions(
    {
        [SCHEDULER_UPDATE] : (state, {payload}) => state.map( s => s.id === payload.id ? payload : s),
        [SCHEDULER_INSERT] : (state, {payload}) => [...state, payload],
        [SCHEDULER_DELETE] : (state, {payload}) => state.filter(s => s.id !== payload),
        [SCHEDULER_INIT] : (state, {payload}) => [...payload],
    },
    initialState
)

export default scheduler;