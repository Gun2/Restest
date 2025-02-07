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

const TdInput = styled.input<{$readonly?: boolean}>`
    width : 95%;
    ${({theme, $readonly}) => $readonly && theme.style.readonly}
`
const TdTextArea = styled.textarea<{$readonly?: boolean}>`
    width : 95%;
    height:200px;
    ${({theme, $readonly}) => $readonly && theme.style.readonly}
`

const TdCheckBox = styled.input<{$readonly?: boolean}>`
    ${({theme, $readonly}) => $readonly && theme.style.readonly}
`

function needIncreasingRow(data : Array<any>, cols: Array<Column>) {
    if (data.length === 0) return true;
    var lastData = data[data.length - 1];
    var trIncrease = cols.some(({key, increaseIgnore}) => !increaseIgnore && lastData[key]);
    return trIncrease;
}

//비어있는 row 제거
function filterEmptyRow<T extends Record<Column["key"], any>>(cols: Column[], data: T[]) {
    const keys = cols.map(({key}) => key);
    return  data.filter(data => keys.some(key => data[key]));
}

/**
 * 공백 row 추가
 * @param data tr data
 * @param cols col 정보
 * @return {*[]}
 */
function addEmptyRow<T>(data: Array<T>, cols: Array<Column>): Array<T> {
    return [
        ...data,
        cols.reduce((pre, col) => {
            //@ts-ignore
            pre[col.key] = col.default ?? "";
            return pre;
        }, {} as T)
    ]
}

type Column = {
    // 컬럼의 고유 키 (예: "usable", "body")
    key: string;
    // 컬럼 이름 (예: "Body", "Usable")
    name: string;
    // 컬럼의 입력 타입
    type: "checkbox" | "input" | "textarea";
    // 해당 컬럼이 증가하지 않도록 무시하는지 여부
    increaseIgnore?: boolean;
    // 컬럼의 기본값 (선택사항)
    default?: string | boolean;
};


type IncreaseTable<T> = {
    // 테이블의 컬럼 정보
    cols: Column[];
    // 테이블에 표시될 데이터
    data: T[];
    // 데이터 변경 시 호출되는 함수
    onChange: (updatedData: T[]) => void;
    //읽기 모드
    readonly?: boolean;
}

/**
 * 비어있는 row를 제거하고, 필요 시 row를 추가함
 * @param cols
 * @param rows
 */
function filterEmptyRowAndIncreaseRowIfNeed<T>(cols: Column[], rows: (T & { [p: string]: string })[]) {
    let filteredData = filterEmptyRow(cols, rows);
    if (needIncreasingRow(filteredData, cols)) {
        filteredData = addEmptyRow(filteredData, cols);
    }
    return filteredData;
}

/**
 * row에 값 입력 시 row가 증가하고, 값 제거 시 row가 삭제되는 테이블
 */
function IncreaseTable<T extends Record<Column["key"], any>>(
    {
        cols = [],
        data = [],
        onChange,
        readonly
    }: IncreaseTable<T>) {

    useEffect(() => {
        if (needIncreasingRow(data, cols)) {
            onChange(addEmptyRow(data, cols));
        }
    }, [cols, data, onChange]);

    const onInputChange = useCallback(({ target }: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, index: number) => {
        const { name, value } = target;
        const updatedData = data.map((d, i) => i === index ? { ...d, [name]: value } : d);

        const processedData = filterEmptyRowAndIncreaseRowIfNeed(cols, updatedData);
        onChange(processedData);
    }, [cols, data, onChange]);

    const onCheckChange = useCallback(({ target }: React.ChangeEvent<HTMLInputElement>, index: number) => {
        var {name, checked} = target;
        var updatedData = data.map((d, i) =>
            i === index ? {
                ...d,
                [name]: checked
            } : d
        );

        const processedData = filterEmptyRowAndIncreaseRowIfNeed(cols, updatedData);
        onChange(processedData);
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
                                                    //@ts-ignore
                                                    value={d[key] ?? ''}
                                                    onChange={(e) => onInputChange(e, i)}
                                                    $readonly={readonly}
                                                />
                                            )
                                        case "textarea" :
                                            return (
                                                readonly ?
                                                    //@ts-ignore
                                                    <ReadOnlyText text={d[key]}/> :
                                                    <TdTextArea
                                                        name={key}
                                                        //@ts-ignore
                                                        value={d[key]}
                                                        onChange={(e) => onInputChange(e, i)}
                                                        $readonly={readonly}
                                                    />
                                            )
                                        case "checkbox" :
                                            return (
                                                <TdCheckBox
                                                    type={"checkbox"}
                                                    name={key}
                                                    //@ts-ignore
                                                    checked={d[key]}
                                                    onChange={(e) => onCheckChange(e, i)}
                                                    $readonly={readonly}
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