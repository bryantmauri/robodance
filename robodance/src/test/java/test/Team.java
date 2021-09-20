package test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Pages.Competition;
import Pages.HomePage;

//("//app-robot-card[2]//div[1]//div[1]//p[2]").map(a=>a.textContent)
public class Team {
	@Test
	public void onlyOneSelectedForBattlePerTeam() throws InterruptedException {

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
		int count1 = 0;
		int count2 = 0;

		for (int i = 0; i < RobotTeam1.size(); i++) {
			String battleResult = match.battleStatusByRobotId(RobotTeam1, i);
			String battleResult2 = match.battleStatusByRobotId(RobotTeam2, i);

			a.assertTrue(
					battleResult.trim().equals("Select for battle") || battleResult2.trim().equals("Select for battle"),
					"there is no selection yet, robot id: " + i + " should not be selected for battle yet");
		}

		match.selectRobotById(RobotTeam1, 0);
		match.selectRobotById(RobotTeam2, 3);

		for (int i = 0; i < RobotTeam1.size(); i++) {

			String battleResult = match.battleStatusByRobotId(RobotTeam1, i);
			String battleResult2 = match.battleStatusByRobotId(RobotTeam2, i);

			if (battleResult.trim().equals("Selected for battle")) {
				count1++;
			}
			if (battleResult2.trim().equals("Selected for battle")) {
				count2++;
			}
			a.assertTrue(count1 < 2 || count2 < 2, "there is should be only one selected for battle");
		}

		count1 = 0;
		count2 = 0;
		match.selectRobotById(RobotTeam1, 1);
		match.selectRobotById(RobotTeam2, 2);
		System.out.println(driver.findElement(By.xpath("//app-robot-card[2]//div[1]//div[1]//p[2]")).getText()
				.split("Experience level: ")[1]);

		for (int i = 0; i < RobotTeam1.size(); i++) {
			String battleResult = match.battleStatusByRobotId(RobotTeam1, i);
			String battleResult2 = match.battleStatusByRobotId(RobotTeam2, i);

			if (battleResult.trim().equals("Selected for battle")) {
				count1++;
			}
			if (battleResult2.trim().equals("Selected for battle")) {
				count2++;
			}
			a.assertTrue(count1 < 2 || count2 < 2, "there is should be only one selected for battle");
		}

		a.assertAll();
		driver.quit();
	}

	@Test
	public void ExperienceTest() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://localhost:4200/");
		HomePage home = new HomePage(driver);
		SoftAssert a = new SoftAssert();
		home.clickStartCompetition();
		// test in competition page
		Competition match = new Competition(driver);
		match.enterTeamName(1, "TestTeam1");
		match.enterTeamName(2, "TestTeam2");
		List<WebElement> RobotTeam1 = match.listOfRobotsByTeam(1);
		List<WebElement> RobotTeam2 = match.listOfRobotsByTeam(2);
		int sum1 = 0;
		int sum2 = 0;
		// I was trying to use streams here but i got stuck on the first value being
		// repeated the list.size amount of times
		for (int i = 1; i <= RobotTeam1.size(); i++) {
			String s = driver
					.findElement(By.xpath("//app-team[1]/app-robot-card[" + i + "]//div[@class='robot-details']/p[2]"))
					.getText().split("Experience level: ")[1];
			int num = Integer.parseInt(s);
			sum1 = sum1 + num;
		}

		for (int i = 1; i <= RobotTeam2.size(); i++) {
			String s = driver
					.findElement(By.xpath("//app-team[2]/app-robot-card[" + i + "]//div[@class='robot-details']/p[2]"))
					.getText().split("Experience level: ")[1];
			int num = Integer.parseInt(s);
			sum2 = sum2 + num;
		}
		System.out.println(sum1 + " " + sum2);
		if (sum1 > 50 || sum2 > 50) {
			a.assertTrue(false, "The Experience for one of the teams is above 50");
		}

		a.assertAll();
		driver.quit();
	}
}
