package co.com.sofkau.pecientes.test.data;

import co.com.sofkau.pecientes.test.model.JsonAutenticacion;

public class RequestAutenticacion {

    public static JsonAutenticacion getValidUser(){
        return JsonAutenticacion.builder()
                .username("Covid")
                .password("Covid")
                .build();
    }

    public static JsonAutenticacion getInvalidPassword(){
        return JsonAutenticacion.builder()
                .username("Covid")
                .password("YGFDdfasf")
                .build();
    }

    public static JsonAutenticacion getInvalidUsername(){
        return JsonAutenticacion.builder()
                .username("InvALidUSerNaMe")
                .password("Covid")
                .build();
    }
}
