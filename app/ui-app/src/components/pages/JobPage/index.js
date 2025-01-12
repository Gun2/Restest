import React from 'react';
import JobTemplate from "../../templates/JobTemplate";
import JobTop from "../../organisms/JobTop";
import JobList from "../../organisms/JobList";
import {useReadAllQuery} from "../../../modules/job.ts";

function JobPage() {
    const {data, error, isLoading, refetch} = useReadAllQuery();
    const onSaveCallback = () => {
        refetch();
    }
    return (
        <JobTemplate
            top={
                <JobTop
                    onSaveCallback={onSaveCallback}
                />
            }
            list={
                <JobList
                    hideCheckBox
                />
            }
        />
    );
}

export default JobPage;