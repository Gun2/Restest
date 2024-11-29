import React from 'react';

import styled from 'styled-components'
import {Dna} from "react-loader-spinner";
const Box = styled.div`
    ${({theme}) => theme.backgroundCover};
`

const Font = styled.p`
    color : ${({theme}) => theme.palette.text.primary};
`

function LoadingBox({show = false}) {
    return (
        <>
            {show &&
                <Box>
                    <Dna
                        visible={true}
                        height="80"
                        width="80"
                        ariaLabel="dna-loading"
                        wrapperStyle={{}}
                        wrapperClass="dna-wrapper"
                    />
                    <Font>
                        loading...
                    </Font>
                </Box>
            }
        </>
    );
}

export default LoadingBox;