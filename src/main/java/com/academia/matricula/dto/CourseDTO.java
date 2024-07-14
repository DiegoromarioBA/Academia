package com.academia.matricula.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {

    private String id;

    @NotNull
    @Size(min = 3)
    private String nameCourse;

    @NotNull
    @Size(min = 2, max = 5)
    private String acronymCourse;

    @NotNull
    private Boolean statusCourse;



}
