import {createAction, handleActions} from "redux-actions";

const SHOW = 'loading/SHOW';
const HIDE = 'loading/HIDE';

export const showLoadingAction = createAction(SHOW);
export const hideLoadingAction = createAction(HIDE);

const initialState = false;
const loading = handleActions({
    [SHOW]: () => true,
    [HIDE]: () => false,
}, initialState);

export default loading;