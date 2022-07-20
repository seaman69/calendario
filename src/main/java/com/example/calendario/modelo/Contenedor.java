package com.example.calendario.modelo;

import org.springframework.data.annotation.Transient;

import java.util.ArrayList;

public class Contenedor {
    @Transient
    public static final String SEQ_NAME="contenedor_sequence";
    private long idContenedor;

    private Dosis dosis;
    private int cantidadDosis;
    private Boolean suficiente;

    public Contenedor(long idContenedor,  Dosis dosis, int cantidadDosis) {
        this.idContenedor = idContenedor;

        this.dosis = dosis;
        this.cantidadDosis = cantidadDosis;
    }

    public long getIdContenedor() {
        return idContenedor;
    }

    public void setIdContenedor(long idContenedor) {
        this.idContenedor = idContenedor;
    }



    public Dosis getDosis() {
        return dosis;
    }

    public void setDosis(Dosis dosis) {
        this.dosis = dosis;
    }

    public int getCantidadDosis() {
        return cantidadDosis;
    }

    public void setCantidadDosis(int cantidadDosis) {
        this.cantidadDosis = cantidadDosis;
    }

    public Boolean getSuficiente() {
        return cantidadDosis > 3;
    }
}
