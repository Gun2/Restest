import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {Provider} from "react-redux";
import store from "./store";
import {DevSupport} from "@react-buddy/ide-toolbox";
//import {ComponentPreviews, useInitial} from "./dev";

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);
const htmlTitle = document.querySelector("title");
if (htmlTitle){
    htmlTitle.innerText = 'RESTEST';
}
root.render(
    <React.StrictMode>
        <Provider store={store}>
            <App/>
            {/*<DevSupport ComponentPreviews={ComponentPreviews}
                        useInitialHook={useInitial}
            >
            </DevSupport>*/}
        </Provider>
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
