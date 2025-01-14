import React from 'react';
import styled from 'styled-components';
import Title from "../../atoms/Title";


const Box = styled.div<{$bgColor?: string}>`
    ${({theme}) => theme.flex.aroundCenter};
    border-radius : 5px;
    width : 500px;
    height : 200px;
    background-color: ${({theme, $bgColor}) => $bgColor || theme.palette.panel};
`;
type ImageTitleCardProps = {
    image: React.ReactNode,
    text: React.ReactNode,
    bgColor: string,
}
function ImageTitleCard(
    {
        image,
        text,
        bgColor
    }
    : ImageTitleCardProps
) {
    return (
        <Box $bgColor={bgColor}>
            <div>
                {image}
            </div>
            <div>
                <Title
                    text={text}
                    fontSize={40}
                />
            </div>
        </Box>
    );
}

export default ImageTitleCard;