import React, {useEffect} from 'react';
import {MdCheckCircle, MdError, MdGroup} from 'react-icons/md';
import styled, {css, useTheme} from 'styled-components';
import ImageTitleCard from '../../molecules/ImageTitleCard';
import DashBoardTemplate from '../../templates/DashBoardTemplate';
import {useReadFailureCountQuery, useReadSuccessCountQuery, useReadUserQuery} from '../../../modules/sysInfo';
import {useStompClient} from "hooks/useStompClient";
import {AppTheme} from "../../../theme";

const Box = styled.div`

`;
function DashBoardPage() {
    const client = useStompClient();
    const {data: userSysInfoData, ...useReadUserQueryResult} = useReadUserQuery();
    const {data: successSysInfoData, ...useReadSuccessCountQueryResult} = useReadSuccessCountQuery();
    const {data: failureSysInfoData, ...useReadFailureCountQueryResult} = useReadFailureCountQuery();
    useEffect(() => {
        useReadUserQueryResult.refetch();
        useReadSuccessCountQueryResult.refetch();
        useReadFailureCountQueryResult.refetch();
    }, [client]);
    const theme : AppTheme = useTheme();
    return (
        <Box>
            <DashBoardTemplate
                topLeft={<ImageTitleCard 
                    text={userSysInfoData?.data || 0}
                    bgColor={theme.palette.status.default}
                    image={<MdGroup 
                        size={100}
                        />}
                />}
                topCenter={<ImageTitleCard 
                    text={successSysInfoData?.data || 0}
                    bgColor={theme.palette.status.success}
                    image={<MdCheckCircle 
                        size={100}
                    />}
                />}
                topRight={<ImageTitleCard 
                    text={failureSysInfoData?.data || 0}
                    bgColor={theme.palette.status.failure}
                    image={<MdError 
                        size={100}
                        />}
                />}
            />
        </Box>
    );
}

export default DashBoardPage;