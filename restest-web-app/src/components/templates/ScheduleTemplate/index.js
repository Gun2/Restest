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
function ScheduleTemplate({top, list}) {
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