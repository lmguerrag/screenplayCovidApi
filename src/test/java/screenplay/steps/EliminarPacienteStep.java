package screenplay.steps;

import co.com.sofkau.pecientes.test.data.RequestAutenticacion;
import co.com.sofkau.pecientes.test.data.RequestPacientes;
import co.com.sofkau.pecientes.test.task.http.DeleteRequestWithInvalidToken;
import co.com.sofkau.pecientes.test.task.http.DeleteRequestWithToken;
import co.com.sofkau.pecientes.test.task.http.PostRequestWithToken;
import co.com.sofkau.pecientes.test.task.project.SaveToken;
import co.com.sofkau.pecientes.test.task.project.TokenRequest;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.util.EnvironmentVariables;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class EliminarPacienteStep {

    private Actor actor;
    private EnvironmentVariables variables;
    private String documento = "55557777";

    @Dado("que un {string} guarda un paciente")
    public void queUnGuardaUnPaciente(String name) {
        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.execute(RequestAutenticacion.getValidUser()));
        actor.attemptsTo(SaveToken.execute());
        String urlbase = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(urlbase));
        actor.attemptsTo(PostRequestWithToken
                .execute(variables.getProperty("pathPatient"), RequestPacientes.getPatientWithId(documento)));
    }

    @Cuando("un {string} autenticado quiere eliminar un paciente registrado por numero de documento")
    public void unAutenticadoQuiereEliminarUnPacienteRegistradoPorNumeroDeDocumento(String name) {
        actor.attemptsTo(DeleteRequestWithToken.execute("paciente/delete/" + documento));
    }

    @Entonces("puede eliminar un paciente correctamente")
    public void puedeEliminarUnPacienteCorrectamente() {
        actor.should(
                seeThatResponse("la api entrego el codigo 200 correctamente",
                        response -> response.statusCode(200)));
    }

    @Cuando("un {string} autenticado quiere eliminar un paciente con numero de documento invalido")
    public void unAutenticadoQuiereEliminarUnPacienteConNumeroDeDocumentoInvalido(String name) {
        actor.attemptsTo(DeleteRequestWithToken.execute("paciente/delete/876512493"));
    }

    @Entonces("la api arroja codigo {string}")
    public void laApiArrojaCodigo(String codigo) {
        actor.should(
                seeThatResponse("la api entrego el codigo 404 correctamente",
                        response -> response.statusCode(Integer.parseInt(codigo))));
    }

    @Cuando("un {string} con token invalido quiere eliminar un paciente registrado")
    public void unConTokenInvalidoQuiereEliminarUnPacienteRegistrado(String name) {
        actor.attemptsTo(DeleteRequestWithInvalidToken.execute("paciente/delete/" + documento));
    }

    @Entonces("la api no permitira eliminar el paciente y respondera con codigo {string}")
    public void laApiNoPermitiraEliminarElPacienteYResponderaConCodigo(String codigo) {
        actor.should(
                seeThatResponse("la api entrego el codigo 403 correctamente",
                        response -> response.statusCode(Integer.parseInt(codigo))));
    }

}
