package co.com.sofkau.pecientes.test.task.http;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class GetRequestWithInvalidToken implements Task {

    private String path;

    public GetRequestWithInvalidToken(String path){
        this.path = path;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Get.resource(path).with(request -> request.auth().oauth2("InvalidToken")));
    }

    public static GetRequestWithInvalidToken execute(String path){
        return Tasks.instrumented(GetRequestWithInvalidToken.class, path);
    }
}
