package com.example.grade_spring_batch.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestDTO {
    private Long id;
    private Double test1;
    private Double test2;
    private Double test3;
    private Double test4;
    private StudentDTO studentAccount;
    private Long accountId;

}
