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

function ValidationMessage({field, validationGroup, children, style, hide}) {
    const validationSelector = useSelector(store => store.validationMessage[validationGroup]) ?? undefined;
    const matchedErrors = useMemo(() => {
        if(!validationSelector){
            return [];
        }
        return validationSelector.errors?.filter(fieldError => fieldError.field === field)
    }, [validationSelector, field]);
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