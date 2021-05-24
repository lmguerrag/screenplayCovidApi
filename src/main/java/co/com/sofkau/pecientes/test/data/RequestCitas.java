package co.com.sofkau.pecientes.test.data;

import co.com.sofkau.pecientes.test.model.JsonCita;

public class RequestCitas {

    public static JsonCita getInfoCita(){
        return JsonCita.builder()
                .conclude(true)
                .date("05/08/2021")
                .patientId(Long.parseLong("123456"))
                .recommendations("ayunar")
                .time("8:30 AM")
                .build();
    }

    public static JsonCita getInfoCitaWithEmptyField(){
        return JsonCita.builder()
                .conclude(true)
                .date("")
                .patientId(Long.parseLong("123456"))
                .recommendations("")
                .time("")
                .build();
    }

    public static JsonCita getInfoCitaWithInvalidId(){
        return JsonCita.builder()
                .conclude(true)
                .date("04/09/2021")
                .patientId(Long.parseLong("9543218"))
                .recommendations("dormir")
                .time("9:00 AM")
                .build();
    }
}
