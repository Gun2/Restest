import {configureStore} from '@reduxjs/toolkit'
import schedulerSuccessLogReducer from "modules/schedulerSuccessLog"
import schedulerFailureLogReducer from "modules/schedulerFailureLog"
import dialogReducer from "modules/dialog"
import alertModalReducer from "modules/alertModal"
import validationMessageReducer from "modules/validationMessage"
import loadingReducer from "modules/loading"
import schedulerReducer, {schedulerApi} from "modules/scheduler";

import {systemInformationApi} from "modules/sysInfo"
import {performanceApi, performanceSlice} from 'modules/performance'
import {jobApi} from "modules/job";
import {setupListeners} from '@reduxjs/toolkit/query'
import {loadingMiddleware} from "modules/middlewares/loadingMiddleware";
import {scheduleApi} from "modules/schedule";
import {performanceSettingApi} from "modules/performanceSetting";


const store = configureStore({
    reducer: {
        /* client */
        schedulerSuccessLog: schedulerSuccessLogReducer,
        schedulerFailureLog: schedulerFailureLogReducer,
        dialog: dialogReducer,
        alertModal: alertModalReducer,
        performance: performanceSlice.reducer,
        validationMessage: validationMessageReducer,
        loading: loadingReducer,
        scheduler: schedulerReducer,

        /* server */
        [systemInformationApi.reducerPath]: systemInformationApi.reducer,
        [performanceApi.reducerPath]: performanceApi.reducer,
        [jobApi.reducerPath]: jobApi.reducer,
        [scheduleApi.reducerPath]: scheduleApi.reducer,
        [schedulerApi.reducerPath]: schedulerApi.reducer,
        [performanceSettingApi.reducerPath]: performanceSettingApi.reducer,
    },
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware().concat(
            systemInformationApi.middleware,
            performanceApi.middleware,
            jobApi.middleware,
            scheduleApi.middleware,
            schedulerApi.middleware,
            performanceSettingApi.middleware,
            loadingMiddleware.middleware,
        ),
})

setupListeners(store.dispatch);

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch

export default store;