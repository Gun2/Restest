import {useCallback, useEffect, useState} from "react";
import {readAllResponseSuccess} from "apis/schedulerApi";
import {useDispatch, useSelector} from "react-redux";
import {add, addAll, clear, setTopic} from "modules/schedulerSuccessLog";
import {RootState} from "../store";
import {useStompClient} from "hooks/useStompClient";
import {HttpResponse} from "types/httpResponse.types";
import {DIALOG_ID, show} from "modules/dialog";
import {StompSubscription} from "@stomp/stompjs";

type UseScheduleSuccessDialogArgs = {
    schedulerId: number
}
/**
 * 스케줄러 성공 로그 다이얼로그 생성/삭제 hook
 */
export const useScheduleSuccessDialog = (
    {
    }: UseScheduleSuccessDialogArgs = {}
) => {
    const schedulerSuccessLog = useSelector((state: RootState) => state.schedulerSuccessLog);
    const [stompSubscription, setStompSubscription] = useState<StompSubscription>();
    const stompClient = useStompClient();

    const dispatch = useDispatch();
    const prepareLogsBySchedulerId = useCallback((schedulerId : number) => {
        if (schedulerId){
            readAllResponseSuccess(schedulerId).then(response => {
                dispatch(addAll(response.data.data))
                dispatch(setTopic(`/scheduler/${schedulerId}/success`))
            });
        }
    }, []);

    const showModal = useCallback((schedulerId: number) => {
        prepareLogsBySchedulerId(schedulerId);
        dispatch(show(DIALOG_ID.SCHEDULER_SUCCESS_LOG));
    }, [])

    const hideModal = useCallback(() => {
        dispatch(clear());
    }, [])

    useEffect(() => {
        if (stompClient){
            if (stompSubscription){
                stompClient.unsubscribe(stompSubscription.id);
            }
            if (schedulerSuccessLog.topic){
                const stompSubscription = stompClient.subscribe(
                    schedulerSuccessLog.topic,
                    message => {
                        const log = JSON.parse(message.body) as HttpResponse;
                        dispatch(add(log));
                    });
                setStompSubscription(stompSubscription)
            }
        }
    }, [schedulerSuccessLog.topic]);

    return {
        showModal,
        hideModal,
    }
}