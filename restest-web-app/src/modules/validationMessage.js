import {createAction, handleActions} from "redux-actions";

const SET_MESSAGE = 'validationMessage/SET_MESSAGE';
const REMOVE_MESSAGE = 'validationMessage/REMOVE_MESSAGE';

export const setMessageAction = createAction(SET_MESSAGE);
export const removeMessageAction = createAction(REMOVE_MESSAGE);

const initialState = {};

const validationMessage = handleActions({
    [SET_MESSAGE] : (state, {payload}) => ({...state, [payload.group] : {errors : payload.errors}}),
    [REMOVE_MESSAGE] : (state, {payload}) => Object.keys(state)
        .filter(key => key != payload)
        .reduce((result, key) => [...result, state[key]], {})
},initialState);

export default validationMessage;