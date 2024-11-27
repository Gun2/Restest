package com.github.gun2.managerapp.form.request;

import com.github.gun2.managerapp.constant.Method;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class JobRequest implements Serializable {
    private Long id;
    @NotBlank
    @Length(max = 255)
    private String title;
    @NotNull
    private Method method;
    @URL
    @NotBlank
    @Length(max = 255)
    private String url;
    private String color;
    @Valid
    private List<JobParamRequest> jobParamList = new ArrayList<>();
    @Valid
    private List<JobHeaderRequest> jobHeaderList = new ArrayList<>();
    @Valid
    private List<JobBodyRequest> jobBodyList = new ArrayList<>();
}
