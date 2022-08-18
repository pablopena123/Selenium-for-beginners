package runnerclass;




import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features ="src/test/resources", 
		tags="@Demo",
		dryRun = false,
		glue={"com/accesshub/qa/stepdefinitions"},
		monochrome = true) //Display the console output in a proper readable format
 

	public class TestRunnerDemo extends AbstractTestNGCucumberTests{

//	@Override
//    @DataProvider(parallel = true)
//    public Object[][] scenarios() {
//        return super.scenarios();
//
//
//	}	
}
