package screenplay.steps;

import co.com.sofkau.pecientes.test.data.RequestAutenticacion;
import co.com.sofkau.pecientes.test.task.project.TokenRequest;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.CoreMatchers.startsWith;

public class AutenticacionStep {

    private Actor actor;

    @Cuando("un {string} tiene credenciales validas")
    public void unTieneCredencialesValidas(String name) {
        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.execute(RequestAutenticacion.getValidUser()));
    }

    @Entonces("se puede autenticar en la api correctamente")
    public void sePuedeAutenticarEnLaApiCorrectamente() {
        actor.should(seeThatResponse("la api entrego codigo 200 correctamente",
                response -> response.statusCode(200)
                )
        );

        actor.should(seeThatResponse("se genero el token correctamente",
                response -> response.body(startsWith("{\"jwt\":\"ey"))));
    }

    @Cuando("un {string} tiene password incorrecto")
    public void unTienePasswordIncorrecto(String name) {
        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.execute(RequestAutenticacion.getInvalidPassword()));
    }

    @Entonces("la api contesta con codigo de error {string}")
    public void laApiContestaConCodigoDeError(String codigo) {
        actor.should(seeThatResponse("la api entrego codigo de respuesta esperado",
                response -> response.statusCode(Integer.parseInt(codigo))
                )
        );
    }

    @Cuando("un {string} tiene username invalido y password {string}")
    public void unTieneUsernameInvalidoYPassword(String name, String password) {
        actor = Actor.named(name);
        actor.attemptsTo(TokenRequest.execute(RequestAutenticacion.getInvalidUsername()));
    }

    @Entonces("la api contesta con status {string}")
    public void laApiContestaConStatus(String codigo) {
        actor.should(seeThatResponse("la api entrego codigo de respuesta esperado",
                response -> response.statusCode(Integer.parseInt(codigo))
                )
        );
    }
}
