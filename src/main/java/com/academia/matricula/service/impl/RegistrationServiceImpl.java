package com.academia.matricula.service.impl;


import com.academia.matricula.model.Course;
import com.academia.matricula.model.Registration;
import com.academia.matricula.repo.ICourseRepo;
import com.academia.matricula.repo.IGenericRepo;
import com.academia.matricula.repo.IRegistrationRepo;
import com.academia.matricula.service.ICourseService;
import com.academia.matricula.service.IRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
public class RegistrationServiceImpl extends CRUDImpl<Registration, String> implements IRegistrationService {


    private final IRegistrationRepo repo;

    @Override
    protected IGenericRepo<Registration, String> getRepo() {
        return repo;
    }



}
