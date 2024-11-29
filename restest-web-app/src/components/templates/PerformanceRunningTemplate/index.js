import React from 'react';
import styled from "styled-components";

const Box = styled.div`
    ${({theme}) => theme.flex.center};
    flex-wrap:wrap;
    gap:50px;
    color : ${({theme}) => theme.palette.text.default}
`;


const RunningContain = styled.div`
    ${({theme}) => theme.flex.center};
    flex-direction:column;
    min-width: 600px;
    align-items:stretch;
    gap:20px;
    padding : 30px;
    flex-direction:column;
`;

const CardBox = styled.div`
     ${({theme}) => theme.flex.center};
     gap: 20px;
     flex-wrap:wrap;
`

const ChartBox = styled.div`
     ${({theme}) => theme.flex.center};
     justify-content: center;
     flex-wrap:wrap;
     gap:20px;
     
`
const ControlBox = styled.div`
     ${({theme}) => theme.flex.center};
`
const PerformanceSettingTemplate = ({
                                        cardArea,
                                        chartArea,
                                        controlArea
                                    }) => {
    return (
        <Box>
            <RunningContain>
                <CardBox>
                    {cardArea}
                </CardBox>
                <ChartBox>
                    {chartArea}
                </ChartBox>
                <ControlBox>
                    {controlArea}
                </ControlBox>
            </RunningContain>
        </Box>
    );
};

export default PerformanceSettingTemplate;