package com.github.gun2.managerapp.form.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class JobHeaderRequest implements Serializable {
    private Long id;
    private Long jobId;
    @Length(max = 255)
    private String keyName;
    @Length(max = 255)
    private String value;
    @Length(max = 255)
    private String description;
    private boolean usable;

}
