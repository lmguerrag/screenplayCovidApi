package co.com.sofkau.pecientes.test.task.http;

import io.restassured.http.ContentType;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class GetRequestWithToken implements Task {

    private String path;

    public GetRequestWithToken(String path){
        this.path = path;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Get.resource(path).
                with(request -> request.auth().oauth2(Serenity.sessionVariableCalled("TOKEN")))
        );
    }

    public static GetRequestWithToken execute(String path){
        return Tasks.instrumented(GetRequestWithToken.class, path);
    }
}
