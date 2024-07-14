package com.academia.matricula.service.impl;

import com.academia.matricula.model.Course;
import com.academia.matricula.repo.ICourseRepo;
import com.academia.matricula.repo.IGenericRepo;
import com.academia.matricula.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
public class CourseServiceImpl extends CRUDImpl<Course, String> implements ICourseService {

    private final ICourseRepo repo;

    @Override
    protected IGenericRepo<Course, String> getRepo() {
        return repo;
    }


}
