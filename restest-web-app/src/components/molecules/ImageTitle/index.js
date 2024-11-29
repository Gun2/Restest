import React from 'react';
import styled from 'styled-components';
import Title from '../../atoms/Title';

const Box = styled.div`
    ${({theme}) => theme.flex.aroundCenter};
    height : 100%;
`


function ImageTitle({image, text}) {
    return (
        <Box>
            <div>
                {image}
            </div>
            <div>
                <Title text={text} fontSize={"40px"}/>
            </div>
        </Box>
    );
}

export default ImageTitle;