package co.com.sofkau.pecientes.test.task.http;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Delete;

public class DeleteRequestWithToken implements Task {

    private String path;

    public DeleteRequestWithToken(String path){
        this.path = path;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Delete.from(path)
                .with(request -> request.auth().oauth2(Serenity.sessionVariableCalled("TOKEN")))
        );
    }

    public static DeleteRequestWithToken execute(String path){
        return Tasks.instrumented(DeleteRequestWithToken.class, path);
    }

}
