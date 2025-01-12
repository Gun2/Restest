import {createListenerMiddleware} from '@reduxjs/toolkit'
import {hide, show} from "modules/loading";

export const loadingMiddleware = createListenerMiddleware()

loadingMiddleware.startListening({
    effect: async (action, api) => {
        const { dispatch } = api;
        if (action.type.endsWith('/pending')) {
            dispatch(show()); // 로딩 시작
        } else if (
            action.type.endsWith('/fulfilled') ||
            action.type.endsWith('/rejected')
        ) {
            dispatch(hide()); // 로딩 종료
        }
    },
    predicate: (action, currentState, originalState) => {
        return true;
    }
})