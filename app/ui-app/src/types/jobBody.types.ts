import {Job} from "./job.types";
import {LocalDateTime} from "./global.types";

export type JobBody = {
    id: number
    job: Job
    body: string
    afterDelay: number
    createdAt: LocalDateTime
    updateAt: LocalDateTime
    usable: boolean
}