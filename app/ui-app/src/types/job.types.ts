import {JobParam, JobParamCreateOrUpdateRequest} from "./jobParam.types";
import {JobHeader, JobHeaderCreateOrUpdateRequest} from "./jobHeader.types";
import {JobBody, JobBodyCreateOrUpdateRequest} from "./jobBody.types";
import {LocalDateTime} from "./global.types";

export type Job = {
    id: number
    title: string
    method: Method
    url: string
    color: string
    jobParamList: JobParam[]
    jobHeaderList: JobHeader[]
    jobBodyList: JobBody[]
    createdAt: LocalDateTime
    updateAt: LocalDateTime
}

export type Method =
    "GET" |
    "POST" |
    "PUT" |
    "DELETE";

export type JobCreateOrUpdateRequest = Pick<Job, "title" | "method" | "url">& {
    id?: Job["id"];
    color?: string;
    jobParamList: Array<JobParamCreateOrUpdateRequest>;
    jobHeaderList: Array<JobHeaderCreateOrUpdateRequest>;
    jobBodyList: Array<JobBodyCreateOrUpdateRequest>;
}