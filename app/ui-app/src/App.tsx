import './App.css';
import styled, {ThemeProvider} from 'styled-components';
import theme from './theme';

// @ts-ignore
import LayoutPage from './components/pages/LayoutPage';
import {BrowserRouter} from 'react-router-dom';

import React, {useRef} from "react";

const Box = styled.div`
  background-color : ${({theme}) => theme.palette.background};
  min-height : 100vh;
`;

function App() {
    const socketRef = useRef();
    return (
        <BrowserRouter>
            <ThemeProvider theme={theme}>
                <Box className="App">
                    <LayoutPage/>
                </Box>
            </ThemeProvider>
        </BrowserRouter>


    );
}

export default App;
