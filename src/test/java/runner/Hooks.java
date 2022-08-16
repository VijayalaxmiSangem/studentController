package runner;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
    @Before(order=1)
    public void beforeScenario(){
        System.out.println("start the browser and clean the cookies ");
    }
    @Before(order=0)
    public void beforeScenarioaStart(){
        System.out.println("---------End of scenario------");
    }
    @After(order = 0)
    public void afterScenarioFinish(){
        System.out.println("------End of Scenario-----");
    }

    @After(order=1)
    public void afterScenario(){
        System.out.println("log out the user and close the browser");
    }

}
