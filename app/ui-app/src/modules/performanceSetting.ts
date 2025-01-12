import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";
import {SuccessResponse} from "types/api.types.js";

export const performanceSettingApi = createApi({
    reducerPath: 'performanceSettingApi',
    baseQuery: fetchBaseQuery({baseUrl: '/api/v1/performances'}),
    endpoints: (builder) => ({
        readMaxJob: builder.query<SuccessResponse<number>, void>({
            query: () => `/max-job`,
        }),
        readMaxInstance: builder.query<SuccessResponse<number>, void>({
            query: () => `/max-instance`,
        }),
    })
})

export const {
    useReadMaxJobQuery,
    useReadMaxInstanceQuery,
} = performanceSettingApi;