package screenplay.steps;

import co.com.sofkau.pecientes.test.data.RequestAutenticacion;
import co.com.sofkau.pecientes.test.model.JsonPaciente;
import co.com.sofkau.pecientes.test.task.http.GetRequestWithInvalidToken;
import co.com.sofkau.pecientes.test.task.http.GetRequestWithToken;
import co.com.sofkau.pecientes.test.task.http.PostRequestWithToken;
import co.com.sofkau.pecientes.test.task.project.SaveToken;
import co.com.sofkau.pecientes.test.task.project.TokenRequest;
import com.ibm.icu.impl.number.Parse;
import freemarker.core.Environment;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.util.EnvironmentVariables;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class ConsultarUnPacienteStep {

    private Actor actor;
    private EnvironmentVariables variables;
    private JsonPaciente pacienteRecibido;
    private String documento = "123456";

    @Cuando("un {string} tiene un token valido")
    public void unTieneUnTokenValido(String name) {
        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.execute(RequestAutenticacion.getValidUser()));
        actor.attemptsTo(SaveToken.execute());
        String urlbase = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(urlbase));
    }

    @Y("quiere consultar un paciente registrado por numero de documento")
    public void quiereConsultarUnPacienteRegistradoPorNumeroDeDocumento() {
        actor.attemptsTo(GetRequestWithToken.execute("paciente/" + documento));
    }

    @Entonces("puede consultar un paciente correctamente")
    public void puedeConsultarUnPacienteCorrectamente() {
        actor.should(
                seeThatResponse("la api entrego el codigo 200 correctamente",
                        response -> response.statusCode(200)));

        pacienteRecibido = SerenityRest.lastResponse().jsonPath().getObject("", JsonPaciente.class);
        assertThat(documento).isEqualTo(String.valueOf(pacienteRecibido.getPatientId()));
    }

    @Cuando("un {string} tiene con token invalido quiere consultar un paciente registrado por numero de documento")
    public void unTieneConTokenInvalidoQuiereConsultarUnPacienteRegistradoPorNumeroDeDocumento(String name) {
        String urlbase = variables.getProperty("baseurl");
        actor = Actor.named(name);
        actor.whoCan(CallAnApi.at(urlbase));
        actor.attemptsTo(GetRequestWithInvalidToken.execute("paciente/" + documento));
    }

    @Entonces("la api responde con codigo {string}")
    public void laApiRespondeConCodigo(String codigo) {
        actor.should(
                seeThatResponse("la api entrego el codigo 403 correctamente",
                        response -> response.statusCode(Integer.parseInt(codigo))));
    }


    @Cuando("un {string} con token valido quiere consultar un paciente cuyo numero de documento no existe")
    public void unConTokenValidoQuiereConsultarUnPacienteCuyoNumeroDeDocumentoNoExiste(String name) {
        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.execute(RequestAutenticacion.getValidUser()));
        actor.attemptsTo(SaveToken.execute());
        String urlbase = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(urlbase));
        actor.attemptsTo(GetRequestWithToken.execute("paciente/177"));
    }

    @Entonces("la api responde con codigo de status {string}")
    public void laApiRespondeConCodigoDeStatus(String codigo) {
        actor.should(
                seeThatResponse("la api entrego el codigo 404 correctamente",
                        response -> response.statusCode(Integer.parseInt(codigo))));
    }
}
