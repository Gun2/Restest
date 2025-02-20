import React from 'react';
import DraggableDialog from "../../../../components/organisms/DraggableDialog";
import OpenRow from "../../../../components/molecules/OpenRow";
import StatusBox from "../../../../components/atoms/StatusBox";
import Title from "../../../../components/atoms/Title";
import {strSliceAt, toSystemDateFormat} from "utils/stringFormatter";
import styled, {useTheme} from "styled-components";
import KeyValueText from "../../../../components/atoms/KeyValueText";
import {HttpResponse} from "types/httpResponse.types";

const RowBox = styled.div`
    margin-bottom: 10px;
`;
const TimeBox = styled.div`
    ${({theme}) => theme.flex.endCenter};
`;
type LogDialogProps = {
    //log 고유값
    id: string;
    //Log data
    data: HttpResponse[];
    //다이얼로그 제목
    title: string;
    //다이얼로그 위치
    startPoint: {x: number, y: number};
    onClick: (id: string) => void;
}
const LogDialog = (
    {
        id,
        data,
        title,
        startPoint,
        onClick
    }: LogDialogProps
) => {
    const theme = useTheme();
    return (
        <div onClick={() => {onClick(id)}}>
            <DraggableDialog title={title} id={id} startPoint={startPoint}>
                {
                    data.map((d, i) => (
                        <RowBox key={d.recordTime}>
                            <OpenRow
                                head={
                                    <>
                                        <StatusBox status={d.status}/>
                                        <Title text={d.method} color={theme.palette.text.primary}/>
                                        <Title text={strSliceAt(d.url, 50)}
                                               color={theme.palette.text.default}/>
                                    </>
                                }
                                tail={
                                    <>
                                        <Title text={`${d.time}ms`} color={theme.palette.text.default}
                                               fontSize={16}/>
                                    </>
                                }
                                content={
                                    <>
                                        <KeyValueText
                                            title={"[일반]"}
                                            keyValue={{
                                                "요청 URL": d.url,
                                                "요청 method": d.method,
                                                "상태코드": d.status,
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
                                <Title fontSize={10} text={toSystemDateFormat(d.recordTime)}
                                       color={theme.palette.text.default}/>
                            </TimeBox>
                        </RowBox>
                    ))
                }
            </DraggableDialog>
        </div>
    );
};

export default LogDialog;