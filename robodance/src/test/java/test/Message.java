package test;


import java.util.List;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Pages.Competition;
import Pages.HomePage;

public class Message {
  @Test
  public void WelcomeMessage() throws InterruptedException {
	  System.setProperty("webdriver.chrome.driver", "C:\\Users\\bryan\\eclipse\\chromedriver.exe");
	  	WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:4200/");
		HomePage home = new HomePage(driver);
		SoftAssert a = new SoftAssert();
		//test in homepage
		a.assertEquals(home.checkWelcomeMessage("Welcome to the robot dance-offs!"), true);
		home.clickStartCompetition();
		//test in competition page
		a.assertEquals(home.checkWelcomeMessage("Welcome to the robot dance-offs!"), true);
		Competition comp = new Competition(driver);
		comp.automatedDanceOff();
		comp.goToLeaderBoards();
		//test in competition page
		a.assertEquals(home.checkWelcomeMessage("Welcome to the robot dance-offs!"), true);
		driver.quit();
		a.assertAll();
  }
  
  @Test
  public void WinnerOrLoserMessage() throws InterruptedException {
	  System.setProperty("webdriver.chrome.driver", "C:\\Users\\bryan\\eclipse\\chromedriver.exe");
	  	WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:4200/");
		HomePage home = new HomePage(driver);
		SoftAssert a = new SoftAssert();
		home.clickStartCompetition();
		//test in competition page
		Competition match = new Competition(driver);
		match.enterTeamName(1, "TestTeam1");
		match.enterTeamName(2, "TestTeam2");
		List<WebElement>RobotTeam1 = match.listOfRobotsByTeam(1);
		List<WebElement>RobotTeam2 = match.listOfRobotsByTeam(2);
		match.selectRobotById(RobotTeam1, 0);
		match.selectRobotById(RobotTeam2, 0);
		match.startDance();
		String battleResult1 = match.battleResultsByRobotId(RobotTeam1, 0);
		String battleResult2 = match.battleResultsByRobotId(RobotTeam2, 0);
		a.assertFalse(battleResult1.equals(battleResult2),"Robot1 is a: " + battleResult1 + 
															" and Robot2 also a: " + battleResult2 + 
															"which is impossible");
		driver.quit();
		a.assertAll();
  }
  
  @Test
  public void SelectedForBattleMessage() throws InterruptedException {
	  System.setProperty("webdriver.chrome.driver", "C:\\Users\\bryan\\eclipse\\chromedriver.exe");
	  	WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:4200/");
		HomePage home = new HomePage(driver);
		SoftAssert a = new SoftAssert();
		home.clickStartCompetition();
		//test in competition page
		Competition match = new Competition(driver);
		match.enterTeamName(1, "TestTeam1");
		match.enterTeamName(2, "TestTeam2");
		List<WebElement>RobotTeam1 = match.listOfRobotsByTeam(1);
	
		String battleResult1 = match.battleStatusByRobotId(RobotTeam1, 0);
		match.selectRobotById(RobotTeam1, 0);
		String battleResult2 = match.battleStatusByRobotId(RobotTeam1, 0);
		a.assertFalse(battleResult1.equals(battleResult2),"Robot1 is a: " + battleResult1 +
															" before click and then is " + battleResult2 +
															"after the click which is impossible");
		driver.quit();
		a.assertAll();
  }
  
  @SuppressWarnings("null")
@Test
  public void selectedRobotMessage() throws InterruptedException {
	  //this could be a file that contains this rows of strings

	  String[] selectRobotMessageText = {"Now select opponents for your first battle",
			  							"Now select opponents for your second battle",
			  							"Now select opponents for your third battle",
			  							"Now select opponents for your fourth battle",
			  							"Now select opponents for your fifth battle"};
	  System.setProperty("webdriver.chrome.driver", "C:\\Users\\bryan\\eclipse\\chromedriver.exe");
	  	WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:4200/");
		HomePage home = new HomePage(driver);
		SoftAssert a = new SoftAssert();
		home.clickStartCompetition();
		//test in competition page
		Competition match = new Competition(driver);
		a.assertTrue(match.checkTeamMessageFirst("First things first, let's create our robot teams!"),
													"The 'createTeamsMessage' is not correct ");
		match.enterTeamName(1, "TestTeam1");
		match.enterTeamName(2, "TestTeam2");
		Thread.sleep(1000);
		List<WebElement>RobotTeam1 = match.listOfRobotsByTeam(1);
		List<WebElement>RobotTeam2 = match.listOfRobotsByTeam(2);
		for(int i=0;i<RobotTeam1.size();i++) {
			
			a.assertTrue(match.checkSelectRobotsMessage(selectRobotMessageText[i]),"message is wrong on iteration: "+ i);
			
			match.selectRobotById(RobotTeam1, i);
			match.selectRobotById(RobotTeam2, i);
			match.startDance();
		}
		//String battleResult2 = match.battleStatusByRobotId(RobotTeam1, 0);
		//a.assertTrue(!battleResult1.equals(battleResult2),"Robot1 is a: " + battleResult1 +
											//" before click and then is " + battleResult2 +
										//	"after the click which is impossible");
		driver.quit();
		a.assertAll();
  }
}
