package com.example.calendario.repository;

import com.example.calendario.modelo.Tratamiento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface Calendariorepo extends MongoRepository<Tratamiento,Long> {
    @Query(value = "{'idUsuario': ?0}")
    List<Tratamiento> findAllByIdUsuario(long idusuario);

    @Query(value = "{'idUsuario': ?0, 'totalStatus': '?1'}")
    List<Tratamiento> findAllByIdUsuarioAndTotalStatus(long idusuario,String status);
}
