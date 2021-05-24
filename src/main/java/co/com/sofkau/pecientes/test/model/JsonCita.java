package co.com.sofkau.pecientes.test.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JsonCita {

    private boolean conclude;
    private String date;
    private Long patientId;
    private String recommendations;
    private String time;

}
