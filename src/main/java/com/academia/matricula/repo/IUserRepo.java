package com.academia.matricula.repo;

import com.academia.matricula.model.User;
import reactor.core.publisher.Mono;

public interface IUserRepo extends IGenericRepo<User, String> {
    Mono<User> findOneByUsername(String username);
}
