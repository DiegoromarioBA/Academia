package com.academia.matricula.config;

import com.academia.matricula.dto.CourseDTO;

import com.academia.matricula.model.Course;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class MapperConfig {

    @Bean("defaultMapper")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean("courseMapper")
    public ModelMapper courseMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //Escritura
        mapper.createTypeMap(CourseDTO.class, Course.class)
                .addMapping(CourseDTO::getNameCourse, (dest, v)-> dest.setName((String) v))
                .addMapping(CourseDTO::getAcronymCourse, (dest, v)-> dest.setAcronym((String) v))
                .addMapping(CourseDTO::getStatusCourse, (dest, v)-> dest.setStatus((Boolean) v));


        //Lectura
        mapper.createTypeMap(Course.class, CourseDTO.class)
                .addMapping(Course::getName, (dest, v)-> dest.setNameCourse((String) v))
                .addMapping(Course::getAcronym, (dest, v)-> dest.setAcronymCourse((String) v))
                .addMapping(Course::getStatus, (dest, v)-> dest.setStatusCourse((Boolean) v));

        return mapper;
    }


}
