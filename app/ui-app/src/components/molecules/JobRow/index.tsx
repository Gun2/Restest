import React from 'react';
import styled, {useTheme} from "styled-components";
import Title from "../../atoms/Title";
import JobContent from "../JobContent";
import CheckBox from "../../atoms/CheckBox";
import Rect from "../../atoms/Rect";
import OpenRow from "../OpenRow";
import {Job} from "@_types/job.types";

const RectDiv = styled.div`
    flex:none;
`
type JobRowProps = {
    //출력할 제목
    title: string;
    //job 정보
    data: Job;
    //저장 버튼 클릭 시 callback 함수
    onSaveCallback: () => void;
    //삭제 버튼 클릭 시 callback 함수
    onDeleteCallback: () => void;
    //체크 시 callback 함수
    onCheckCallback: (event: React.ChangeEvent<HTMLInputElement>, id: Job["id"]) => void;
    //체크된 job의 id가 추가되어있는 set
    checkSet: Set<number>;
    //체크박스 숨김
    hideCheckBox?: boolean;
    //읽기모드
    readonly?: boolean;
    //라벨 색상 (hex color)
    labelColor?: string;

}
function JobRow(
    {
        title,
        data,
        onSaveCallback,
        onDeleteCallback,
        onCheckCallback,
        checkSet,
        hideCheckBox,
        readonly,
        labelColor = "#f00",
    } : JobRowProps
) {
    const theme = useTheme();
    return (
        <OpenRow
            head={
                <>
                    {
                        !hideCheckBox &&
                        <CheckBox
                            onChange={(e) => {
                                onCheckCallback(e, data.id);
                            }}
                            checked={checkSet.has(data.id)}

                        />
                    }
                    <RectDiv>
                        <Rect color={data.color} width={7} height={25}/>
                    </RectDiv>
                    <Title color={theme.palette.text.default}>
                        {title}
                    </Title>
                </>
            }
            content={
                <JobContent
                    data={data}
                    showDeleteBtn
                    showSaveBtn
                    onSaveCallback={() => {
                        onSaveCallback();
                    }}
                    onDeleteCallback={() => {
                        onDeleteCallback();
                    }}
                    readonly={readonly}
                />
            }
        />
    );
}

export default JobRow;