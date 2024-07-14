package com.academia.matricula.service.impl;

import com.academia.matricula.model.Course;
import com.academia.matricula.model.Student;
import com.academia.matricula.repo.IGenericRepo;
import com.academia.matricula.repo.IStudentRepo;
import com.academia.matricula.service.ICourseService;
import com.academia.matricula.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
public class StudentServiceImpl extends CRUDImpl<Student, String> implements IStudentService {

    private final IStudentRepo repo;

    @Override
    protected IGenericRepo<Student, String> getRepo() {
        return repo;
    }

    @Override
    public Flux<Student> getAllStudentSortedByAgeAsc() {
        return repo.findAll(Sort.by(Sort.Order.asc("age")));
    }

    @Override
    public Flux<Student> getAllStudentSortedByAgeDsc() {
        return repo.findAll(Sort.by(Sort.Order.desc("age")));
    }
}
