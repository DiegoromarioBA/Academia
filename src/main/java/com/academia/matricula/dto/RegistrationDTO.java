package com.academia.matricula.dto;


import com.academia.matricula.model.Course;
import com.academia.matricula.model.Student;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationDTO {



    private String id;



    private LocalDate date;



    private StudentDTO student;



    private List<CourseDTO> courses;

    @NotNull
    private Boolean status;


}
