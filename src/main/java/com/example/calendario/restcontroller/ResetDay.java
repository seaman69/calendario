package com.example.calendario.restcontroller;

import com.example.calendario.modelo.Tratamiento;
import com.example.calendario.repository.Calendariorepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ResetDay {
    @Autowired
    Calendariorepo calendariorepo;

    @Scheduled(cron= "0 0 0 * * *")
    public void resetday(){
        ArrayList<Tratamiento> tratamientos= (ArrayList<Tratamiento>) calendariorepo.findAll();
        for (int i=0;i< tratamientos.size();i++){
            Tratamiento tratamiento=tratamientos.get(i);
            ArrayList<String> horas=tratamiento.getHourstoTake();
            for(int j=0;j<horas.size();j++){
                tratamiento.setStatusday(horas.get(i),false);
            }
            calendariorepo.save(tratamiento);
        }
    }

}
