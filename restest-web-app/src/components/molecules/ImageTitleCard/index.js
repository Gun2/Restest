import React from 'react';
import styled from 'styled-components';
import Title from "../../atoms/Title";


const Box = styled.div`
    ${({theme}) => theme.flex.aroundCenter};
    border-radius : 5px;
    width : 500px;
    height : 200px;
    background-color: ${({theme, bgColor}) => bgColor || theme.palette.panel};
`;

function ImageTitleCard({image, text, bgColor}) {
    return (
        <Box bgColor={bgColor}>
            <div>
                {image}
            </div>
            <div>
                <Title text={text} fontSize={40}/>
            </div>
        </Box>
    );
}

export default ImageTitleCard;