package com.example.calendario.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("logs_tratamiento")
public class LogsTratamiento {
    @Transient
    public static final String SEQ_NAME="tratamiento_logs_sequence";
    @Id
    private long id;
    private String evento;
    private String fecha;
    private long idtratamiento;

    public LogsTratamiento(long id, String evento, long idtratamiento, String fecha) {
        this.id=id;
        this.evento = evento;
        this.fecha = fecha;
        this.idtratamiento=idtratamiento;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdtratamiento() {
        return idtratamiento;
    }

    public void setIdtratamiento(long idtratamiento) {
        this.idtratamiento = idtratamiento;
    }
}
