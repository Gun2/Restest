import {SuccessResponse} from "../types/api.types";
import {subscribeWebSocket} from "./utils/streamingUpdateUtil";
import {ChangeData} from "../types/changeData.types";
import {Schedule, ScheduleRequest, ScheduleRunRequest} from "../types/schedule.types";
import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";

export const scheduleApi = createApi({
    reducerPath: 'scheduleApi',
    baseQuery: fetchBaseQuery({ baseUrl: '/api/v1/schedules' }),
    endpoints: (builder) => ({
        readAll: builder.query<SuccessResponse<Schedule[]>, void>({
            query: () => ``,
            async onCacheEntryAdded(
                arg,
                api,
            ) {
                await subscribeWebSocket(api, '/change-data-spreader/schedule', message => {
                    if (message?.body){
                        const changeData = JSON.parse(message?.body) as ChangeData<Schedule, Schedule["id"]>;
                        api.updateCachedData(draft => {
                            switch (changeData.type) {
                                case "CREATE":
                                    draft.data = [changeData.data, ...draft.data];
                                    break;
                                case "UPDATE":
                                    draft.data = draft.data.map(schedule => schedule.id === changeData.data.id ? changeData.data : schedule);
                                    break;
                                case "DELETE":
                                    draft.data = draft.data.filter(schedule => schedule.id !== changeData.data);
                                    break;
                            }
                        })
                    }else {
                        console.warn(`response data is not number message: ${message}`)
                    }
                });
            },
        }),
        update: builder.mutation<SuccessResponse<Schedule>, ScheduleRequest>({
            query: (arg) => ({
                url: '',
                method: "PUT",
                body: arg
            })
        }),
        create: builder.mutation<SuccessResponse<Schedule>, ScheduleRequest>({
            query: (arg) => ({
                url: "",
                method: "POST",
                body: arg,
            })
        }),
        deleteById: builder.mutation< SuccessResponse<boolean>, Schedule["id"]>({
            query: id => ({
                url: `/${id}`,
                method: "DELETE",
            })
        }),
        run: builder.mutation< SuccessResponse<Schedule>, ScheduleRunRequest>({
            query: arg => ({
                url: `/run`,
                method: "PUT",
                body: arg,
            })
        }),

    }),
});


export const {
    useReadAllQuery,
    useCreateMutation,
    useUpdateMutation,
    useDeleteByIdMutation,
    useRunMutation,
} = scheduleApi