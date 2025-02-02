import React from 'react';
import JobTemplate from "components/templates/JobTemplate";
import JobTop from "../../organisms/JobTop";
import JobList from "components/organisms/JobList";
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