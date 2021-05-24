package screenplay.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/GuardarPaciente.feature",
        glue = "screenplay/steps"
)
public class TestRunnerGuardarPaciente {
}
