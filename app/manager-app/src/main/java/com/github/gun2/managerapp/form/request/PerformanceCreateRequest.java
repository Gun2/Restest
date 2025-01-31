package com.github.gun2.managerapp.form.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class PerformanceCreateRequest {
    @NonNull
    @Min(value = 1, message = "최소 1이상의 값을 입력해 주세요")
    private Integer instance;
    @NonNull
    @Size(min = 1, message = "최소 1개 이상 선택해주세요.")
    private List<Long> jobIdList = new ArrayList<>();
}
