import React from 'react';
import ScheduleTemplate from "./components/ScheduleTemplate";
import ScheduleTop from "./components/ScheduleTop";
import ScheduleList from "./components/ScheduleList";

function SchedulePage() {
    return (
        <ScheduleTemplate
            top={
                <ScheduleTop/>
            }
            list={
                <ScheduleList/>
            }

        />
    );
}

export default SchedulePage;