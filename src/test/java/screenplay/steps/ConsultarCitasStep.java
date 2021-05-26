package screenplay.steps;

import co.com.sofkau.pecientes.test.data.RequestAutenticacion;
import co.com.sofkau.pecientes.test.model.JsonCita;
import co.com.sofkau.pecientes.test.model.JsonPaciente;
import co.com.sofkau.pecientes.test.task.http.GetRequestWithInvalidToken;
import co.com.sofkau.pecientes.test.task.http.GetRequestWithToken;
import co.com.sofkau.pecientes.test.task.project.SaveToken;
import co.com.sofkau.pecientes.test.task.project.TokenRequest;
import com.google.gson.JsonArray;
import io.cucumber.java.ast.Cuando;
import io.cucumber.java.ast.Y;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.util.EnvironmentVariables;


import java.util.List;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class ConsultarCitasStep {

    private Actor actor;
    private String documento = "123456";
    private EnvironmentVariables variables;

    @Cuando("un {string} genera un token valido")
    public void unGeneraUnTokenValido(String name) {
        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.execute(RequestAutenticacion.getValidUser()));
        actor.attemptsTo(SaveToken.execute());
        String urlbase = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(urlbase));
    }

    @Y("quiere consultar todas las citas de un paciente por documento")
    public void quiereConsultarTodasLasCitasDeUnPacientePorDocumento() {
        actor.attemptsTo(GetRequestWithToken.execute("cita/paciente/" + documento));
    }

    @Entonces("puede consultar las citas de un paciente correctamente")
    public void puedeConsultarLasCitasDeUnPacienteCorrectamente() {
        actor.should(
                seeThatResponse("la api entrego el codigo 200 correctamente",
                        response -> response.statusCode(200)));

    }

    @Cuando("un {string} con token invalido quiere consultar las citas de un paciente")
    public void unConTokenInvalidoQuiereConsultarLasCitasDeUnPaciente(String name) {
        String urlbase = variables.getProperty("baseurl");
        actor = Actor.named(name);
        actor.whoCan(CallAnApi.at(urlbase));
        actor.attemptsTo(GetRequestWithInvalidToken.execute("cita/paciente/" + documento));
    }

    @Entonces("la api no permite hacer la consulta respondiendo con codigo {string}")
    public void laApiNoPermiteHacerLaConsultaRespondiendoConCodigo(String codigo) {
        actor.should(
                seeThatResponse("la api entrego el codigo 403 correctamente",
                        response -> response.statusCode(Integer.parseInt(codigo))));
    }
}
