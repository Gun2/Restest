import React from 'react';
import JobCard from "../../molecules/JobCard";
import {Job} from "@_types/job.types";
import {PerformanceData, PerformanceTaskMeasure} from "@_types/performanceData.types";


/**
 * job id에 해당하는 performanceData 반환
 * @param jobId
 * @param data
 */
const getJobData = (jobId: Job["id"], data: PerformanceData[] = []) : PerformanceTaskMeasure | undefined => {
    var lastIndex = data.length - 1;
    if (lastIndex >= 0) {
        return data[lastIndex].performanceTaskMeasureList.find(d => d.jobId === jobId);
    } else {
        return undefined;
    }
}
type JobCardListProps = {
    jobList: Job[];
    data: PerformanceData[];
}
const JobCardList = (
    {
        jobList = [],
        data = []
    }: JobCardListProps
) => {
    return (
        <>
            {
                jobList.map((job) => {
                    const jobData = getJobData(job.id, data);
                    return (
                        <JobCard
                            key={job.id}
                            color={job.color}
                            title={job.title}
                            maxTime={jobData ? jobData.maxTime : 0}
                            minTime={jobData ? jobData.minTime : 0}
                            totalCnt={jobData ? jobData.cnt : 0}
                            successCnt={jobData ? jobData.successCnt : 0}
                            failureCnt={jobData ? jobData.failureCnt : 0}
                        />
                    )
                })
            }
        </>
    );
};

export default JobCardList;