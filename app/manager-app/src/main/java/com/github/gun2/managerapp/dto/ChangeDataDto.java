package com.github.gun2.managerapp.dto;

import lombok.*;

/**
 * <p>변경 데이터 전달 객체</p>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeDataDto<T> {
    enum Type{
        CREATE("CREATE"),
        UPDATE("UPDATE"),
        DELETE("DELETE");
        private String type;
        Type(String type){
            this.type = type;
        }
    }
    private Type type;
    private T data;

    private ChangeDataDto(Type type, T data){
        this.type = type;
        this.data = data;
    }

    /**
     * <p>생성된 유형 정보</p>
     * @param data 생성된 정보
     * @param <T>
     * @return
     */
    public static <T> ChangeDataDto ofCreatedData(T data){
        return new ChangeDataDto(Type.CREATE, data);
    }

    /**
     * <p>수정된 유형 정보</p>
     * @param data 수정된 정보
     * @param <T>
     * @return
     */
    public static <T> ChangeDataDto ofUpdatedData(T data){
        return new ChangeDataDto(Type.UPDATE, data);
    }

    /**
     * <p>삭제된 유형 정보</p>
     * @param data 삭제된 정보
     * @param <T>
     * @return
     */
    public static <T> ChangeDataDto ofDeletedData(T data){
        return new ChangeDataDto(Type.DELETE, data);
    }
}
