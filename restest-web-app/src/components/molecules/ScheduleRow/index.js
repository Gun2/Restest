import React from 'react';
import Title from "../../atoms/Title";
import ScheduleContent from "../ScheduleContent";
import Switch from "../../atoms/Switch"
import axios from "axios";
import StatusLabel from "../../atoms/StatusLabel";
import theme from "../../../theme";
import OpenRow from "../OpenRow";
import {failureLogDialogThunk} from "../../../modules/schedulerFailureLog";
import {useDispatch} from "react-redux";
import {successLogDialogThunk} from "../../../modules/schedulerSuccessLog";

const initData = {
    title: "",
    delay: "1000",
    jobList: [],
}


function ScheduleRow({
                         title,
                         data,
                         schedulerStateInfo,
                         onSaveCallback,
                         onDeleteCallback,
                         _key,
                     }) {
    const dispatch = useDispatch();
    return (
        <OpenRow
            head={
                <Title color={"#e4e4e4"}>
                    {title}
                </Title>
            }
            tail={
                <>
                    {
                        schedulerStateInfo &&
                        <>
                            <Title fontSize={12} color={theme.palette.text.default}>{schedulerStateInfo.lastTime}ms</Title>
                            <StatusLabel
                                status={"success"}
                                label={schedulerStateInfo.success}
                                onClick={() => {
                                    dispatch(successLogDialogThunk({id:data.id}));
                                }}
                            />
                            <StatusLabel
                                status={"failure"}
                                label={schedulerStateInfo.failure}
                                onClick={() => {
                                    dispatch(failureLogDialogThunk({id:data.id}));
                                }}
                            />
                        </>


                    }
                    <Switch
                        checked={schedulerStateInfo != null}
                        onChange={({checked}) => {
                            axios.put('/api/v1/schedules/run', {
                                id: _key,
                                run: checked
                            }).then(r => {
                                onSaveCallback();
                            }).catch(e => {
                                console.error(e)
                            })
                        }}
                    />
                </>

            }
            content={
                <ScheduleContent
                    data={data}
                    showDeleteBtn
                    showSaveBtn
                    onSaveCallback={() => {
                        //onToggle();
                        onSaveCallback();
                    }}
                    onDeleteCallback={() => {
                        //onToggle();
                        onDeleteCallback();
                    }}
                />
            }
        />
    );
}

export default ScheduleRow;