package screenplay.steps;

import co.com.sofkau.pecientes.test.data.RequestAutenticacion;
import co.com.sofkau.pecientes.test.data.RequestPacientes;
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

public class GuardarPacienteStep {

    private Actor actor;
    private EnvironmentVariables variables;
    private JsonPaciente pacienteEnviado;
    private JsonPaciente pacienteRecibido;

    @Cuando("un {string} se autentica correctamente en la api")
    public void unSeAutenticaCorrectamenteEnLaApi(String name) {
        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.execute(RequestAutenticacion.getValidUser()));
        actor.attemptsTo(SaveToken.execute());
        String urlbase = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(urlbase));
    }

    @Y("se tiene la informacion de un paciente")
    public void seTieneLaInformacionDeUnPaciente() {
        pacienteEnviado = RequestPacientes.getPatientInformation();
        actor.attemptsTo(PostRequestWithToken
                .execute(variables.getProperty("pathPatient"), pacienteEnviado));
    }

    @Entonces("se puede crear el paciente en la api")
    public void sePuedeCrearElPacienteEnLaApi() {
        actor.should(
                seeThatResponse("la api entrego el codigo 201 correctamente",
                        response -> response.statusCode(201)));

        pacienteRecibido = SerenityRest.lastResponse().jsonPath().getObject("", JsonPaciente.class);
        assertThat(pacienteEnviado).isEqualTo(pacienteRecibido);
    }

    @Cuando("un {string} quiere guardar la informacion de un paciente con un token invalido")
    public void unQuiereGuardarLaInformacionDeUnPacienteConUnTokenInvalido(String name) {
        String urlbase = variables.getProperty("baseurl");
        pacienteEnviado = RequestPacientes.getPatientInformation();
        actor = Actor.named(name);
        actor.whoCan(CallAnApi.at(urlbase));
        actor.attemptsTo(PostRequestWithInvalidToken
                .execute(variables.getProperty("pathPatient"), pacienteEnviado));
    }

    @Entonces("la api contesta con codigo {string}")
    public void laApiContestaConCodigo(String codigo) {
        actor.should(
                seeThatResponse("la api entrego el codigo esperado",
                        response -> response.statusCode(Integer.parseInt(codigo))));
    }

    @Cuando("un {string} se autentica correctamente")
    public void unSeAutenticaCorrectamente(String name) {
        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.execute(RequestAutenticacion.getValidUser()));
        actor.attemptsTo(SaveToken.execute());
        String urlbase = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(urlbase));
    }

    @Y("quiere guardar la informacion de un paciente dejando campos vacios")
    public void quiereGuardarLaInformacionDeUnPacienteDejandoCamposVacios() {
        pacienteEnviado = RequestPacientes.getPatientWithEmptyFields();
        actor.attemptsTo(PostRequestWithToken
                .execute(variables.getProperty("pathPatient"), pacienteEnviado));
    }

    @Entonces("la api contesta con codigo de status {string}")
    public void laApiContestaConCodigoDeStatus(String codigo) {
        actor.should(
                seeThatResponse("la api entrego el codigo esperado",
                        response -> response.statusCode(Integer.parseInt(codigo))));
    }
}
