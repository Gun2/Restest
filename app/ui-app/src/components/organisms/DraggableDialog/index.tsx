import React, {MouseEvent, useCallback, useEffect, useState} from 'react';
import styled, {useTheme} from 'styled-components';
import Title from "../../atoms/Title";
import CloseIcon from '@mui/icons-material/Close';
import MinimizeIcon from '@mui/icons-material/Minimize';
import FitScreenIcon from '@mui/icons-material/FitScreen';
import {useDispatch, useSelector} from "react-redux";
import {hide} from "modules/dialog";
import Button from "../../atoms/Button";
import KeyboardDoubleArrowDownIcon from '@mui/icons-material/KeyboardDoubleArrowDown';
import useScroll from "hooks/useScroll";
import {RootState} from "store";

const Background = styled.div`
    ${({theme}) => theme.flex.center};
    position:absolute;
    align-items:flex-start;
    width:100%;
    height:100%;
    z-index:1;
    pointer-events:none;
`

const Dialog = styled.div<{ dragPoint: Position; viewContent: boolean; }>`
    min-width : 800px;
    width: 90%;
    ${({viewContent}) => viewContent ? `height : 800px;` : ``}
    display: flex;
    flex-direction: column;
    border : 0.5px solid ${({theme}) => theme.palette.secondary};
    position : relative;
    ${({dragPoint}) => `transform: translate(${dragPoint.x}px, ${dragPoint.y}px)`};
    transition: all 0.05s;
    pointer-events:all;
    `

const Head = styled.div`
    height : 40px;
    background-color : ${({theme}) => theme.palette.panel};
    color : ${({theme}) => theme.palette.text.default};
    ${({theme}) => theme.flex.center};
    position:relative;
    border : 0.5px solid ${({theme}) => theme.palette.secondary};
`

const Body = styled.div`
    flex : 1;
    background-color : ${({theme}) => theme.palette.background};
    overflow:auto;
`

const BodyInner = styled.div`
    margin: 10px;
`

const ControlContain = styled.div`
    position: absolute;
    right: 10px;
    display:flex;
    gap:5px;
    z-index:1;
`

const ControlItem = styled.div`
    border-radius:2px;
    width:24px;
    height:24px;
    border : 0.5px solid ${({theme}) => theme.palette.secondary};
    background-color : ${({theme}) => theme.palette.panel};
    &:hover{
        background-color : ${({theme}) => theme.palette.primary};
        cursor:pointer;
    }
`;

const DragArea = styled.div`
    position:absolute;
    width:100%;
    height:100%;
    cursor:move;
    pointer-events:all;
`
const TitleContain = styled.div`
    width:100%;
    height:100%;
    cursor:move;
    ${({theme}) => theme.flex.center};
`

const Overlay = styled.div`
    position: absolute;
    right : 10px;
    bottom: 10px;
    opacity: 0.5;
    &:hover{
        opacity: 1;
    }
    
`

type DraggableDialogProps = {
    children: React.ReactNode;
    title: string;
    id?: string;
    startPoint?: Position;
}
type Position = {
    x: number;
    y: number;
}
const DraggableDialog = (
    {
        children,
        title,
        id= '',
        startPoint = {x:0,y:0}
    }: DraggableDialogProps
) => {

    const dispatch = useDispatch();
    const [viewContent, setViewContent] = useState(true);
    const [dragging, setDragging] = useState(false);
    const [dragStartPoint, setDragStartPoint] = useState<Position>({x:0,y:0});
    const [dragPoint, setDragPoint] = useState(startPoint);
    const showDialog = useSelector((store: RootState) => store.dialog[id]);
    const theme = useTheme();
    const hideDialog = useCallback(() => {
        dispatch(hide(id));
    }, [id]);

    const toggleContent = useCallback(() => {
        setViewContent(!viewContent);
    },[viewContent]);
    const dragPointOnMouseDown = useCallback((e: MouseEvent<HTMLDivElement>)=>{
        setDragStartPoint({
            x: e.clientX - dragPoint.x,
            y: e.clientY - dragPoint.y,
        });
        setDragging(true);
    }, [dragStartPoint, dragPoint])
    const dragAreaOnMouseMove = useCallback((e : MouseEvent<HTMLDivElement>) => {
        setDragPoint({
            x: e.clientX-dragStartPoint.x,
            y: e.clientY - dragStartPoint.y
        })
    }, [dragStartPoint]);
    const dragAreaOnMouseUp = useCallback(() => {
        setDragging(false);
    }, []);
    return (
        <>
            {showDialog &&
                <Background>
                    <Dialog dragPoint={dragPoint} viewContent={viewContent}>
                        <Head>
                            <TitleContain onMouseDown={dragPointOnMouseDown}>
                                <Title text={title} color={theme.palette.text.default}></Title>
                            </TitleContain>
                            <ControlContain>
                                <ControlItem onClick={(e) => {
                                    toggleContent();
                                }}>
                                    {
                                        viewContent ? <MinimizeIcon/> : <FitScreenIcon/>
                                    }
                                </ControlItem>
                                <ControlItem onClick={hideDialog}>
                                    <CloseIcon/>
                                </ControlItem>
                            </ControlContain>
                        </Head>
                        <ScrollBody>
                            {
                                viewContent &&
                                <BodyInner>
                                    {children}
                                </BodyInner>
                            }
                        </ScrollBody>


                    </Dialog>
                    {
                        dragging &&
                        <DragArea onMouseMove={dragAreaOnMouseMove} onMouseUp={dragAreaOnMouseUp}/>
                    }
                </Background>
            }
        </>


    );
};
type ScrollBodyProps = {
    children : React.ReactNode;
}
const ScrollBody = (
    {
        children
    }: ScrollBodyProps
) => {
    const [scrollBottom, setScrollBottom] = useState(true);
    const [scrollRef, event] = useScroll<HTMLDivElement>();
    useEffect(() => {
        if (event){
            handleScroll(event);
        }
    }, [event]);
    useEffect(() => {
        if(scrollBottom){
            scrollToBottom();
        }
    }, [scrollBottom, children]);
    const scrollToBottom = useCallback(() => {
        if (scrollRef.current){
            scrollRef?.current.scrollTo(0, scrollRef?.current.scrollHeight);
            setScrollBottom(true);
        }
    }, [scrollRef]);

    const handleScroll = useCallback(({target}: {target: {scrollHeight: number; clientHeight: number; scrollTop: number}}) => {
        if(target.scrollHeight <= target.clientHeight + target.scrollTop + 5){
            setScrollBottom(true);
        }else{
            setScrollBottom(false);
        }
    }, [children])

    return(

        <Body ref={scrollRef}>
            {
                children &&
                <>
                    {children}
                    <Overlay>
                        <Button form={'panel'} width={40} height={40} circle={true} onClick={scrollToBottom}>
                            <KeyboardDoubleArrowDownIcon width={30} />
                        </Button>
                    </Overlay>
                </>
            }

        </Body>
    )
}

export default DraggableDialog;