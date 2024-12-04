import {JobParam} from "./jobParam.types";
import {JobHeader} from "./jobHeader.types";
import {JobBody} from "./jobBody.types";
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
    "DELETE"
