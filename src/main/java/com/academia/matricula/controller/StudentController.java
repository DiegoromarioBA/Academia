package com.academia.matricula.controller;


import com.academia.matricula.dto.StudentDTO;

import com.academia.matricula.model.Student;

import com.academia.matricula.pagination.PageSupport;
import com.academia.matricula.service.IStudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;


@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final IStudentService service;
    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;
    //  private final Cloudinary cloudinary;

    @GetMapping
    public Mono<ResponseEntity<Flux<StudentDTO>>> findAll() {
        Flux<StudentDTO> fx = service.findAll().map(this::convertToDto);

        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx)
        ).defaultIfEmpty(ResponseEntity.notFound().build());

    }



    @GetMapping("/ascendent")
    public Mono<ResponseEntity<Flux<StudentDTO>>> findAllAscedent() {
        Flux<StudentDTO> fx = service.getAllStudentSortedByAgeAsc().map(this::convertToDto);

        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx)
        ).defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @GetMapping("/descendent")
    public Mono<ResponseEntity<Flux<StudentDTO>>> findAllDescendent() {
        Flux<StudentDTO> fx = service.getAllStudentSortedByAgeDsc().map(this::convertToDto);

        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx)
        ).defaultIfEmpty(ResponseEntity.notFound().build());

    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<StudentDTO>> findById(@PathVariable("id") String id) {
        return service.findById(id)
                .map(this::convertToDto)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<StudentDTO>> save(@Valid @RequestBody StudentDTO dto, final ServerHttpRequest req) {
        return service.save(convertToDocument(dto))
                .map(this::convertToDto)
                .map(e -> ResponseEntity.created(
                                        URI.create(req.getURI().toString().concat("/").concat(e.getId()))
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(e)
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<StudentDTO>> update(@Valid @PathVariable("id") String id, @RequestBody StudentDTO dto) {
        return Mono.just(dto)
                .map(e -> {
                    e.setId(id);
                    return e;
                })
                .flatMap(e -> service.update(id, convertToDocument(dto)))
                .map(this::convertToDto)
                .map(e -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {
        return service.delete(id)
                .flatMap(result -> {
                    if (result) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }

    private StudentDTO clientHateoas;

    @GetMapping("/hateoas/{id}")
    public Mono<EntityModel<StudentDTO>> getHateoas(@PathVariable("id") String id) {
        Mono<Link> monoLink = linkTo(methodOn(StudentController.class).findById(id)).withRel("client-info").toMono();



        //PRACTICA IDEAL
        return service.findById(id)
                .map(this::convertToDto)
                .zipWith(monoLink, EntityModel::of); // (d, link) -> EntityModel.of(d, link)
    }


    @GetMapping("/pageable")
    public Mono<ResponseEntity<PageSupport<StudentDTO>>> getPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "2") int size
    ) {

        return service.getPage(PageRequest.of(page, size))
                .map(pageSupport -> new PageSupport<>(
                        pageSupport.getContent().stream().map(this::convertToDto).toList(),
                        pageSupport.getPageNumber(),
                        pageSupport.getPageSize(),
                        pageSupport.getTotalElements()
                ))
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }



    private StudentDTO convertToDto(Student model) {
        return modelMapper.map(model, StudentDTO.class);
    }

    private Student convertToDocument(StudentDTO dto) {
        return modelMapper.map(dto, Student.class);
    }





}
