package test;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import Pages.Competition;
import Pages.HomePage;

public class DanceOff {
	
	
  @Test
  public void AllCombinationsAvailable() throws InterruptedException {
	  	System.setProperty("webdriver.chrome.driver", "C:\\Users\\bryan\\eclipse\\chromedriver.exe");
	  	WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get("http://localhost:4200/");
		HomePage home = new HomePage(driver);
		home.clickStartCompetition();
		Competition match = new Competition(driver);
		match.enterTeamName(1, "TestTeam1");
		match.enterTeamName(2, "TestTeam2");
		List<WebElement>RobotTeam1 = match.listOfRobotsByTeam(1);
		List<WebElement>RobotTeam2 = match.listOfRobotsByTeam(2);
		//just playing around with java streams
		List<String>robot1Names = RobotTeam1.stream().map(s->s.findElement(By.className("robot-name"))
				.getText()).collect(Collectors.toList());
		
		List<String>robot2Names = RobotTeam2.stream().map(s->s.findElement(By.className("robot-name"))
				.getText()).collect(Collectors.toList());
		
		System.out.println(robot1Names);
		System.out.println(robot2Names);
		int a=0;
		ArrayList<Integer> nums = new ArrayList<Integer>();
		for(int i=0;i<RobotTeam1.size();i++){
			nums.add(i);
		}
		
		for(int i=0;i<RobotTeam1.size();i++){
			for(int j=0;j<RobotTeam2.size();j++) {
				match.selectRobotById(RobotTeam1,nums.get(a));
				match.selectRobotById(RobotTeam2, j);
				
				match.startDance();
				a++;
				
			}
			Collections.rotate(nums, -1);
			driver.navigate().back();
			//Thread.sleep(1000);
			home.clickStartCompetition();
			match.enterTeamName(1, "TestTeam1");
			match.enterTeamName(2, "TestTeam2");
			List<WebElement>RobotTeam1v2 = match.listOfRobotsByTeam(1);
			List<WebElement>RobotTeam2v2 = match.listOfRobotsByTeam(2);
			RobotTeam1=RobotTeam1v2;
			RobotTeam2=RobotTeam2v2;
			a=0;
		}
		driver.quit();	  
  }
  
  @Test
  public void DanceButtonTest() throws InterruptedException {
	  System.setProperty("webdriver.chrome.driver", "C:\\Users\\bryan\\eclipse\\chromedriver.exe");
	  	WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get("http://localhost:4200/");
		HomePage home = new HomePage(driver);
		SoftAssert a = new SoftAssert();
		home.clickStartCompetition();
		Competition match = new Competition(driver);
		match.enterTeamName(1, "TestTeam1");
		match.enterTeamName(2, "TestTeam2");
		List<WebElement>RobotTeam1 = match.listOfRobotsByTeam(1);
		List<WebElement>RobotTeam2 = match.listOfRobotsByTeam(2);
		a.assertFalse(match.startDanceIsDisplayed(), "The dance button should not appear yet, there is no robots selected yet");
		match.selectRobotById(RobotTeam1, 0);
		a.assertFalse(match.startDanceIsDisplayed(), "The dance button should not appear yet, there i only 1 robot selected");
		match.selectRobotById(RobotTeam2, 0);
		a.assertTrue(match.startDanceIsDisplayed(), "The dance button should, there is 2 robots selected ");
		driver.quit();
		a.assertAll();
  }
 
}
