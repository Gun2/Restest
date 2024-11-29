import React from 'react';
import styled from "styled-components";
import Rect from "../../atoms/Rect";
import Title from "../../atoms/Title";
import KeyValueText from "../../atoms/KeyValueText";

const Box = styled.div`
    width:300px;
    height:160px;
    background-color:${({theme}) => theme.palette.panel};
    padding:10px;
    overflow: hidden;
    box-shadow: 0px 0px 2px 0 #0d0d0d;
`;

const BoxContain = styled.div`
    ${({theme}) => theme.flex.startCenter};
    height:100%;
    gap:10px;
`

const InfoBox = styled.div`
    flex:1;
    ${({theme}) => theme.flex.startCenter};
    height: 100%;
`
const JobCard = ({color, title, maxTime, minTime, totalCnt, successCnt, failureCnt}) => {
    return (
        <Box>
            <BoxContain>
                <Rect height={"full"} color={color}/>
                <InfoBox>
                    <KeyValueText
                        title={title}
                        titleFontSize={30}
                        keyValue={{
                            "최대 요청시간" : `${maxTime}ms`,
                            "최소 요청시간" : `${minTime}ms`,
                            "총 요청 횟수" : totalCnt,
                            "요청 성공 횟수" : successCnt,
                            "요청 실패 횟수" : failureCnt,
                    }}
                    />
                </InfoBox>
            </BoxContain>
        </Box>
    );
};

export default JobCard;