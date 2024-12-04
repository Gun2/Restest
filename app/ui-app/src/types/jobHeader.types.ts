import {Job} from "./job.types";

export type JobHeader = {
    id: number
    job: Job
    keyName: string
    value: string
    description: string
    createdAt: Date
    updateAt: Date
    usable: boolean
}