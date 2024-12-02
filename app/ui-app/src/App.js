import './App.css';
import styled, {ThemeProvider} from 'styled-components';
import theme from './theme';
import LayoutPage from './components/pages/LayoutPage';
import {BrowserRouter} from 'react-router-dom';
import SockJsClient from "react-stomp";
import React, {useMemo, useRef} from "react";
import {useDispatch, useSelector} from "react-redux";

const Box = styled.div`
  background-color : ${({theme}) => theme.palette.background};
  min-height : 100vh;
`;

export const SocketContext = React.createContext(null);

function App() {
    const socketRef = useRef();
    const dispatch = useDispatch();
    const topic = useSelector(store => store.topic);
    const topicAction = useMemo(() => (topic), [topic]);
    return (
        <BrowserRouter>
            <SockJsClient
                ref={socketRef}
                url={`${process.env.REACT_APP_API_URL}/ws`}

                //getRetryInterval={1}
                topics={Object.keys(topicAction)}
                onMessage={(response, topic) => {
                    //console.log(`response : ${response}, topic : ${topic}`);
                    dispatch(topicAction[topic](response));
                }}
                onConnect={(...args) => {
                    const init = () => {
                        if (socketRef.current.state.connected) {
                            socketRef.current.sendMessage('/app/sys-info/user/init', 'search', {});
                            socketRef.current.sendMessage('/app/sys-info/success/init', 'search', {});
                            socketRef.current.sendMessage('/app/sys-info/failure/init', 'search', {});
                            socketRef.current.sendMessage('/app/scheduler/init', 'search', {});
                        } else {
                            setTimeout(init, 500);
                        }
                    }
                    init();
                }}
                onDisconnect={(...args) => {
                    console.log('disconnect');
                    console.log(args);
                }}
                autoReconnect
            />
            <ThemeProvider theme={theme}>
                <SocketContext.Provider value={socketRef}>
                    <Box className="App">
                        <LayoutPage/>
                    </Box>
                </SocketContext.Provider>
            </ThemeProvider>
        </BrowserRouter>


    );
}

export default App;
