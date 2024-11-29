import React from 'react';
import styled from "styled-components";

const Box = styled.div`
    color : ${({theme}) => theme.palette.text.default};
    text-align:start;
`;
const Ul = styled.ul`
    list-style:none;
    margin:5px;
`
const Li = styled.li`
    display:flex;
`

const Title = styled.div`
    font-size:${({titleFontSize}) => titleFontSize}px;
    font-weight:bold;
    margin-left:10px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;

`

const Bold = styled.div`
    font-weight:bold;
    `;

/**
 * object의 empty여부 반환
 * @param obj : Object
 * @return {boolean|boolean} 빈 값이면 ture 반환
 */
const objectIsEmpty = (obj) => {
    if(obj instanceof Object){
       return Object.keys(obj).length === 0 ? true : false;
    }else{
        return true;
    }
}
const KeyValueText = ({title, titleFontSize=18, keyValue = {}, value}) => {
    return (
        <Box>
            {
                (!objectIsEmpty(keyValue) || value) &&
                <>
                    <Title titleFontSize={titleFontSize}>
                        {title}
                    </Title>
                    <Ul>
                        {
                            Object.keys(keyValue).map(key => <Li key={key}><Bold>{key}</Bold> : {keyValue[key]}</Li>)
                        }
                        {
                            <Li>{value}</Li>
                        }
                    </Ul>
                </>
            }
        </Box>
    );
};

export default KeyValueText;