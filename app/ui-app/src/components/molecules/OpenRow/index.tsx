import React, {useCallback, useState, useReducer} from 'react';
import styled, {css} from "styled-components";
import DirectionToggle from "../../atoms/DirectionToggle";
import Title from "../../atoms/Title";
import JobContent from "../../../pages/JobPage/components/JobContent";
import CheckBox from "../../atoms/CheckBox";
import Rect from "../../atoms/Rect";

const Box = styled.div`
    background-color : ${({theme}) => theme.palette.panel};
    display:flex;
    flex-direction : column;
    padding : 5px;
    gap:5px;
}   
`;

const Row = styled.div`
    display: flex;
    padding: 0 10px;
`

const RowHead = styled.div`
    flex: 1;
    gap:5px;
    ${({theme}) => theme.flex.startCenter};
`

const RowTail = styled.div`
    ${({theme}) => theme.flex.startCenter};
`

const HiddenRow = styled.div`

`
type OpenRowProps = {
    head?: React.ReactNode;
    content?: React.ReactNode;
    tail?: React.ReactNode;
}
function OpenRow(
    {
        head,
        content,
        tail
    }: OpenRowProps
) {
    const [viewBody, setViewBody] = useState(false);
    const onToggle = useCallback((flag : number) => {
        setViewBody(flag != 1);
    }, [viewBody]);
    return (
        <Box >
            <Row>
                <RowHead>
                    {head}
                </RowHead>
                <RowTail>
                    {tail}
                    <DirectionToggle onToggle={onToggle} degree={90}/>
                </RowTail>
            </Row>
            {
                viewBody && (
                    <HiddenRow>
                        {content}
                    </HiddenRow>
                )
            }
        </Box>
    );
}

export default OpenRow;