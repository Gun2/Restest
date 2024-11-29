import React, {useCallback, useState, useReducer} from 'react';
import styled, {css} from "styled-components";
import DirectionToggle from "../../atoms/DirectionToggle";
import Title from "../../atoms/Title";
import JobContent from "../JobContent";
import CheckBox from "../../atoms/CheckBox";
import Rect from "../../atoms/Rect";
import OpenRow from "../OpenRow";

const RectDiv = styled.div`
    flex:none;
`
function JobRow({
                    title,
                    data,
                    onSaveCallback,
                    onDeleteCallback,
                    onCheckCallback,
                    _key,
                    checkSet,
                    hideCheckBox,
                    readonly,
                    labelColor="#f00",
                }) {
    const [viewBody, setViewBody] = useState(false);
    const onToggle = useCallback((flag) => {
        setViewBody(flag != 1);
    }, [viewBody]);

    return (
        <OpenRow
            head={<>
                {
                    !hideCheckBox &&
                    <CheckBox
                        onChange={(e) => {
                            onCheckCallback(e, _key);
                        }}
                        checked={checkSet.has(_key)}

                    />
                }
                <RectDiv>
                    <Rect color={data.color} width={7} height={25}/>
                </RectDiv>
                <Title color={"#e4e4e4"}>
                    {title}
                </Title>
            </>}
            content={<JobContent
                data={data}
                showDeleteBtn
                showSaveBtn
                onSaveCallback={() => {
                    onToggle();
                    onSaveCallback();
                }}
                onDeleteCallback={() => {
                    onToggle();
                    onDeleteCallback();
                }}
                readonly={readonly}
            />}
        />
    );
}

export default JobRow;