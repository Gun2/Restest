import React, {CSSProperties, useMemo} from 'react';
import styled, {css} from "styled-components";
import {ClientFieldError} from "@_types/api.types";

const Box = styled.div<{$errorBorder?: boolean}>`
    ${({$errorBorder}) => {
        return $errorBorder && css`border: 2px solid ${({theme}) => theme.palette.validation};`
    }}
`;

const Font = styled.p`
    color : ${({theme}) => theme.palette.validation};
    font-size : 14px;
    margin: 0;
    text-align: left;
`
type ValidationMessageProps = {
    //필드 이름
    field: string;
    children?: React.ReactNode;
    style?: CSSProperties;
    hide?: boolean;
    validationErrors : ClientFieldError[];
}
function ValidationMessage(
    {
        field,
        children,
        style,
        hide,
        validationErrors
    }: ValidationMessageProps
) {
    const matchedErrors = useMemo(() => {
        if(!validationErrors){
            return [];
        }
        return validationErrors.filter(fieldError => fieldError.field === field)
    }, [validationErrors, field]);
    return (
        <div style={style}>
            {
                hide ? children :
                    <>
                        <Box $errorBorder={matchedErrors?.length > 0}>
                            {children}
                        </Box>
                        {matchedErrors.map((fieldError, i) => <Font key={i}>{fieldError.defaultMessage}</Font>)}
                    </>
            }

        </div>
    );
}

export default ValidationMessage;