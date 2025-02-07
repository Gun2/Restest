import {ErrorResponse} from "types/api.types";

//rtk query 에러 타입
export type CustomRtkQueryError = {
    status: number;
    data: ErrorResponse;
    message: string;
}

//rtk query 타입인지 확인
export function isFetchBaseQueryError(error: unknown): error is CustomRtkQueryError {
    return typeof error === 'object' && error !== null && 'status' in error && 'data' in error;
}