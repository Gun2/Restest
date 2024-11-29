import React, {useCallback, useState} from 'react';
import styled from "styled-components";
import Button from "../../atoms/Button";
import JobContent from "../../molecules/JobContent";
import ScheduleContent from "../../molecules/ScheduleContent";

const Box = styled.div`
    display: flex;
    flex-direction : column;
    gap: 5px;
`

const Top = styled.div`
    ${({theme}) => theme.flex.endCenter};
    gap: 5px;
`

const CreateBox = styled.div`
    background-color : ${({theme}) => theme.palette.panel};
`


function ScheduleTop() {

    const [viewCreator, setViewCreator] = useState(false);
    const onAdd = useCallback(() => {
        setViewCreator(true);
    }, []);

    return (
        <Box>
            <Top>
                {
                    viewCreator ?
                        (
                            <></>
                        )
                        : (
                            <Button form={"primary"} onClick={onAdd}>등록</Button>
                        )
                }


                {/*<Button form={"danger"}>삭제</Button>*/}
            </Top>
            <CreateBox>
                {
                    viewCreator &&
                    <ScheduleContent
                        showSaveBtn
                        showCancelBtn
                        onSaveCallback={() => {
                            setViewCreator(false);
                        }}
                        onCancelCallback={() => {
                            setViewCreator(false);
                        }}
                    />
                }
            </CreateBox>
        </Box>
    );
}

export default ScheduleTop;