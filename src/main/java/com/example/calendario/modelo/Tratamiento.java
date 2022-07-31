package com.example.calendario.modelo;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;
import java.util.*;

@Document(collection="tratamiento")
public class Tratamiento {
    @Transient
    public static final String SEQ_NAME="tratamiento_sequence";
    @Id
    private long idTratamiento;
    private long idUsuario;
    private long idPill;
    private int amount;

    private ArrayList<String> hourstoTake=new ArrayList<>();
    private String startdate;
    private String finishDate;
    private int daysCompleted;
    private int daysTreatement;
    private int available;
    private TreeMap<String,Boolean> statusday=new TreeMap<>();
    private String totalStatus;
    private String statusDescription;

    private String namePill;
    public Tratamiento(long idTratamiento,long idUsuario, long idPill, int amount, ArrayList<String> hourstoTake, String startdate, String finishDate, int daysCompleted, int daysTreatement) throws Exception{
        this.idTratamiento = idTratamiento;
        this.idPill = idPill;
        this.amount = amount;
        this.hourstoTake = hourstoTake;
        this.startdate = startdate;
        this.finishDate = finishDate;
        this.daysCompleted = daysCompleted;
        this.daysTreatement = daysTreatement;
        this.idUsuario=idUsuario;
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse("http://localhost:8084/pills/getpill?"+idPill).newBuilder();
        urlBuilder.addQueryParameter("id", idPill+"");

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient client= new OkHttpClient();
        Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String,Object> mpa= null;
        try {
            mpa = new ObjectMapper().readValue(response.body().string(), HashMap.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.body());
        response.close();
        namePill=(String) mpa.get("name");
        available=(Integer)mpa.get("pillCount");
        for (String s : hourstoTake) {
            statusday.put(s, false);
        }


    }

    public long getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(long idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setStatusday(TreeMap<String, Boolean> statusday) {
        this.statusday = statusday;
    }

    public long getIdPill() {
        return idPill;
    }

    public void setIdPill(long idPill) {
        this.idPill = idPill;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ArrayList<String> getHourstoTake() {
        return hourstoTake;
    }

    public void setHourstoTake(ArrayList<String> hourstoTake) {
        this.hourstoTake = hourstoTake;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public int getDaysCompleted() {
        return daysCompleted;
    }

    public void setDaysCompleted(int daysCompleted) {
        this.daysCompleted = daysCompleted;
    }

    public int getDaysTreatement() {
        return daysTreatement;
    }

    public void setDaysTreatement(int daysTreatement) {
        this.daysTreatement = daysTreatement;
    }

    public int getAvailable() throws IOException {

        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }



    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public TreeMap<String, Boolean> getStatusday() {
        return statusday;
    }

    public void setStatusday( String hora,boolean value) {
        this.statusday.put(hora,value);
    }

    public String getTotalStatus() {
        return totalStatus;
    }

    public void setTotalStatus(String totalStatus) {
        this.totalStatus = totalStatus;
    }

    public String getNamePill() {
        return namePill;
    }

    public void setNamePill(String namePill) {
        this.namePill = namePill;
    }
}
