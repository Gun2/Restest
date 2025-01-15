import {Job} from "./job.types";
import {LocalDateTime} from "./global.types";

export type JobParam = {
    id : number
    job: Job
    keyName: string
    value: string
    description: string
    createdAt: LocalDateTime
    updateAt: LocalDateTime
    usable: boolean

}

export type JobParamCreateOrUpdateRequest = Pick<JobParam, "keyName" | "value" | "description" | "usable"> & {
    id?: JobParam["id"];
    jobId?: Job["id"];
}