import React from 'react';
import styled from "styled-components";
import JobRow from "../../molecules/JobRow";
import ScheduleRow from "../../molecules/ScheduleRow";
import {useSelector} from "react-redux";

const Box = styled.div`
    
`
const List = styled.div`
    display: flex;
    flex-direction : column;
    gap:5px;
    padding 10px;
`

function ScheduleList({
                          data,
                          getData,
                      }) {
    const schedulerStateInfoList = useSelector(state => state.scheduler);

    return (
        <Box>
            <List>
                {
                    data.map((d,i) => {
                        const schedulerStateInfo = schedulerStateInfoList.find(s => s.id == d.id);
                        return (
                            <ScheduleRow
                                title={d.title}
                                schedulerStateInfo={schedulerStateInfo}
                                data={d}
                                onSaveCallback={() => {
                                    getData();
                                }}
                                onDeleteCallback={() => {
                                    getData();
                                }}
                                key={d.id}
                                _key={d.id}
                            />
                        )
                    })
                }
            </List>
        </Box>    );
}

export default ScheduleList;