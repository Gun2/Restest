import React from 'react';
import styled from "styled-components";
import Button from "../../atoms/Button";

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
type JobTemplateProps = {
    //상단 영역
    top: React.ReactNode,
    //리스트 영역
    list: React.ReactNode
}
function JobTemplate(
    {
        top,
        list
    }: JobTemplateProps
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

export default JobTemplate;