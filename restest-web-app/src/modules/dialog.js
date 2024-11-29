import {createAction, handleActions} from "redux-actions";

const SHOW_DIALOG = 'dialog/SHOW_DIALOG';
const HIDE_DIALOG = 'dialog/HIDE_DIALOG';

export const showDialogAction = createAction(SHOW_DIALOG, id => id);
export const hideDialogAction = createAction(HIDE_DIALOG, id => id);

const dialog = handleActions({
    [SHOW_DIALOG] : (state, {payload}) => ({...state, [payload] : true}),
    [HIDE_DIALOG] : (state, {payload}) => Object.keys(state)
        .filter(key => key != payload)
        .reduce((result, key) => ({...result, [key]:state[key]}), {}),
}, {});

export default dialog;