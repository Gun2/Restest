/**
 * data 변경 정보
 */
export type ChangeData<T> = {
    type: Type
    data: T,
}

type Type = "CREATE" | "UPDATE" | "DELETE"