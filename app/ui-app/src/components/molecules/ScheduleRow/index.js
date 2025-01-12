import React from 'react';
import Title from "../../atoms/Title";
import ScheduleContent from "../ScheduleContent";
import Switch from "../../atoms/Switch"
import axios from "axios";
import StatusLabel from "../../atoms/StatusLabel";
import theme from "../../../theme";
import OpenRow from "../OpenRow";
import {useDispatch} from "react-redux";
import {useRunMutation} from "modules/schedule";

const initData = {
    title: "",
    delay: "1000",
    jobList: [],
}


function ScheduleRow(
    {
        title,
        data,
        schedulerStateInfo,
        onSaveCallback,
        onDeleteCallback,
        onClickSuccessIcon,
        onClickFailureIcon,
        _key,
    }) {
    const dispatch = useDispatch();
    const [runTrigger] = useRunMutation();
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
                                    onClickSuccessIcon && onClickSuccessIcon();
                                }}
                            />
                            <StatusLabel
                                status={"failure"}
                                label={schedulerStateInfo.failure}
                                onClick={() => {
                                    onClickFailureIcon && onClickFailureIcon();
                                }}
                            />
                        </>


                    }
                    <Switch
                        checked={schedulerStateInfo != null}
                        onChange={({checked}) => {
                            runTrigger({
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