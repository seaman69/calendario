package com.example.calendario.repository;

import com.example.calendario.modelo.Calendario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Calendariorepo extends MongoRepository<Calendario,Long> {
}
