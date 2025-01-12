import React, {useEffect} from 'react';
import {MdCheckCircle, MdError, MdGroup} from 'react-icons/md';
import styled, {css} from 'styled-components';
import ImageTitleCard from '../../molecules/ImageTitleCard';
import DashBoardTeemplate from '../../templates/DashBoardTemplate';
import {useReadFailureCountQuery, useReadSuccessCountQuery, useReadUserQuery} from '../../../modules/sysInfo';
import {useStompClient} from "hooks/useStompClient";

const Box = styled.div`

`;
function DashBoardPage({theme}) {
    const client = useStompClient();
    const {data: userSysInfoData, ...useReadUserQueryResult} = useReadUserQuery();
    const {data: successSysInfoData, ...useReadSuccessCountQueryResult} = useReadSuccessCountQuery();
    const {data: failureSysInfoData, ...useReadFailureCountQueryResult} = useReadFailureCountQuery();
    useEffect(() => {
        useReadUserQueryResult.refetch();
        useReadSuccessCountQueryResult.refetch();
        useReadFailureCountQueryResult.refetch();
    }, [client]);
    return (
        <Box>
            <DashBoardTeemplate
                topLeft={<ImageTitleCard 
                    text={userSysInfoData?.data || 0}
                    bgColor={css`${({theme})=>theme.palette.status.default}`}
                    image={<MdGroup 
                        size={100}
                        />}
                />}
                topCenter={<ImageTitleCard 
                    text={successSysInfoData?.data || 0}
                    bgColor={css`${({theme})=>theme.palette.status.success}`}
                    image={<MdCheckCircle 
                        size={100}
                    />}
                />}
                topRight={<ImageTitleCard 
                    text={failureSysInfoData?.data || 0}
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