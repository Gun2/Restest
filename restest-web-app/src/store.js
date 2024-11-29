import {applyMiddleware, compose, createStore} from "redux";
import rootReducer from "./modules";
import ReduxThunk from 'redux-thunk';

const store = createStore(rootReducer,
    (() => {
        if(window.__REDUX_DEVTOOLS_EXTENSION__){
            return compose(applyMiddleware(ReduxThunk), window.__REDUX_DEVTOOLS_EXTENSION__())
        }else{
            return compose(applyMiddleware(ReduxThunk))
        }
    })());

export default store;