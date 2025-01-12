import {WebSocketClientFactory} from "../../utils/webSocketClientFactory";

export async function subscribeWebSocket<T>(
    api: any,
    topic: string,
    messageProcessor: (message: any) => T
) {
    const {cacheDataLoaded, cacheEntryRemoved } = api;
    const instance = WebSocketClientFactory.getInstance();
    const client = await instance.getClient();

    try {
        // 초기 데이터가 로드될 때까지 대기
        await cacheDataLoaded;

        client.subscribe(topic, (message) => {
            messageProcessor(message);
        });
    } catch (e) {
        console.error(e);
    }

    // 캐시 항목이 제거될 때까지 대기
    await cacheEntryRemoved;
}
