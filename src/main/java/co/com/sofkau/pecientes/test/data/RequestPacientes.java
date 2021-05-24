package co.com.sofkau.pecientes.test.data;

import co.com.sofkau.pecientes.test.model.JsonPaciente;
import com.github.javafaker.Faker;

import java.security.SecureRandom;
import java.util.Locale;

public class RequestPacientes {

    private static Faker faker = Faker.instance(new Locale("es", "CO"), new SecureRandom());

    public static JsonPaciente getPatientInformation(){
        return JsonPaciente.builder()
                .address(faker.address().fullAddress())
                .cellPhoneNumber(Long.parseLong(faker.number().digits(7)))
                .email(faker.internet().emailAddress())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .patientId(Long.parseLong(faker.number().digits(9)))
                .build();
    }

    public static JsonPaciente getPatientWithEmptyFields(){
        return JsonPaciente.builder()
                .address("")
                .cellPhoneNumber(Long.parseLong(faker.number().digits(7)))
                .email("")
                .firstName("")
                .lastName("")
                .patientId(Long.parseLong(faker.number().digits(9)))
                .build();
    }

}
