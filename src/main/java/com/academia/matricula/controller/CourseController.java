package com.academia.matricula.controller;

import com.academia.matricula.dto.CourseDTO;

import com.academia.matricula.model.Course;

import com.academia.matricula.pagination.PageSupport;
import com.academia.matricula.service.ICourseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.net.URI;


@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final ICourseService service;
    @Qualifier("courseMapper")
    private final ModelMapper modelMapper;
  //  private final Cloudinary cloudinary;

    @GetMapping
    public Mono<ResponseEntity<Flux<CourseDTO>>> findAll() {
        Flux<CourseDTO> fx = service.findAll().map(this::convertToDto);

        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx)
        ).defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CourseDTO>> findById(@PathVariable("id") String id) {
        return service.findById(id)
                .map(this::convertToDto)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<CourseDTO>> save(@Valid @RequestBody CourseDTO dto, final ServerHttpRequest req) {
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
    public Mono<ResponseEntity<CourseDTO>> update(@Valid @PathVariable("id") String id, @RequestBody CourseDTO dto) {
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

    private CourseDTO clientHateoas;

    @GetMapping("/hateoas/{id}")
    public Mono<EntityModel<CourseDTO>> getHateoas(@PathVariable("id") String id) {
        Mono<Link> monoLink = linkTo(methodOn(CourseController.class).findById(id)).withRel("client-info").toMono();

        return service.findById(id)
                .map(this::convertToDto)
                .zipWith(monoLink, EntityModel::of); // (d, link) -> EntityModel.of(d, link)
    }


    @GetMapping("/pageable")
    public Mono<ResponseEntity<PageSupport<CourseDTO>>> getPage(
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



    private CourseDTO convertToDto(Course model) {
        return modelMapper.map(model, CourseDTO.class);
    }

    private Course convertToDocument(CourseDTO dto) {
        return modelMapper.map(dto, Course.class);
    }


}
