package com.gun2.restest.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;

    private String keyName;
    private String value;
    private String description;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    //사용유무
    private boolean usable;

    @Builder
    public JobHeader(Long id, Long jobId, String keyName, String value, String description, boolean usable) {
        this.id = id;
        this.jobId = jobId;
        this.keyName = keyName;
        this.value = value;
        this.description = description;
        this.usable = usable;
    }
}
