package com.example.calendario.repository;

import com.example.calendario.modelo.LogsTratamiento;
import com.example.calendario.modelo.Tratamiento;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogsRepo extends MongoRepository<LogsTratamiento,Long> {
}
