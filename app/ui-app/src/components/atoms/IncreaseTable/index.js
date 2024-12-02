import React, {useCallback, useEffect} from 'react';
import styled from "styled-components";
import ReadOnlyText from "../ReadOnlyText";

const Box = styled.div`
    background-color : ${({theme}) => theme.palette.primary};
`;

const Table = styled.table`
    width:100%;
`

const Tr = styled.tr`
    height: 20px;
`

const Td = styled.td`
    background-color : ${({theme}) => theme.palette.text.default};
`

const Th = styled.th`
`

const TdInput = styled.input`
    width : 95%;
    ${({theme, readonly}) => readonly && theme.style.readonly}
`
const TdTextArea = styled.textarea`
    width : 95%;
    height:200px;
    ${({theme, readonly}) => readonly && theme.style.readonly}
`

const TdCheckBox = styled.input`
    ${({theme, readonly}) => readonly && theme.style.readonly}
`

function trIncreaseCheck(data, cols) {
    if (data.length === 0) return true;
    var lastData = data[data.length - 1];
    var trIncrease = cols.some(({key, increaseIgnore}) => !increaseIgnore && lastData[key]);
    return trIncrease;
}

/**
 * 공백 tr을 추가하여 반환
 * @param data tr data
 * @param cols col 정보
 * @return {*[]}
 */
function addNextEmptyTr(data, cols) {
    return [
        ...data,
        cols.reduce((pre, col) => {
            pre[col.key] = col.default ? col.default : "";
            return pre;
        }, {})
    ]
}

function IncreaseTable({
                           cols = [],
                           data = [],
                           onChange,
                           readonly
                       }) {

    useEffect(() => {
        if (trIncreaseCheck(data, cols)) {
            onChange(addNextEmptyTr(data, cols));
        }
    }, [cols, data, onChange]);


    const onInputChange = useCallback(({target}, index) => {
        var {name, value} = target;
        var keys = cols.map(({key}) => key);
        var emptyRemovedData = data.map((d, i) =>
            i === index ? {
                ...d,
                [name]: value
            } : d
        ).filter(d => keys.some(key => d[key]));
        if (trIncreaseCheck(emptyRemovedData, cols)) {
            emptyRemovedData = addNextEmptyTr(emptyRemovedData, cols)
        }
        onChange(emptyRemovedData);
    }, [cols, data, onChange]);

    const onCheckChange = useCallback(({target}, index) => {
        var {name, checked} = target;
        var keys = cols.map(({key}) => key);
        var emptyRemovedData = data.map((d, i) =>
            i === index ? {
                ...d,
                [name]: checked
            } : d
        ).filter(d => keys.some(key => d[key]));
        if (trIncreaseCheck(emptyRemovedData, cols)) {
            emptyRemovedData = addNextEmptyTr(emptyRemovedData, cols)
        }
        onChange(emptyRemovedData);
    }, [cols, data, onChange]);

    return (
        <Box>
            <Table>
                <tbody>
                <Tr key={0}>
                    {cols.map(({key, name}) => (
                        <Th key={key}>{name}</Th>
                    ))}
                </Tr>
                {data.map((d, i) => (
                    <Tr key={i}>
                        {cols.map(({type, key}) => (
                            <Td key={key + i}>
                                {(() => {
                                    switch (type) {
                                        case "input":
                                            return (
                                                <TdInput
                                                    name={key}
                                                    value={d[key] ?? ''}
                                                    onChange={(e) => onInputChange(e, i)}
                                                    readonly={readonly}
                                                />
                                            )
                                        case "textarea" :
                                            return (
                                                readonly ?
                                                    <ReadOnlyText text={d[key]}/> :
                                                    <TdTextArea
                                                        name={key}
                                                        value={d[key]}
                                                        onChange={(e) => onInputChange(e, i)}
                                                        readonly={readonly}
                                                    />
                                            )
                                        case "checkbox" :
                                            return (
                                                <TdCheckBox
                                                    type={"checkbox"}
                                                    name={key}
                                                    checked={d[key]}
                                                    onChange={(e) => onCheckChange(e, i)}
                                                    readonly={readonly}
                                                />
                                            )
                                    }
                                })()
                                }
                            </Td>
                        ))}
                    </Tr>
                ))}
                </tbody>
            </Table>
        </Box>
    );
}

export default IncreaseTable;