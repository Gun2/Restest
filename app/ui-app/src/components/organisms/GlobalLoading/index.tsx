import React from 'react';
import {useSelector} from "react-redux";
import LoadingBox from "../../molecules/LoadingBox";
import {RootState} from "store";

const GlobalLoading = () => {
    const loadingShow = useSelector((state: RootState)  => state.loading);
    return (
        <LoadingBox show={loadingShow}></LoadingBox>
    );
};

export default GlobalLoading;