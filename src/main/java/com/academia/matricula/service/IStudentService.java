package com.academia.matricula.service;

import com.academia.matricula.model.Student;
import reactor.core.publisher.Flux;

public interface IStudentService extends ICRUD<Student, String> {


    //Flux<Student> findAll();
    Flux<Student> getAllStudentSortedByAgeAsc();
    Flux<Student> getAllStudentSortedByAgeDsc();
}
