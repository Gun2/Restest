import React from 'react';
import styled from "styled-components";
import {MdCheckCircleOutline, MdErrorOutline} from "react-icons/md";

const Box = styled.div`
    margin: 0 10px;
    ${({theme}) => theme.flex.startCenter};
    gap: 5px;
    cursor: pointer;
`
const Label = styled.div`
    color: ${({theme}) => theme.palette.text.primary}
`
function StatusLabel({status = "success", label, onClick}) {
    return (
        <Box onClick={onClick}>
            {
                status == "success" &&
                <MdCheckCircleOutline
                    color={"#2fcc71"}
                    size={"30px"}
                />
            }
            {
                status == "failure" &&
                <MdErrorOutline
                    color={"#e74c3c"}
                    size={"30px"}
                />
            }

            <Label>
                {label}
            </Label>
        </Box>
    );
}

export default StatusLabel;