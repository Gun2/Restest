package com.gun2.restest.form.request;

import com.gun2.restest.entity.JobBody;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class JobBodyRequest implements Serializable {
    private Long id;
    private Long jobId;
    @NotNull
    @Column(length = 30)
    private String body;

    @Range(min = 0, max = 1000000)
    private Integer afterDelay;

    private boolean usable;

}
