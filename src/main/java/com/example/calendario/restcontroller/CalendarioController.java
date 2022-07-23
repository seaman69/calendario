package com.example.calendario.restcontroller;

import com.example.calendario.modelo.Calendario;
import com.example.calendario.modelo.Contenedor;
import com.example.calendario.modelo.Dosis;
import com.example.calendario.modelo.Pastilla;
import com.example.calendario.modelo.generacionids.SiguienteID;
import com.example.calendario.repository.Calendariorepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/calendario")
public class CalendarioController {
    @Autowired
    Calendariorepo calendariorepo;
    @Autowired
    SiguienteID siguienteID;

    @PostMapping("/addcalendario")
    public HttpEntity<?> agregarcalendario(@RequestParam("idusuario")long idusuario,@RequestParam("fechainicio")String fechainicio,@RequestParam("fechafin")String fechafin){
        try{
            Date dateinicio=new SimpleDateFormat("yyyy-MM-dd").parse(fechainicio);
            Date datefin=new SimpleDateFormat("yyyy-MM-dd").parse(fechafin);
            long idcalendario= siguienteID.generateSequence(Calendario.SEQ_NAME);
            Calendario calendario= new Calendario(idcalendario,idusuario,dateinicio,datefin);
            calendariorepo.save(calendario);
            return new HttpEntity<>(idcalendario);
        }catch (ParseException e){
            return new HttpEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/adddosis")
    public HttpEntity<?>agregardosis(@RequestParam("idcalendario")long idcalendario,@RequestParam("idpastilla")long idpastilla,@RequestParam("cantidadpastillas")int cantidadpastillas, @RequestParam("hora_inicio")String hora_inicio,@RequestParam("intervalo") int intervalo){
        try{
            Date date_inicio=new SimpleDateFormat("HH:mm:ss").parse(hora_inicio);
            Calendar calendar= Calendar.getInstance();
            calendar.setTime(date_inicio);
            ArrayList<Date> dates= new ArrayList<>();

            while (date_inicio.before(new SimpleDateFormat("HH:mm:ss").parse("23:59:59"))){
                dates.add(date_inicio);
                calendar.add(Calendar.HOUR_OF_DAY,intervalo);
                date_inicio=calendar.getTime();
                calendar.setTime(date_inicio);
            }
            HashMap<String,Object> pastilla= (HashMap<String, Object>) getPastilla(idpastilla+"");
            String nombre= (String) pastilla.get("name");
            double peso= (double) pastilla.get("weight");
            Pastilla pastilla1=new Pastilla(idpastilla,nombre,peso);
            long iddosis= siguienteID.generateSequence(Dosis.SEQ_NAME);
            Dosis dosis=new Dosis(iddosis,cantidadpastillas,cantidadpastillas*peso,pastilla1);
            dosis.setHoras(dates);
            long idcontenedor= siguienteID.generateSequence(Contenedor.SEQ_NAME);
            Contenedor contenedor=new Contenedor(idcontenedor,dosis,10);
            contenedor.setDosis(dosis);
            HashMap<String, Long> ids=new HashMap<String, Long>();
            if(calendariorepo.findById(idcalendario).isPresent()){
                Calendario calendario= calendariorepo.findById(idcalendario).get();
                calendario.addcontendedor(contenedor);
                ids.put("iddosis",iddosis);
                ids.put("idcontenedor",idcontenedor);
                calendariorepo.save(calendario);
            }else{
                return new HttpEntity<>(HttpStatus.NOT_FOUND);
            }
            return new HttpEntity<>(ids);
        }catch (ParseException e){
            return new HttpEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    @PostMapping("/deletedosis")
    public HttpEntity<?> borrardosis(@RequestParam("idcalendario")long idcalendario,@RequestParam("idcontenedor")long idcontenedor){
        if(calendariorepo.findById(idcalendario).isPresent()){
            Calendario calendario=calendariorepo.findById(idcalendario).get();
            Contenedor contenedor=calendario.getContenedor(idcontenedor);
            int dosis=contenedor.getCantidadDosis();
            contenedor.setCantidadDosis(dosis-1);
            calendario.modificarContenedor(idcontenedor,contenedor);
            calendariorepo.save(calendario);
            return new HttpEntity<>("Dosis restantes="+contenedor.getCantidadDosis());
        }else{
            return new HttpEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/essuficiente")
    public HttpEntity<?> necesitarecarga(@RequestParam("idcalendario")long idcalendario,@RequestParam("idcontenedor")long idcontenedor){
        if(calendariorepo.findById(idcalendario).isPresent()){
            Calendario calendario=calendariorepo.findById(idcalendario).get();
            Contenedor contenedor=calendario.getContenedor(idcontenedor);
            return new HttpEntity<>(contenedor.getSuficiente());
        }else{
            return new HttpEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    private Map<String, Object> getPastilla(String idpastilla) throws IOException {
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse("http://localhost:8084/pills/getpill?"+idpastilla).newBuilder();
        urlBuilder.addQueryParameter("id", "1");

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient client= new OkHttpClient();
        Call call = client.newCall(request);
        Response response = call.execute();

        Map<String,Object> mpa= new ObjectMapper().readValue(response.body().string(), HashMap.class);
        System.out.println(response.body());
        response.close();
        return mpa;


    }
    @GetMapping("/getcalendario")
    public HttpEntity<?> getCalendario(@RequestParam("idcalendario")long idcalendario){
        if(calendariorepo.findById(idcalendario).isPresent()){
            Calendario calendario=calendariorepo.findById(idcalendario).get();

            return new HttpEntity<>(calendario);
        }else{
            return new HttpEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getcalendariobyusuario")
    public HttpEntity<?> getCalendariobyusuer(@RequestParam("idusuario")long idusuario){
        ArrayList<Calendario> calendarios= (ArrayList<Calendario>) calendariorepo.findAllByIdUsuario(idusuario);
        return new HttpEntity<>(calendarios);
    }

}
