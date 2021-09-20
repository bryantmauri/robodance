package test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Pages.Competition;
import Pages.HomePage;
import Pages.Leaderboards;

public class LeaderboardTestCases {
	@Test
	public void CheckTheLeaderboardButtonTest() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:4200/");
		HomePage home = new HomePage(driver);
		SoftAssert a = new SoftAssert();
		home.clickStartCompetition();
		Competition match = new Competition(driver);
		match.enterTeamName(1, "TestTeam1");
		match.enterTeamName(2, "TestTeam2");
		List<WebElement> RobotTeam1 = match.listOfRobotsByTeam(1);
		List<WebElement> RobotTeam2 = match.listOfRobotsByTeam(2);
		for (int i = 0; i < RobotTeam1.size(); i++) {
			match.selectRobotById(RobotTeam1, i);
			match.selectRobotById(RobotTeam2, i);
			match.startDance();
			if (i == 4) {
				a.assertTrue(match.goToLeaderBoardsIsDisplayed(),
						"Leaderboard Should be available after damce: " + RobotTeam1.size());
			} else {
				a.assertFalse(match.goToLeaderBoardsIsDisplayed(),
						"Leaderboard Should not be available yet on dance: " + i);
			}
		}
		a.assertAll();
		driver.quit();
	}

	@Test
	public void CheckWinnersOnLeaderboard() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:4200/");
		HomePage home = new HomePage(driver);
		SoftAssert a = new SoftAssert();
		home.clickStartCompetition();
		Competition comp = new Competition(driver);
		comp.automatedDanceOff();
		comp.goToLeaderBoards();
		Leaderboards ld = new Leaderboards(driver);
		a.assertTrue(ld.compareWinnerNames(), "the winners dont match the FINALwinners");
		driver.quit();
		a.assertAll();
	}

	@Test
	public void CheckLastMatchWinnersOnLeaderboards() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:4200/");
		HomePage home = new HomePage(driver);
		SoftAssert a = new SoftAssert();
		home.clickStartCompetition();
		Competition match = new Competition(driver);
		match.enterTeamName(1, "TestTeam1");
		match.enterTeamName(2, "TestTeam2");
		List<WebElement> RobotTeam1 = match.listOfRobotsByTeam(1);
		List<WebElement> RobotTeam2 = match.listOfRobotsByTeam(2);
		List<String> winners = new ArrayList<String>();
		List<String> losers = new ArrayList<String>();
		for (int i = 0; i < RobotTeam1.size(); i++) {
			match.selectRobotById(RobotTeam1, i);
			match.selectRobotById(RobotTeam2, i);
			match.startDance();
			String battleResult1 = match.battleResultsByRobotId(RobotTeam1, i);
			// String battleResult2 = match.battleResultsByRobotId(RobotTeam2, i);
			if (battleResult1.equals("Winner")) {
				winners.add(RobotTeam1.get(i).findElement(By.className("robot-name")).getText());
				losers.add(RobotTeam2.get(i).findElement(By.className("robot-name")).getText());
			} else {
				winners.add(RobotTeam2.get(i).findElement(By.className("robot-name")).getText());
				losers.add(RobotTeam1.get(i).findElement(By.className("robot-name")).getText());
			}
		}
		// The API is not fast enought to be updated with this results, so i need to
		// wait a little to get a proper
		// updated leaderboard
		Thread.sleep(2000);
		match.goToLeaderBoards();
		Leaderboards ld = new Leaderboards(driver);
		Thread.sleep(2000);
		a.assertTrue(ld.compareLastMatchWinnerNames(winners),
				"last winners dont match, last winners are: " + winners + "and last " + winners.size()
						+ " winners in the leaderboards are: " + ld.returnListWithLastMatchWinersNames(winners.size()));

		a.assertTrue(ld.compareLastMatchLoserNames(losers),
				"last losers dont match, last losers are: " + losers + "and last " + losers.size()
						+ " winners in the leaderboards are: " + ld.returnListWithLastMatchLosersNames(losers.size()));
		a.assertAll();
		driver.quit();
	}
}
