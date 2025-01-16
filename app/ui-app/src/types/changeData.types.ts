/**
 * data 변경 정보
 */
export type ChangeData<T, K> = {
    type: "CREATE" | "UPDATE"
    data: T,
} | {
    type: "DELETE",
    data: K
};