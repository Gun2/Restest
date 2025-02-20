import React from 'react';
import styled, {css, useTheme} from "styled-components";
import Title from "../../atoms/Title";
import Button from "../../atoms/Button";
import {AlertModal as AlertModalType} from "modules/alertModal";

const BackgroundCover = styled.div`
    ${({theme}) => css`${theme.backgroundCover}`};
    z-index:1;
`;

const Box = styled.div`
    width:400px;
    height:300px;
    display:flex;
    flex-direction:column;
    border: 1px solid ${({theme}) => theme.palette.background};
    background-color: ${({theme}) => theme.palette.panel};
`;

const Head = styled.div`
    background-color: ${({theme}) => theme.palette.background};
    height:40px;
    ${({theme}) => theme.flex.center};
`
const Body = styled.div`
    flex:1;
    display:flex;
    flex-direction:column;
    align-items:center;
    padding:5px;
`

const Content = styled.div`
    flex:1;
    ${({theme}) => theme.flex.center};
    color:${({theme}) => theme.palette.text.default};
    font-size: 18px;
`;
type AlertModalProps = {
    text: string;
    id: AlertModalType["key"];
    onClickConfirm: (id : AlertModalType["key"]) => void;
}
const AlertModal = (
    {
        text,
        onClickConfirm,
        id
    }: AlertModalProps
) => {
    const theme = useTheme();
    return (
        <>
            show &&
            <BackgroundCover>
                <Box>
                    <Head>
                        <Title
                            text={"알림"}
                            color={theme.palette.text.default}
                            fontSize={25}
                        />
                    </Head>
                    <Body>
                        <Content>
                            {text}
                        </Content>
                        <Button form={"primary"} onClick={
                            () => onClickConfirm(id)
                        }>
                            닫기
                        </Button>
                    </Body>

                </Box>
            </BackgroundCover>
        </>

    );
};

export default AlertModal;