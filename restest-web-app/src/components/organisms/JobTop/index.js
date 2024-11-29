import React, {useCallback, useReducer, useState} from 'react';
import Button from "../../atoms/Button";
import styled from "styled-components";
import JobContent from "../../molecules/JobContent";
import axios from "axios";

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


function JobTop({onSaveCallback}) {

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
                    <JobContent
                        showSaveBtn
                        showCancelBtn
                        onSaveCallback={() => {
                            setViewCreator(false);
                            onSaveCallback();
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

export default JobTop;