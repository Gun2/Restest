import React from 'react';
import styled from 'styled-components';
import Title from '../../atoms/Title';

const Box = styled.div`
    ${({theme}) => theme.flex.aroundCenter};
    height : 100%;
`

type ImageTitleProps = {
    image: React.ReactNode;
    text: string;
}
function ImageTitle(
    {
        image,
        text
    }: ImageTitleProps
) {
    return (
        <Box>
            <div>
                {image}
            </div>
            <div>
                <Title text={text} fontSize={40}/>
            </div>
        </Box>
    );
}

export default ImageTitle;