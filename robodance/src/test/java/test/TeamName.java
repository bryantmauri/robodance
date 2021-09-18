package test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Pages.Competition;
import Pages.HomePage;

public class TeamName {
  @Test
  public void testTeamNames() {
	  System.setProperty("webdriver.chrome.driver", "C:\\Users\\bryan\\eclipse\\chromedriver.exe");
	  	WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:4200/");
		HomePage home = new HomePage(driver);
		SoftAssert a = new SoftAssert();
		home.clickStartCompetition();
		Competition match = new Competition(driver);
		match.enterTeamName(1, "TestName1");
		match.enterTeamName(2, "TestName2");
		a.assertTrue(match.testTeamName(1,"TestName1"), "team name 1 is wrong");
		a.assertTrue(match.testTeamName(2,"TestName2"), "team name 2 is wrong");
		a.assertAll();
		driver.quit();
  }
  
  @Test
  public void testCreateButton() {
	  System.setProperty("webdriver.chrome.driver", "C:\\Users\\bryan\\eclipse\\chromedriver.exe");
	  	WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:4200/");
		HomePage home = new HomePage(driver);
		SoftAssert a = new SoftAssert();
		home.clickStartCompetition();
		Competition match = new Competition(driver);
		a.assertFalse(match.createTeamButtonTest(1), "Team 1, You still have no entered no name, the button should still be disabled");
		a.assertFalse(match.createTeamButtonTest(2), "Team 2, You still have no entered no name, the button should still be disabled");
		match.enterTeamName(1, "Test1");
		match.enterTeamName(2, "Test2");
		a.assertFalse(match.createTeamButtonTest(1), "Team 1, Your name is too short, the button should still be disabled");
		a.assertFalse(match.createTeamButtonTest(2), "Team 2, Your name is too short, the button should still be disabled");
		match.enterTeamName(1, "a");
		match.enterTeamName(2, "b");
		a.assertTrue(match.createTeamButtonTest(1), "Team 1, Your name is Okay, the button should be available");
		a.assertTrue(match.createTeamButtonTest(2), "Team 2, Your name is Okay, the button should be available");
		a.assertAll();
		driver.quit();
  }
}
