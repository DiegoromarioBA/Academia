package com.academia.matricula.service;

import com.academia.matricula.model.User;
import reactor.core.publisher.Mono;

public interface IUserService {

    Mono<User> saveHash(User user);
    Mono<com.academia.matricula.security.User> searchByUser(String username);
}
