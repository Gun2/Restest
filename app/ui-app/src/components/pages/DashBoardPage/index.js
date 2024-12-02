import React from 'react';
import { MdCheckCircle, MdError, MdGroup } from 'react-icons/md';
import styled, {css} from 'styled-components';
import ImageTitleCard from '../../molecules/ImageTitleCard';
import DashBoardTeemplate from '../../templates/DashBoardTemplate';
import {useSelector} from "react-redux";

const Box = styled.div`

`;
function DashBoardPage({theme}) {
    const userCount = useSelector(state => state.sysInfo.user);
    const successCount = useSelector(state => state.sysInfo.success);
    const failureCount = useSelector(state => state.sysInfo.failure);
    return (
        <Box>
            <DashBoardTeemplate
                topLeft={<ImageTitleCard 
                    text={userCount}
                    bgColor={css`${({theme})=>theme.palette.status.default}`}
                    image={<MdGroup 
                        size={100}
                        />}
                />}
                topCenter={<ImageTitleCard 
                    text={successCount}
                    bgColor={css`${({theme})=>theme.palette.status.success}`}
                    image={<MdCheckCircle 
                        size={100}
                    />}
                />}
                topRight={<ImageTitleCard 
                    text={failureCount}
                    bgColor={css`${({theme})=>theme.palette.status.failure}`}
                    image={<MdError 
                        size={100}
                        />}
                />}
            />
        </Box>
    );
}

export default DashBoardPage;