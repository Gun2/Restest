import React, {useEffect, useRef, useState} from 'react';
import DraggableDialog from "../DraggableDialog";
import OpenRow from "../../molecules/OpenRow";
import StatusBox from "../../atoms/StatusBox";
import Title from "../../atoms/Title";
import {strSliceAt, toSystemDateFormat} from "../../../lib/StringFormatter";
import styled from "styled-components";
import KeyValueText from "../../atoms/KeyValueText";
import {useSelector} from "react-redux";
import store from "../../../store";

const RowBox = styled.div`
    margin-bottom: 10px;
`;
const TimeBox = styled.div`
    ${({theme}) => theme.flex.endCenter};
`;
const LogDialog = ({id, data, title, startPoint, onClick}) => {
    return (
        <div onClick={() => {onClick(id)}}>
            <DraggableDialog title={title} id={id} startPoint={startPoint}>
                {
                    data.map((d, i) => <RowBox key={d.recordTime}>
                        <OpenRow
                            head={
                                <>
                                    <StatusBox status={d.status}/>
                                    <Title text={d.method} color={({theme})=>theme.palette.text.primary}/>
                                    <Title text={strSliceAt(d.url, 50)} color={({theme})=>theme.palette.text.default}/>
                                </>
                            }
                            tail={
                                <>
                                    <Title text={`${d.time}ms`} color={({theme})=>theme.palette.text.default} fontSize={16}/>
                                </>
                            }
                            content={
                                <>
                                    <KeyValueText
                                        title={"[일반]"}
                                        keyValue={{
                                            "요청 URL" : d.url,
                                            "요청 method" : d.method,
                                            "상태코드" : d.status,
                                        }}
                                    />
                                    <KeyValueText
                                        title={"[요청헤더]"}
                                        keyValue={d.requestHeaderMap}
                                    />
                                    <KeyValueText
                                        title={"[Payload]"}
                                        value={d.requestBody}
                                    />
                                    <KeyValueText
                                        title={"[응답헤더]"}
                                        keyValue={d.responseHeaderMap}
                                    />
                                    <KeyValueText
                                        title={"[응답]"}
                                        value={d.responseBody}
                                    />
                                </>
                            }
                        />
                        <TimeBox>
                            <Title fontSize={10} text={toSystemDateFormat(d.recordTime)} color={({theme}) => theme.palette.text.default}/>
                        </TimeBox>
                    </RowBox>)
                }
            </DraggableDialog>
        </div>
    );
};

export default LogDialog;