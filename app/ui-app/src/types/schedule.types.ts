import {Job} from "@_types/job.types";


export type Schedule = {
    id: number;
    title: string;
    delay: number;
    run: boolean;
    jobIdList: Array<Job["id"]>;
    jobList: Job[];
    createdAt: string;
    updateAt: string
}

export type ScheduleRequest = Pick<Schedule, "title" | "delay" | "run" | "jobList" | "jobIdList"> & {
    id?: number
}

export type ScheduleRunRequest = {
    id: Schedule["id"];
    run: boolean;
}