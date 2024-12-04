/**
 * api 요청/응답 관련 타입
 */

export type BaseResponse = {
    code: string
    message: string
}

export type SuccessResponse<T> = BaseResponse & {
    data : T
}
