import {useEffect, useState} from "react";
import {ClientFieldError, ErrorResponse, SuccessResponse} from "types/api.types";


export type UseDetectValidationErrorArgs = {
    validationGroup: string;
}
/**
 * api 응답값에서 유효성 검증 에러 감지
 * @param validationGroup 유효성 검증의 그룹 name (구분용)
 */
export const useDetectValidationError = (
    {
        validationGroup
    }: UseDetectValidationErrorArgs
) => {
    const [validationErrors, setValidationErrors] = useState<ClientFieldError[]>([]);
    const [response, setResponse] = useState<SuccessResponse<any> | ErrorResponse | undefined>();
    useEffect(() => {
        if (response?.code === "INVALID_INPUT_VALUE" && "errors" in response) {
            setValidationErrors(response.errors)
        }else {
            setValidationErrors([]);
        }
    }, [response]);

    return {
        //유효성 검증의 그룹 name (구분용)
        validationGroup,
        //에러 필드 및 메시지 정보
        validationErrors,
        //response를 바인딩 하는 함수
        bindResponse: setResponse
    }
}