import {createAction, handleActions} from "redux-actions";

const ADD_ALERT = 'alert/ADD_ALERT';
const REMOVE_ALERT = 'alert/REMOVE_ALERT';

export const addAlertAction = createAction(ADD_ALERT);
export const removeAlertAction = createAction(REMOVE_ALERT, key => key);

const initialValue = [];
let key = 1;

const alertModal = handleActions({
    [ADD_ALERT] : (state, {payload}) => [...state, {
        key : key++,
        text : payload.message
    }],
    [REMOVE_ALERT] : (state, {payload}) => state.filter(state => state.key != payload)
}, initialValue)

export default alertModal;