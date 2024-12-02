import {createAction, handleActions} from "redux-actions";
import {createDialogThunk} from "../lib/thunkCreator";
import schedulerApi from "../api/schedulerApi";
import {showDialogAction} from "./dialog";
import {addTopicAction, removeTopicAction} from "./topic";

const READ_ALL = "schedulerSuccessLog/readAll";
const ADD = "schedulerSuccessLog/ADD";
const SET_TOPIC = "schedulerSuccessLog/SET_TOPIC";


const readAllAction = createAction(READ_ALL);
const setSuccessTopicAction = createAction(SET_TOPIC);
export const successLogAddAction = createAction(ADD);

export const successLogReadAllThunk = createDialogThunk(schedulerApi.readAllResponseSuccess, readAllAction, showDialogAction('schedulerSuccessLog'));

export const successLogDialogThunk = ({id}) => (dispatch, getState) => {
    dispatch(successLogReadAllThunk({param:id}));
    const preTopic = getState().schedulerSuccessLog.topic?.payload;
    if(preTopic){
        dispatch(removeTopicAction(preTopic));
    }
    const topic = `/scheduler/${id}/success`;
    dispatch(setSuccessTopicAction(topic));
    dispatch(addTopicAction({
        topic : topic,
        actionCallback : (r) => successLogAddAction(r),
    }));
}
const schedulerSuccessLog = handleActions({
    [READ_ALL] : (state, {payload}) => ({...state, data : [...payload.data.data]}),
    [ADD] : (state, {payload}) => ({...state, data : [...state.data, payload]}),
    [SET_TOPIC] : (state, {payload}) => ({...state, topic: {payload}}),
}, {
    data : [],
    topic : null,
})

export default schedulerSuccessLog;