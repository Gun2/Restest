import React, {useEffect} from 'react';
import ScheduleTemplate from "../../templates/ScheduleTemplate";
import ScheduleTop from "../../organisms/ScheduleTop";
import ScheduleList from "../../organisms/ScheduleList";
import {useDispatch, useSelector} from "react-redux";
import {scheduleReadAllThunk} from "../../../modules/schedule";
import {jobReadAllThunk} from "../../../modules/job";

function SchedulePage(props) {
    const data = useSelector(store => store.schedule);
    const dispatch = useDispatch();
    const getData = () => {
        dispatch(scheduleReadAllThunk({}));
        dispatch(jobReadAllThunk({}));
    }
    useEffect(() => {
        getData();
    }, []);
    return (
        <ScheduleTemplate
            top={
                <ScheduleTop/>
            }
            list={
                <ScheduleList
                    data={data}
                    getData={getData}
                />
            }

        />
    );
}

export default SchedulePage;