package co.com.sofkau.pecientes.test.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JsonAutenticacion {

    private String username;
    private String password;
}
