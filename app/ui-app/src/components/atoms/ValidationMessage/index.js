import React, {useEffect, useMemo} from 'react';
import styled, {css} from "styled-components";
import {useSelector} from "react-redux";

const Box = styled.div`
    ${({validError}) => {
        return validError.length != 0 && css`border: 2px solid ${({theme}) => theme.palette.validation};`
    }}
`;

const Font = styled.p`
    color : ${({theme}) => theme.palette.validation};
    font-size : 14px;
    margin: 0;
    text-align: left;
`

function ValidationMessage({field, validationGroup, children, style, hide, validationErrors}) {
    const matchedErrors = useMemo(() => {
        if(!validationErrors){
            return [];
        }
        return validationErrors.filter(fieldError => fieldError.field === field)
    }, [validationErrors, field]);
    return (
        <div style={style}>
            {
                hide ?
                    {children}
                    :
                    <>
                        <Box validError={matchedErrors}>
                            {children}
                        </Box>
                        {matchedErrors.map((fieldError, i) => <Font key={i}>{fieldError.defaultMessage}</Font>)}
                    </>
            }

        </div>
    );
}

export default ValidationMessage;