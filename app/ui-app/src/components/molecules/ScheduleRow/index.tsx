import React from 'react';
import Title from "../../atoms/Title";
import ScheduleContent from "../ScheduleContent";
import Switch from "../../atoms/Switch"
import StatusLabel from "../../atoms/StatusLabel";
import theme from "../../../theme";
import OpenRow from "../OpenRow";
import {useRunMutation} from "modules/schedule";
import {Schedule} from "@_types/schedule.types";
import {SchedulerState} from "@_types/scheduler.types";

type ScheduleRowProps = {
    title: string;
    data: Schedule;
    schedulerStateInfo?: SchedulerState | null;
    onSaveCallback: () => void;
    onDeleteCallback: () => void;
    onClickSuccessIcon?: () => void;
    onClickFailureIcon?: () => void;
    _key: Schedule["id"];

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
    }: ScheduleRowProps
) {
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