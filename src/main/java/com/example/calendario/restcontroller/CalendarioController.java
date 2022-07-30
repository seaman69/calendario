package com.example.calendario.restcontroller;

import com.example.calendario.modelo.Tratamiento;
import com.example.calendario.modelo.generacionids.SiguienteID;
import com.example.calendario.repository.Calendariorepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/treatement")
public class CalendarioController {
    @Autowired
    Calendariorepo calendariorepo;
    @Autowired
    SiguienteID siguienteID;

    @PostMapping("/addtreatement")
    public HttpEntity<?> agregarcalendario(@RequestBody HashMap<String,Object> body){
        long idPill= ((Number) body.get("idPill")).longValue();
        int amount=(int)body.get("amount");
        ArrayList<String> hourstotake=(ArrayList<String>) body.get("hourToTake");
        String startDate=(String) body.get("startDate");
        String finish=(String) body.get("finishDate");
        int dayscompleted=(int)body.get("daysCompleted");
        int daystreatement=(int) body.get("daysTreatement");
        long idusuario=((Number) body.get("iduser")).longValue();
        long id=siguienteID.generateSequence(Tratamiento.SEQ_NAME);
        Tratamiento tratamiento= new Tratamiento(id,idusuario,idPill,amount,hourstotake,startDate,finish,dayscompleted,daystreatement);
        tratamiento=calendariorepo.save(tratamiento);
        return new HttpEntity<>(tratamiento);

    }

    @PostMapping("/updatetreatement")
    public HttpEntity<?> updatetratamiento(@RequestBody HashMap<String,Object> body){
        if(calendariorepo.findById(((Number) body.get("id")).longValue()).isPresent()){
            Tratamiento tratamiento=calendariorepo.findById(((Number) body.get("id")).longValue()).get();
            //long idPill= (long) body.get("idPill");
            int amount=(int)body.get("amount");
            ArrayList<String> hourstotake=(ArrayList<String>) body.get("hourToTake");
            String startDate=(String) body.get("startDate");
            String finish=(String) body.get("finishDate");
            int dayscompleted=(int)body.get("daysCompleted");
            int daystreatement=(int) body.get("daysTreatement");
            int available=(int) body.get("available");
            String totalStatus =(String) body.get("status");
            String statusDescription=(String) body.get("statusDescription");
            tratamiento.setAmount(amount);
            tratamiento.setAvailable(available);
            tratamiento.setDaysCompleted(dayscompleted);
            tratamiento.setDaysTreatement(daystreatement);
            tratamiento.setHourstoTake(hourstotake);
            tratamiento.setFinishDate(finish);
            tratamiento.setStartdate(startDate);
            tratamiento.setTotalStatus(totalStatus);
            tratamiento.setStatusDescription(statusDescription);
            tratamiento=calendariorepo.save(tratamiento);
            return new HttpEntity<>(tratamiento);
        }else{
            return new HttpEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/gettreatement")
    public HttpEntity<?> gettratamiento(@RequestParam(value = "iduser")long idUsuario,@RequestParam(value = "status",required = false)String totalStatus){
        if(totalStatus!=null){
            List<Tratamiento> tratamientos= calendariorepo.findAllByIdUsuarioAndTotalStatus(idUsuario,totalStatus);
            return new HttpEntity<>(tratamientos);
        }else{
            List<Tratamiento> tratamientos= calendariorepo.findAllByIdUsuario(idUsuario);
            return new HttpEntity<>(tratamientos);
        }

    }

}
