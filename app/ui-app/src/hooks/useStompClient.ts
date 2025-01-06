import {WebSocketClientFactory} from "utils/webSocketClientFactory";
import {useState} from "react";
import {Client} from "@stomp/stompjs";


export const useStompClient = () => {

    const instance = WebSocketClientFactory.getInstance();
    const [client, setClient] = useState<Client | undefined>();
    instance.getClient().then(value => {
        setClient(value);
    })

    return client;

}