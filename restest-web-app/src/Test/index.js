import React, {useContext} from 'react';
import Button from '../components/atoms/Button';
import {SocketContext} from "../App";
import SchedulePage from "../components/pages/SchedulePage";

function Test(props) {
    console.log(useContext(SocketContext));
    return (
        <div>
            <Button form='primary'>
                primary
            </Button>
            <Button form='primary' disabled>primary</Button>
            <Button form='danger'>danger</Button>
            <Button form='danger' disabled>danger</Button>
            <Button form='warning'>warning</Button>
            <Button form='warning' disabled>warning</Button>
            <Button form='info'>info</Button>
            <Button form='info' disabled>info</Button>
            <Button form='success'>success</Button>
            <Button form='success' disabled>success</Button>
            <Button form='default'>default</Button>
            <Button form='default' disabled>default</Button>



            <SchedulePage/>

        </div>

    );
}

export default Test;