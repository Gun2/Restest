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

export type JobBodyCreateOrUpdateRequest = Pick<JobBody, "body" | "afterDelay" | "usable"> & {
    id?: JobBody["id"];
    jobId?: Job["id"];
}