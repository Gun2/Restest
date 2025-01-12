import {Schedule} from "@_types/schedule.types";

export type SchedulerInfo = {
    scheduleDto: Schedule;
    successCount: number;
    failureCount: number;
    lastTime: number;

}

export type SchedulerState = {
    id: number;
    success: number;
    failure: number;
    lastTime: number;
}