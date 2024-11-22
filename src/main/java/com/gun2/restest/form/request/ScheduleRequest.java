package com.gun2.restest.form.request;

import com.gun2.restest.dto.JobDto;
import com.gun2.restest.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ScheduleRequest implements Serializable {
    private Long id;
    @NotBlank
    @Length(max = 255)
    private String title;
    @NotNull
    @Range(min = 0, max = 86400)
    private Integer delay;
    private boolean run;

    private List<Long> jobIdList = new ArrayList<>();
    private List<JobDto> jobList = new ArrayList<>();

}
