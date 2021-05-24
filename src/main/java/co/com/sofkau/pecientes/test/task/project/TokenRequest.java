package co.com.sofkau.pecientes.test.task.project;

import co.com.sofkau.pecientes.test.task.http.PostRequest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.util.EnvironmentVariables;

public class TokenRequest implements Task {

    private EnvironmentVariables variables = null;
    private Object body;

    public TokenRequest(Object body){
        this.body = body;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String baseurl = variables.getProperty("baseurl");
        actor.whoCan(CallAnApi.at(baseurl));
        actor.attemptsTo(PostRequest.execute(variables.getProperty("pathLogin"), body));
    }

    public static TokenRequest execute(Object body){
        return Tasks.instrumented(TokenRequest.class, body);
    }

}
