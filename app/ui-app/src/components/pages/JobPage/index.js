import React, {useRef, useState, useEffect} from 'react';
import JobTemplate from "../../templates/JobTemplate";
import JobTop from "../../organisms/JobTop";
import JobList from "../../organisms/JobList";
import axios from "axios";
import {useDispatch, useSelector} from "react-redux";
import store from "../../../store";
import {jobReadAllThunk} from "../../../modules/job";

function JobPage() {
    const dispatch = useDispatch();
    const data = useSelector(store => store.job);
    const getData = () => {
        dispatch(jobReadAllThunk({}));
    }
    useEffect(() => {
        getData();
    }, []);
    const onSaveCallback = () => {
        getData();
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
                    data={data}
                    getData={getData}
                    hideCheckBox
                />
            }
        />
    );
}

export default JobPage;