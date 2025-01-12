import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";
import {SuccessResponse} from "@_types/api.types";
import {subscribeWebSocket} from "./utils/streamingUpdateUtil";

type SysInfoState = {
    user: number;
    success: number;
    failure: number;
};

const initialState: SysInfoState = {
    user: 0,
    success: 0,
    failure: 0,
};

export const systemInformationApi = createApi({
    reducerPath: "system-information",
    baseQuery: fetchBaseQuery({baseUrl: "/api/v1/system-information"}),
    endpoints: (build) => ({
        readUser: build.query< SuccessResponse<number>, void>({
            query: () => ({
                url: '/users'
            }),
            async onCacheEntryAdded(
                arg,
                api,
            ) {
                await subscribeWebSocket(api, '/sys-info/user', message => {
                    if (message?.body){
                        const users: number = message?.body as unknown as number;
                        api.updateCachedData(draft => {
                            draft.data = users;
                        })
                    }else {
                        console.warn(`response data is not number message: ${message}`)
                    }
                });
            },
        }),
        readSuccessCount: build.query< SuccessResponse<number>, void>({
            query: () => ({
                url: '/success-count'
            }),
            async onCacheEntryAdded(
                arg,
                api,
            ) {
                await subscribeWebSocket(api, '/sys-info/success', message => {
                    if (message?.body){
                        const users: number = message?.body as unknown as number;
                        api.updateCachedData(draft => {
                            draft.data = users;
                        })
                    }else {
                        console.warn(`response data is not number message: ${message}`)
                    }
                });
            },
        }),
        readFailureCount: build.query< SuccessResponse<number>, void>({
            query: () => ({
                url: '/failure-count'
            }),
            async onCacheEntryAdded(
                arg,
                api,
            ) {
                await subscribeWebSocket(api, '/sys-info/failure', message => {
                    if (message?.body){
                        const users: number = message?.body as unknown as number;
                        api.updateCachedData(draft => {
                            draft.data = users;
                        })
                    }else {
                        console.warn(`response data is not number message: ${message}`)
                    }
                });
            },
        }),

    })
})

export const {
    useReadUserQuery,
    useReadSuccessCountQuery,
    useReadFailureCountQuery
} = systemInformationApi;