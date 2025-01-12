import {useCallback, useEffect, useState} from "react";
import {readAllResponseFailure} from "apis/schedulerApi";
import {useDispatch, useSelector} from "react-redux";
import {add, addAll, clear, setTopic} from "modules/schedulerFailureLog";
import {RootState} from "../store";
import {HttpResponse} from "types/httpResponse.types";
import {DIALOG_ID, show} from "modules/dialog";
import {useStompClient} from "hooks/useStompClient";
import {StompSubscription} from "@stomp/stompjs";

type UseScheduleFailureDialogArgs = {
    schedulerId: number
}
/**
 * 스케줄러 실패 로그 다이얼로그 생성/삭제 hook
 */
export const useScheduleFailureDialog = (
    {
    }: UseScheduleFailureDialogArgs = {}
) => {
    const schedulerFailureLog = useSelector((state: RootState) => state.schedulerFailureLog);
    const [stompSubscription, setStompSubscription] = useState<StompSubscription>();
    const stompClient = useStompClient();

    const dispatch = useDispatch();
    const prepareLogsBySchedulerId = useCallback((schedulerId : number) => {
        if (schedulerId){
            readAllResponseFailure(schedulerId).then(response => {
                dispatch(addAll(response.data.data))
                dispatch(setTopic(`/scheduler/${schedulerId}/failure`))
            });
        }
    }, []);

    const showModal = useCallback((schedulerId: number) => {
        prepareLogsBySchedulerId(schedulerId);
        dispatch(show(DIALOG_ID.SCHEDULER_FAILURE_LOG))
    }, [])

    const hideModal = useCallback(() => {
        dispatch(clear());
    }, [])

    useEffect(() => {
        if (stompClient){
            if (stompSubscription){
                stompClient.unsubscribe(stompSubscription.id);
            }
            if (schedulerFailureLog.topic){
                const stompSubscription = stompClient.subscribe(
                    schedulerFailureLog.topic,
                    message => {
                        const log = JSON.parse(message.body) as HttpResponse;
                        dispatch(add(log));
                    });
                setStompSubscription(stompSubscription);
            }
        }
    }, [schedulerFailureLog.topic]);


    return {
        showModal,
        hideModal,
    }
}