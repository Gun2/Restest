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