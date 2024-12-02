import {hideLoadingAction, showLoadingAction} from "../modules/loading";
import {removeMessageAction, setMessageAction} from "../modules/validationMessage";
import {addAlertAction} from "../modules/alertModal";

/**
 *
 * @param api api call fn
 * @param action
 * @returns {function({param: *, validationGroup: *, successCallback: *, errorCallback: *}): function(*): Promise<void>}
 */
export const createRequestThunk =  (api, action) => ({param=null, validationGroup=null, successCallback=null, errorCallback=null}) => async dispatch => {
    dispatch(showLoadingAction());
    try{
        var result = await api(param);
        dispatch(action(result));
        if(typeof successCallback === 'function'){
            successCallback(result);
        }
        if(validationGroup){
            dispatch(removeMessageAction(validationGroup));
        }
    }catch (e){
        const data = e.response?.data;
        if(data){
            if(data.code === 'INVALID_INPUT_VALUE' && validationGroup){
                dispatch(setMessageAction({
                    group : validationGroup,
                    errors : data.errors,
                }));
            }else if(data.code === 'NOT_FOUND_BY_ID'){
                dispatch(addAlertAction(data));
            }
        }

        if(typeof errorCallback == 'function'){
            errorCallback(e);
        }
    }
    dispatch(hideLoadingAction());
}

export const createChangeDataSpreaderThunk = (createAction, updateAction, deleteAction) => ({type, data}) => dispatch => {
    if (type === "CREATE") {
        dispatch(createAction(data));
    } else if (type === "UPDATE") {
        dispatch(updateAction(data));
    } else if (type === "DELETE") {
        dispatch(deleteAction(data));
    }
}

export const createDialogThunk = (api, action, showDialogAction) => ({param, callback}) => async dispatch => {
    dispatch(showDialogAction);
    const response = await api(param);
    dispatch(action(response));
    if(typeof callback === 'function'){
        console.log(callback);
        callback();
    }
}