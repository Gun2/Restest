import React from 'react';
import JobTemplate from "./components/JobTemplate";
import JobTop from "./components/JobTop";
import JobList from "./components/JobList";
import {useReadAllQuery} from "modules/job";

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