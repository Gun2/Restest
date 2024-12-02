import {failureUpdate, successUpdate, userUpdate} from "./sysInfo";
import {jobChangeDataSpreaderThunk} from "./job";
import {scheduleChangeDataSpreaderThunk} from "./schedule";
import {schedulerDelete, schedulerInit, schedulerInsert, schedulerUpdate} from "./scheduler";
import {createAction, handleActions} from "redux-actions";

const ADD_TOPIC = 'topic/ADD_TOPIC';
const REMOVE_TOPIC = 'topic/REMOVE_TOPIC';

export const addTopicAction = createAction(ADD_TOPIC);
export const removeTopicAction = createAction(REMOVE_TOPIC);

const initTopic = {
    '/sys-info/user': r => userUpdate(r),
    '/sys-info/success': r => successUpdate(r),
    '/sys-info/failure': r => failureUpdate(r),
    '/change-data-spreader/job': r => jobChangeDataSpreaderThunk(r),
    '/change-data-spreader/schedule': r => scheduleChangeDataSpreaderThunk(r),
    '/scheduler/update' : r => schedulerUpdate(r),
    '/scheduler/init' : r => schedulerInit(r),
    '/scheduler/insert' : r => schedulerInsert(r),
    '/scheduler/delete' : r => schedulerDelete(r),
}

const topic = handleActions({
    [ADD_TOPIC] : (state, {payload : {topic, actionCallback}}) => ({...state, [topic] : actionCallback}),
    [REMOVE_TOPIC] : (state, {payload}) => Object.keys(state)
        .filter(key => key != payload)
        .reduce((result, key) => ({...result, [key] : state[key]}), {}),
}, initTopic)

export default topic;