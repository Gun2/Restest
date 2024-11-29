import React from 'react';
import styled from "styled-components";

const Box = styled.div`
    ${({theme}) => theme.flex.center};
    height:100vh;
    gap:50px;
    color : ${({theme}) => theme.palette.text.default}
`;


const SettingContain = styled.div`
    ${({theme}) => theme.flex.center};
    flex-direction:column;
    min-width: 600px;
    width:80%;
    height: 300px;
    align-items:stretch;
    gap:5px;
`;

const TopBox = styled.div`
    display:flex;
    justify-content: space-between;
`

const JobListBox = styled.div`
    max-height:600px;
    overflow-y:auto;
`
const PerformanceSettingTemplate = ({
                                        instanceArea,
                                        jobCountArea,
                                        jobListArea,
                                        controlArea
                                    }) => {
    return (
        <Box>
            <SettingContain>
                <TopBox>
                    <div>
                        {instanceArea}
                    </div>
                    <div>
                        {jobCountArea}
                    </div>
                </TopBox>
                <div>
                    <JobListBox>
                        {jobListArea}
                    </JobListBox>
                </div>
                <div>
                    {controlArea}
                </div>
            </SettingContain>
        </Box>
    );
};

export default PerformanceSettingTemplate;