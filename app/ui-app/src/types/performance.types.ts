import {Job} from "types/job.types";

/**
 * 성능측정 생성 요청
 */
export type PerformanceCreateRequest = {
    instance: number;
    jobIdList: Array<Job["id"]>
}

export type Performance = {
    instance: number;
    jobList: Job[];
    uuid: string;
}

export type PerformanceRequest = {
    uuid : string;
}