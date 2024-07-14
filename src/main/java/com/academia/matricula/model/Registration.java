package com.academia.matricula.model;

import com.academia.matricula.dto.CourseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "registrations")
public class Registration {


    @Id
    @EqualsAndHashCode.Include
    private String id;


    @Field
    private LocalDate date;


    @Field
    private Student student;


    @Field
    private List<Course> courses;




}
