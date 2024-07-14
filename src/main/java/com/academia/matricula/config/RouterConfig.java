package com.academia.matricula.config;

import com.academia.matricula.handler.CourseHandler;
import com.academia.matricula.handler.RegistrationHandler;
import com.academia.matricula.handler.StudentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;



@Configuration
public class RouterConfig {


    //Functional Endpoints
    @Bean
    public RouterFunction<ServerResponse> routesCourse(CourseHandler handler){
        return route(GET("/v2/courses"), handler::findAll)
                .andRoute(GET("/v2/courses/{id}"), handler::findById)
                .andRoute(POST("/v2/courses"), handler::save)
                .andRoute(PUT("/v2/courses/{id}"), handler::update)
                .andRoute(DELETE("/v2/courses/{id}"), handler::delete);

    }

    @Bean
    public RouterFunction<ServerResponse> routesStudent(StudentHandler handler){
        return route(GET("/v2/students"), handler::findAll) //req -> handler.findAll(req)
                .andRoute(GET("/v2/students/{id}"), handler::findById)
                .andRoute(POST("/v2/students"), handler::save)
                .andRoute(PUT("/v2/students/{id}"), handler::update)
                .andRoute(DELETE("/v2/students/{id}"), handler::delete);
    }
    @Bean
    public RouterFunction<ServerResponse> routesStudentsort(StudentHandler handler){
        return route(GET("/v2/students-descendent"), handler::findAllDescendent1) //req -> handler.findAll(req)
                .andRoute(GET("/v2/students-ascendent"), handler::findAllAscedent1);
    }

    @Bean
    public RouterFunction<ServerResponse> routesRegistration(RegistrationHandler handler){
        return route(GET("/v2/registrations"), handler::findAll) //req -> handler.findAll(req)
                .andRoute(GET("/v2/registrations/{id}"), handler::findById)
                .andRoute(POST("/v2/registrations"), handler::save)
                .andRoute(PUT("/v2/registrations/{id}"), handler::update)
                .andRoute(DELETE("/v2/registrations/{id}"), handler::delete);
    }


}
