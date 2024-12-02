import {createAction, handleActions} from "redux-actions";
import {createDialogThunk} from "../lib/thunkCreator";
import schedulerApi from "../api/schedulerApi";
import {showDialogAction} from "./dialog";
import {addTopicAction, removeTopicAction} from "./topic";

const READ_ALL = "schedulerFailureLog/readAll";
const ADD = "schedulerFailureLog/ADD";
const SET_TOPIC = "schedulerFailureLog/SET_TOPIC";


const readAllAction = createAction(READ_ALL);
const setFailureTopicAction = createAction(SET_TOPIC);
export const failureLogAddAction = createAction(ADD);

export const failureLogReadAllThunk = createDialogThunk(schedulerApi.readAllResponseFailure, readAllAction, showDialogAction('schedulerFailureLog'));

export const failureLogDialogThunk = ({id}) => (dispatch, getState) => {
    dispatch(failureLogReadAllThunk({param:id}));
    const preTopic = getState().schedulerFailureLog.topic?.payload;
    if(preTopic){
        dispatch(removeTopicAction(preTopic));
    }
    const topic = `/scheduler/${id}/failure`;
    dispatch(setFailureTopicAction(topic));
    dispatch(addTopicAction({
        topic : topic,
        actionCallback : (r) => failureLogAddAction(r),
    }));
}
const schedulerFailureLog = handleActions({
    [READ_ALL] : (state, {payload}) => ({...state, data : [...payload.data.data]}),
    [ADD] : (state, {payload}) => ({...state, data : [...state.data, payload]}),
    [SET_TOPIC] : (state, {payload}) => ({...state, topic: {payload}}),
}, {
    data : [],
    topic : null,
})

export default schedulerFailureLog;