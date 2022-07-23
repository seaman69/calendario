package com.example.calendario.modelo;

import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.Date;

public class Dosis {
    @Transient
    public static final String SEQ_NAME="dosis_sequence";
    private long idDosis;
    private int cantidadPastillas;
    private double totalmiligramos;

    private Pastilla pastilla;
    private ArrayList<Date> horas = new ArrayList<Date>();

    public Dosis(long idDosis, int cantidadPastillas, double totalmiligramos, Pastilla pastilla) {
        this.idDosis = idDosis;
        this.cantidadPastillas = cantidadPastillas;
        this.totalmiligramos = totalmiligramos;
        this.pastilla = pastilla;
    }

    public long getIdDosis() {
        return idDosis;
    }

    public void setIdDosis(long idDosis) {
        this.idDosis = idDosis;
    }

    public int getCantidadPastillas() {
        return cantidadPastillas;
    }

    public void setCantidadPastillas(int cantidadPastillas) {
        this.cantidadPastillas = cantidadPastillas;
    }

    public double getTotalmiligramos() {
        return totalmiligramos;
    }

    public void setTotalmiligramos(int totalmiligramos) {
        this.totalmiligramos = totalmiligramos;
    }
    public void addhora(Date date){
        horas.add(date);
    }

    public ArrayList<Date> getHoras() {
        return horas;
    }
}
