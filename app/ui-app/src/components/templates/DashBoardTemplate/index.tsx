import React from 'react';
import styled from 'styled-components';

const Box = styled.div`
    ${({theme}) => theme.flex.aroundCenter};
    height : 100vh;
    flex-wrap: wrap;
`;

type DashBoardTemplateProps = {
    topLeft: React.ReactNode,
    topCenter: React.ReactNode,
    topRight: React.ReactNode
}

function DashBoardTemplate(
    {
        topLeft, topCenter, topRight
    }: DashBoardTemplateProps
) {
    return (
        <Box>
            <div>
                {topLeft}
            </div>
            <div>
                {topCenter}
            </div>
            <div>
                {topRight}
            </div>
        </Box>
    );
}

export default DashBoardTemplate;