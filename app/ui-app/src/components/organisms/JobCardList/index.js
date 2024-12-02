import React from 'react';
import JobCard from "../../molecules/JobCard";

const getJobData = (jobId, data = []) => {
    var lastIndex = data.length - 1;
    if (lastIndex >= 0) {
        return data[lastIndex].performanceTaskMeasureList.find(d => d.jobId == jobId);
    } else {
        return {};
    }
}
const JobCardList = ({jobList = [], data = []}) => {
    return (
        jobList.map((job) => {
            const jobData = getJobData(job.id, data);
            return <JobCard key={job.id} color={job.color} title={job.title} maxTime={jobData.maxTime}
                            minTime={jobData.minTime} totalCnt={jobData.cnt} successCnt={jobData.successCnt} failureCnt={jobData.failureCnt}/>
        })
    );
};

export default JobCardList;