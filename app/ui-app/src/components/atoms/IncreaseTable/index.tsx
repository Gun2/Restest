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

function trIncreaseCheck(data : Array<any>, cols: Array<Column>) {
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
function addNextEmptyTr<T>(data: Array<T>, cols: Array<Column>): Array<T> {
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
function IncreaseTable<T extends Object>(
    {
        cols = [],
        data = [],
        onChange,
        readonly
    }: IncreaseTable<T>) {

    useEffect(() => {
        if (trIncreaseCheck(data, cols)) {
            onChange(addNextEmptyTr(data, cols));
        }
    }, [cols, data, onChange]);

    //TODO: 코드 리펙토링 필요
    const onInputChange = useCallback(({ target }: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, index: number) => {
        const { name, value } = target;
        const keys = cols.map(({ key }) => key);
        let updatedData = data.map((d, i) => i === index ? { ...d, [name]: value } : d)
            .filter(d => keys.some(key => d?.hasOwnProperty(key)));

        if (trIncreaseCheck(updatedData, cols)) {
            updatedData = addNextEmptyTr(updatedData, cols);
        }

        onChange(updatedData);
    }, [cols, data, onChange]);

    const onCheckChange = useCallback(({ target }: React.ChangeEvent<HTMLInputElement>, index: number) => {
        var {name, checked} = target;
        var keys = cols.map(({key}) => key);
        var emptyRemovedData = data.map((d, i) =>
            i === index ? {
                ...d,
                [name]: checked
            } : d
        ).filter(d => keys.some(key => d?.hasOwnProperty(key)));
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