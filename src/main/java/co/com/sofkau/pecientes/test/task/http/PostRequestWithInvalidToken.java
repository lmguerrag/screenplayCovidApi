package co.com.sofkau.pecientes.test.task.http;

import io.restassured.http.ContentType;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;

public class PostRequestWithInvalidToken implements Task {

    private String path;
    private Object body;

    public PostRequestWithInvalidToken(String path, Object body){
        this.path = path;
        this.body = body;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Post.to(path)
                .with(request -> request.body(body)
                        .contentType(ContentType.JSON)
                        .auth().oauth2("InvalidToken"))
        );
    }

    public static PostRequestWithInvalidToken execute(String path, Object body){
        return Tasks.instrumented(PostRequestWithInvalidToken.class, path, body);
    }
}
