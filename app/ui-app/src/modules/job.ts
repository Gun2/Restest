import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'
import {Job, JobCreateOrUpdateRequest} from "../types/job.types";
import {SuccessResponse} from "../types/api.types";
import {createSlice} from "@reduxjs/toolkit";
import {subscribeWebSocket} from "./utils/streamingUpdateUtil";
import {ChangeData} from "types/changeData.types";


export const jobApi = createApi({
    reducerPath: 'jobApi',
    baseQuery: fetchBaseQuery({ baseUrl: '/api/v1/jobs' }),
    endpoints: (builder) => ({
        readAll: builder.query<SuccessResponse<Job[]>, void>({
            query: () => ``,
            async onCacheEntryAdded(
                arg,
                api,
            ) {
                await subscribeWebSocket(api, '/change-data-spreader/job', message => {
                    if (message?.body){
                        const changeData = JSON.parse(message?.body) as ChangeData<Job, Job["id"]>;
                        api.updateCachedData(draft => {
                            switch (changeData.type) {
                                case "CREATE":
                                    draft.data = [changeData.data, ...draft.data];
                                    break;
                                case "UPDATE":
                                    draft.data = draft.data.map(job => job.id === changeData.data.id ? changeData.data : job);
                                    break;
                                case "DELETE":
                                    draft.data = draft.data.filter(job => job.id !== changeData.data );
                                    break;
                            }
                        })
                    }else {
                        console.warn(`response data is not number message: ${message}`)
                    }
                });
            },
        }),
        update: builder.mutation<SuccessResponse<Job>, JobCreateOrUpdateRequest>({
            query: (arg) => ({
                url: '',
                method: "PUT",
                body: arg
            })
        }),
        create: builder.mutation<SuccessResponse<Job>, JobCreateOrUpdateRequest>({
            query: (arg) => ({
                url: "",
                method: "POST",
                body: arg,
            })
        }),
        deleteById: builder.mutation< SuccessResponse<boolean>, Job["id"]>({
            query: id => ({
                url: `/${id}`,
                method: "DELETE",
            })
        })
    }),
});
const initialState: Job[] = [];
createSlice({
    name: "job",
    initialState,
    reducers: {

    }
})

export const {
    useReadAllQuery,
    useCreateMutation,
    useUpdateMutation,
    useDeleteByIdMutation
} = jobApi;