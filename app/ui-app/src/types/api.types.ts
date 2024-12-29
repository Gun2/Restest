/**
 * api 요청/응답 관련 타입
 */

export type BaseResponse = {
    code: string
    message: string
}

/**
 * 요청 성공 응답
 */
export type SuccessResponse<T> = BaseResponse & {
    data : T
}

/**
 * 요청 실패 응답
 */
export type ErrorResponse = Omit<BaseResponse, "code"> & {
    code: ErrorCode;
    errors : ClientFieldError[];
}

export type ClientFieldError = {
    field: string;
    defaultMessage: string;
}

type ErrorCode =
    //유효성 검증 에러
    "INVALID_INPUT_VALUE" |
    //엔티티를 찾을 수 없음
    "ENTITY_NOT_FOUND" |
    //ID로 찾을 수 없음
    "NOT_FOUND_BY_ID" |
    //ID값이 null
    "ID_IS_NULL" |
    //유효하지 않은 타입
    "INVALID_TYPE_VALUE" |
    //성능측정이 진행중이 아님
    "PERFORMANCE_IS_NOT_RUNNING" |
    //성능측정이 이미 진행주임
    "PERFORMANCE_IS_ALREADY_RUNNING" |
    //접근 제한
    "HANDLE_ACCESS_DENIED" |
    //허용되지 않은 메서드
    "METHOD_NOT_ALLOWED" |
    //지원하지 않는 컨텐츠 타입
    "CONTENT_TYPE_NOT_SUPPORTED" |
    //정의되지 않은 내부 시스템 오류
    "INTERNAL_SERVER_ERROR"
