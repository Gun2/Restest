import React from 'react';
import styled from 'styled-components';

const Box = styled.div`
    ${({theme}) => theme.flex.aroundCenter};
    height : 100vh;
    flex-wrap: wrap;
`;

function DashBoardTeemplate({topLeft, topCenter, topRight}) {
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

export default DashBoardTeemplate;