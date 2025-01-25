import React from 'react';
import styled from "styled-components";

const Box = styled.div`
    margin: 20px 5px;
    display:flex;
    flex-direction : column;
    gap:5px;
`;

const Top = styled.div`

`

const Content = styled.div`
    
`

type ScheduleTemplateProps = {
    top: React.ReactNode;
    list: React.ReactNode;
}
function ScheduleTemplate(
    {
        top,
        list
    }: ScheduleTemplateProps
) {
    return (
        <Box>
            <Top>
                {top}
            </Top>
            <Content>
                {list}
            </Content>
        </Box>
    );
}

export default ScheduleTemplate;