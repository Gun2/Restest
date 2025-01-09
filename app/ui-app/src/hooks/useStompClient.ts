import {WebSocketClientFactory} from "utils/webSocketClientFactory";
import {useEffect, useState} from "react";
import {Client} from "@stomp/stompjs";


export const useStompClient = () => {


    const [client, setClient] = useState<Client | undefined>();
    useEffect(() => {
        const instance = WebSocketClientFactory.getInstance();
        instance.getClient().then(value => {
            setClient(value);
        })
    }, []);
    return client;

}