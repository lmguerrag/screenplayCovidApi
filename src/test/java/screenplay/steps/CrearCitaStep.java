package screenplay.steps;

import co.com.sofkau.pecientes.test.data.RequestAutenticacion;
import co.com.sofkau.pecientes.test.data.RequestCitas;
import co.com.sofkau.pecientes.test.data.RequestPacientes;
import co.com.sofkau.pecientes.test.model.JsonCita;
import co.com.sofkau.pecientes.test.model.JsonPaciente;
import co.com.sofkau.pecientes.test.task.http.PostRequestWithInvalidToken;
import co.com.sofkau.pecientes.test.task.http.PostRequestWithToken;
import co.com.sofkau.pecientes.test.task.project.SaveToken;
import co.com.sofkau.pecientes.test.task.project.TokenRequest;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.util.EnvironmentVariables;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class CrearCitaStep {

    private Actor actor;
    private EnvironmentVariables variables;
    private JsonCita citaEnviada;
    private JsonCita citaRecibida;

    @Cuando("un {string} se autentica exitosamente")
    public void unSeAutenticaExitosamente(String name) {
        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.execute(RequestAutenticacion.getValidUser()));
        actor.attemptsTo(SaveToken.execute());
        String urlbase = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(urlbase));
    }

    @Y("se tiene la informacion de una cita")
    public void seTieneLaInformacionDeUnaCita() {
        citaEnviada = RequestCitas.getInfoCita();
        actor.attemptsTo(PostRequestWithToken
                .execute(variables.getProperty("pathCita"), citaEnviada));
    }

    @Entonces("se puede crear una cita en la api")
    public void sePuedeCrearUnaCitaEnLaApi() {
        actor.should(
                seeThatResponse("la api entrego el codigo 201 correctamente",
                        response -> response.statusCode(201)));

        citaRecibida = SerenityRest.lastResponse().jsonPath().getObject("", JsonCita.class);
        assertThat(citaEnviada).isEqualTo(citaRecibida);
    }

    @Cuando("un {string} con un token invalido quiere crear una cita")
    public void unConUnTokenInvalidoQuiereCrearUnaCita(String name) {
        String urlbase = variables.getProperty("baseurl");
        citaEnviada = RequestCitas.getInfoCita();
        actor = Actor.named(name);
        actor.whoCan(CallAnApi.at(urlbase));
        actor.attemptsTo(PostRequestWithInvalidToken
                .execute(variables.getProperty("pathCita"), citaEnviada));
    }

    @Entonces("la api responde con status {string}")
    public void laApiRespondeConStatus(String codigo) {
        actor.should(
                seeThatResponse("la api entrego el codigo esperado",
                        response -> response.statusCode(Integer.parseInt(codigo))));
    }

    @Cuando("un {string} con token valido quiere crear una cita")
    public void unConTokenValidoQuiereCrearUnaCita(String name) {
        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.execute(RequestAutenticacion.getValidUser()));
        actor.attemptsTo(SaveToken.execute());
        String urlbase = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(urlbase));
    }

    @Y("se ingresan campos vacios al crear una cita")
    public void seIngresanCamposVaciosAlCrearUnaCita() {
        citaEnviada = RequestCitas.getInfoCitaWithEmptyField();
        actor.attemptsTo(PostRequestWithToken
                .execute(variables.getProperty("pathCita"), citaEnviada));
    }

    @Entonces("la api responde con status de error {string}")
    public void laApiRespondeConStatusDeError(String codigo) {
        actor.should(
                seeThatResponse("la api entrego el codigo esperado",
                        response -> response.statusCode(Integer.parseInt(codigo))));
    }

    @Cuando("un {string} autenticado exitosamente")
    public void unAutenticadoExitosamente(String name) {
        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.execute(RequestAutenticacion.getValidUser()));
        actor.attemptsTo(SaveToken.execute());
        String urlbase = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(urlbase));
    }

    @Y("quiere crear una cita con un documento de un paciente que no existe")
    public void quiereCrearUnaCitaConUnDocumentoDeUnPacienteQueNoExiste() {
        citaEnviada = RequestCitas.getInfoCitaWithInvalidId();
        actor.attemptsTo(PostRequestWithToken
                .execute(variables.getProperty("pathCita"), citaEnviada));
    }

    @Entonces("la api contesta con un error de status {string}")
    public void laApiContestaConUnErrorDeStatus(String codigo) {
        actor.should(
                seeThatResponse("la api entrego el codigo esperado",
                        response -> response.statusCode(Integer.parseInt(codigo))));
    }

}
