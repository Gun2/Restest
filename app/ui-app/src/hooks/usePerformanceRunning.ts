import {useCallback, useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../store";
import {useStompClient} from "hooks/useStompClient";
import {
    create,
    PerformanceState,
    remove,
    stop,
    useStartMutation,
    useStopMutation,
    addData,
    start
} from "modules/performance";
import {Performance} from "types/performance.types";
import {StompSubscription} from "@stomp/stompjs";
import {PerformanceData} from "@_types/performanceData.types";

type UsePerformanceRunningArgs = {
}
/**
 * 성능측정 hook
 */
export const usePerformanceRunning = (
    {
    }: UsePerformanceRunningArgs = {}
) => {
    const [uuid, setUuid] = useState<PerformanceState["uuid"]>(null);
    const [currentResultStreamingSubscribe, setCurrentResultStreamingSubscribe] = useState<StompSubscription | undefined>();
    const [currentStopStreamingSubscribe, setCurrentStopStreamingSubscribe] = useState<StompSubscription | undefined>();
    const dispatch = useDispatch();
    const [startPerformance] = useStartMutation();
    const [stopPerformance] = useStopMutation();

    const performance: PerformanceState = useSelector((state: RootState) => state.performance);
    const stompClient = useStompClient();

    const init = useCallback((schedulerId: number) => {
        dispatch(remove());
    }, [])

    const forceStop = useCallback(() => {
        if (uuid){
            stopPerformance({
                uuid: uuid
            }).then(r => {
                dispatch(stop());
            })
        }
    }, [uuid])
    /**
     * stomp가 연결되었을 때 changeUuid 실행
     */
    useEffect(() => {
        if(uuid && stompClient){
            changeUuid(uuid);
        }
    }, [stompClient]);
    const changeUuid = useCallback((uuid: PerformanceState["uuid"]) => {
        setUuid(prevState => {
            if (stompClient){
                if (currentResultStreamingSubscribe){
                    stompClient.unsubscribe(currentResultStreamingSubscribe.id);
                }
                if (currentStopStreamingSubscribe){
                    //이전 구독 삭제
                    stompClient.unsubscribe(currentStopStreamingSubscribe.id);
                }
                if (uuid != null) {
                    //성능 측정 시작 요청
                    startPerformance({
                        uuid: uuid
                    }).then(r => {
                        dispatch(start())
                    })
                    //성능 측정 결과 구독
                    const resultStreamingSubscription = stompClient.subscribe(
                        getResultStreamingTopic(uuid),
                        message => {
                            const performanceDto = JSON.parse(message.body) as PerformanceData;
                            dispatch(addData(performanceDto));
                        }
                    );
                    setCurrentResultStreamingSubscribe(resultStreamingSubscription);
                    //성능 측정 중지 구독
                    const stopStreamingSubscription = stompClient.subscribe(
                        getStopStreamingTopic(uuid),
                        message => {
                            dispatch(stop());
                        }
                    );
                    setCurrentStopStreamingSubscribe(stopStreamingSubscription);
                }
            }
            return uuid;
        });
    }, [stompClient, currentResultStreamingSubscribe, currentStopStreamingSubscribe]);


    return {
        //성능 측정 상태
        data: performance,
        //성능 측정 정보 초기화
        init,
        //성능 측정중 동작 취소
        forceStop,
        //uuid 변경
        changeUuid
    }
}

//성능 측정 결과 수집 토픽 반환
const getResultStreamingTopic = (uuid : string) : string => `/performance/${uuid}`
//성능 측정 중지 토픽 반환

const getStopStreamingTopic = (uuid : string) : string => `/performance/${uuid}/stop`