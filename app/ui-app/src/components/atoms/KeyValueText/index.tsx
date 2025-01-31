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

const Title = styled.div<{$titleFontSize: number}>`
    font-size:${({$titleFontSize}) => $titleFontSize}px;
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
const objectIsEmpty = (obj : Record<any, any>) => {
    return Object.keys(obj).length === 0;
}

type KeyValueTextProps = {
    title: string;
    titleFontSize?: number;
    keyValue: Record<string, string | number>;
    value?: React.ReactNode
}
/**
 * key value 형식을 리스트 형태로 출력하는 컴포넌트
 * @param title 제목
 * @param titleFontSize 제목 크기
 * @param keyValue key, value 값 (key, value가 같은 행에서 문자열로 출력됨)
 * @param value 값
 * @constructor
 */
const KeyValueText = (
    {
        title,
        titleFontSize=18,
        keyValue = {},
        value
    }: KeyValueTextProps
) => {
    return (
        <Box>
            {
                (!objectIsEmpty(keyValue) || value) &&
                <>
                    <Title $titleFontSize={titleFontSize}>
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