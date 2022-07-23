package com.example.calendario.repository;

import com.example.calendario.modelo.Calendario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface Calendariorepo extends MongoRepository<Calendario,Long> {
    @Query(value = "{'idUsuario': ?0}")
    List<Calendario> findAllByIdUsuario(long idusuario);
}
