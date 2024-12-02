import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';
import styled from 'styled-components';
import Title from "../../atoms/Title";
import CloseIcon from '@mui/icons-material/Close';
import MinimizeIcon from '@mui/icons-material/Minimize';
import FitScreenIcon from '@mui/icons-material/FitScreen';
import {useDispatch, useSelector} from "react-redux";
import {hideDialogAction} from "../../../modules/dialog";
import Button from "../../atoms/Button";
import KeyboardDoubleArrowDownIcon from '@mui/icons-material/KeyboardDoubleArrowDown';

const Background = styled.div`
    ${({theme}) => theme.flex.center};
    position:absolute;
    align-items:flex-start;
    width:100%;
    height:100%;
    z-index:1;
    pointer-events:none;
`

const Dialog = styled.div`
    min-width : 800px;
    width: 90%;
    ${({viewContent}) => viewContent ? `height : 800px;` : ``}
    display: flex;
    flex-direction: column;
    border : 0.5px solid ${({theme}) => theme.palette.secondary};
    position : relative;
    top : ${({top}) => top}px;
    left : ${({left}) => left}px;
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


const DraggableDialog = ({children, title, id='', startPoint = {x:0,y:0}}) => {

    const dispatch = useDispatch();
    const [viewContent, setViewContent] = useState(true);
    const [dragging, setDragging] = useState(false);
    const [dragStartPoint, setDragStartPoint] = useState({x:0,y:0});
    const [dragPoint, setDragPoint] = useState(startPoint);
    const showDialog = useSelector(store => store.dialog[id]);

    const hideDialog = useCallback(() => {
        dispatch(hideDialogAction(id));
    }, [id]);

    const toggleContent = useCallback(() => {
        setViewContent(!viewContent);
    },[viewContent]);
    const dragPointOnMouseDown = useCallback((e)=>{
        setDragStartPoint({
            x: e.clientX - dragPoint.x,
            y: e.clientY - dragPoint.y,
        });
        setDragging(true);
    }, [dragStartPoint, dragPoint])
    const dragAreaOnMouseMove = useCallback((e) => {
        setDragPoint({
            x: e.clientX-dragStartPoint.x,
            y: e.clientY - dragStartPoint.y
        }, [dragStartPoint])
    });
    const dragAreaOnMouseUp = useCallback((e) => {
        setDragging(false);
    })
    return (
        <>
            {showDialog &&
                <Background>
                    <Dialog dragPoint={dragPoint} viewContent={viewContent}>
                        <Head>
                            <TitleContain onMouseDown={dragPointOnMouseDown}>
                                <Title text={title} color={({theme}) => theme.palette.text.default}></Title>
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
                        <DragArea onMouseMove={dragAreaOnMouseMove} onMouseUp={dragAreaOnMouseUp}>

                        </DragArea>
                    }
                </Background>
            }
        </>


    );
};

const ScrollBody = ({children}) => {
    const [scrollBottom, setScrollBottom] = useState(true);
    const ref = useRef();
    useEffect(() => {
        ref.current.addEventListener('scroll', handleScroll);
        return () => {
            ref.current?.removeEventListener('scroll', handleScroll);
        };
    }, [children]);
    useEffect(() => {
        if(scrollBottom){
            scrollToBottom();
        }
    });
    const scrollToBottom = useCallback(() => {
        ref.current.scrollTo(0, ref.current.scrollHeight);
        setScrollBottom(true);
    }, [ref]);

    const handleScroll = useCallback(({target}) => {
        if(target.scrollHeight <= target.clientHeight + target.scrollTop + 5){
            setScrollBottom(true);
        }else{
            setScrollBottom(false);
        }
    }, [children])

    return(

        <Body ref={ref}>
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