import {createAction, handleActions} from 'redux-actions'

const USER_UPDATE = 'sysInfo/user/UPDATE';
const SUCCESS_UPDATE = 'sysInfo/success/UPDATE';
const FAILURE_UPDATE = 'sysInfo/failure/UPDATE';

export const userUpdate = createAction(USER_UPDATE);
export const successUpdate = createAction(SUCCESS_UPDATE);
export const failureUpdate = createAction(FAILURE_UPDATE);

const initialState = {
    user : 0,
    success : 0,
    failure : 0,
}

const sysInfo = handleActions(
    {
        [USER_UPDATE] : (state, {payload}) => ({
            ...state,
            user: payload
        }),
        [SUCCESS_UPDATE] : (state, {payload}) => ({
            ...state,
            success: payload
        }),
        [FAILURE_UPDATE] : (state, {payload}) => ({
            ...state,
            failure: payload
        }),
    },
    initialState
)

export default sysInfo;