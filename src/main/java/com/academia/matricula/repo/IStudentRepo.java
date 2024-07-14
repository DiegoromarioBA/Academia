package com.academia.matricula.repo;

import com.academia.matricula.model.Student;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

import java.util.List;

public interface IStudentRepo extends IGenericRepo<Student, String>{



}
