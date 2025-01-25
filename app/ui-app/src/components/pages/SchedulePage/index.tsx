import React from 'react';
import ScheduleTemplate from "../../templates/ScheduleTemplate";
import ScheduleTop from "../../organisms/ScheduleTop";
import ScheduleList from "../../organisms/ScheduleList";

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