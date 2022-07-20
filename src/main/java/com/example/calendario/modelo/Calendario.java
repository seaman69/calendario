package com.example.calendario.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Document(collection="calendario")
public class Calendario {
    @Transient
    public static final String SEQ_NAME="calenario_sequence";
    @Id
    private long idCalendario;
    private long idUsuario;
    private Date fechaInicio;
    private Date fechaFin;

    private ArrayList<Contenedor> contenedors= new ArrayList<>();

    public Calendario(long idCalendario, long idUsuario, Date fechaInicio, Date fechaFin) {
        this.idCalendario = idCalendario;
        this.idUsuario = idUsuario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public long getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(long idCalendario) {
        this.idCalendario = idCalendario;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    public void addcontendedor(Contenedor contenedor){
        contenedors.add(contenedor);

    }
    public void modificarContenedor(long idcontenedor,Contenedor contenedor){
        for(int i=0;i<contenedors.size();i++){
            if(contenedors.get(i).getIdContenedor()==idcontenedor){
               contenedors.remove(i);
               contenedors.add(i,contenedor);
               break;
            }
        }


    }
    public Contenedor getContenedor(long idcontenedor){
     for(int i=0;i<contenedors.size();i++){
         if(contenedors.get(i).getIdContenedor()==idcontenedor){
             return contenedors.get(i);
         }
        }
     return null;
    }
}
