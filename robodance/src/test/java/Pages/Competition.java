package Pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Competition {
	WebDriver driver;

	// Constructor that will be automatically called as soon as the object of the
	// class is created
	public Competition(WebDriver driver) {
		this.driver = driver;
	}

	// Locator for create team message
	By CreateTeamMessage = By.cssSelector("p[data-test-id='createTeamsMessage']");
	By selectRobotsMessage = By.cssSelector("p[data-test-id='selectRobotsMessage']");

	// Locator for Team 1 name
	By Team1Name = By.cssSelector("h3[data-test-id='team_1']");

	// Locator for Team 1 name input
	By Team1Input = By.cssSelector("input[data-test-id='team_1_input']");

	// Locator for Team 2 name
	By Team2Name = By.cssSelector("h3[data-test-id='team_2']");

	// Locator for Team 2 name input
	By Team2Input = By.cssSelector("input[data-test-id='team_2_input']");

	// Locator for Create a team button X
	// WebElement Team1Create = driver.findElements(By.className("primary")).get(0);
	By TeamXCreate = By.className("primary");

	// Locator for team 1 members
	By Team1 = By.xpath("//app-team[1]/app-robot-card");

	// Locator for team 2 members
	By Team2 = By.xpath("//app-team[2]/app-robot-card");

	// Select list of robots

	By Robot = By.cssSelector("app-robot-card[data-test-id*='robot']");

	By RobotExperience = By.xpath("//div/p[2]");
	// Locator for Select for Battle
	// Team1.findElements(select4Battle).get(0-4);
	By select4Battle = By.cssSelector("div[data-test-id='selectForBattleButton']");

	By battleResults = By.cssSelector("div[class*='battle-results']");

	// By select4Battle = By.xpath("//div[1]//div[1]//div[1]");

	// Locator for Start Dance
	By startDance = By.cssSelector("button[data-test-id='startDance']");
	// locator for winners and losers
	// Team1.findElements(Winner).size();
	By Winner = By.cssSelector("div[data-test-id='Winner']");
	By Loser = By.cssSelector("div[data-test-id='Loser']");

	// checkout Leaderboard
	By checkoutLeaderboardMessage = By.cssSelector("p[data-test-id='checkoutLeaderboardMessage']");
	By goToLeaderboardButton = By.cssSelector("button[data-test-id='goToLeaderboardButton']");
	// startDance
	//// div[@class='robot-details']/div[1]

	// Method to check the team message
	public boolean checkTeamMessageFirst(String s) {
		String message = driver.findElement(CreateTeamMessage).getText();

		return message.equals(s);
	}

	public boolean checkSelectRobotsMessage(String s) {
		String message = driver.findElement(selectRobotsMessage).getText();

		return message.equals(s);
	}

	// Method to add names to the teams
	public void enterTeamName(int teamNumber, String Name) {
		switch (teamNumber) {

		case 1:
			driver.findElement(Team1Input).sendKeys(Name);
			driver.findElements(TeamXCreate).get(0).click();
			break;

		case 2:
			driver.findElement(Team2Input).sendKeys(Name);
			driver.findElements(TeamXCreate).get(1).click();
			break;
		}
	}

	// Method to test names to the teams
	public boolean testTeamName(int teamNumber, String Name) {
		boolean b = false;
		switch (teamNumber) {

		case 1:
			b = driver.findElement(Team1Name).getText().equals(Name);
			break;

		case 2:
			b = driver.findElement(Team2Name).getText().equals(Name);
			break;
		}
		return b;
	}

	// Method to test if Create Team is available to the teams
	public boolean createTeamButtonTest(int teamNumber) {
		boolean b = false;
		switch (teamNumber) {

		case 1:
			b = driver.findElements(TeamXCreate).get(0).isEnabled();
			break;

		case 2:
			b = driver.findElements(TeamXCreate).get(1).isEnabled();
			break;
		}
		return b;
	}

	// Method to select a robot
	public void selectRobotById(List<WebElement> team, int robotId) {
		team.get(robotId).findElement(select4Battle).click();
	}

	// Method to select a robot
	public String battleResultsByRobotId(List<WebElement> team, int robotId) {
		return team.get(robotId).findElement(battleResults).getText();
	}

	public String battleStatusByRobotId(List<WebElement> team, int robotId) {
		return team.get(robotId).findElement(select4Battle).getText();
	}

	// Method to select a list of robots
	public List<WebElement> listOfRobotsByTeam(int teamNumber) {

		List<WebElement> a = null;
		switch (teamNumber) {

		case 1:
			a = driver.findElements(Team1);
			break;
		case 2:
			a = driver.findElements(Team2);
			break;

		}
		return a;

	}

	// Method to get experience from robots a list of robots
	/*
	 * public List<String> totalTeamExperience(List<WebElement> teamNumber) {
	 * 
	 * List<WebElement> a = teamNumber; List<String> stringList = a.stream().map(s
	 * -> s.findElement(RobotExperience).getText().trim()
	 * .split("Experience level: ")[1]).collect(Collectors.toList());
	 * 
	 * int integerList =
	 * stringList.stream().map(Integer::parseInt).collect(Collectors.summingInt(
	 * Integer::intValue)); /*int sum = integerList.stream()
	 * .collect(Collectors.summingInt(Integer::intValue)); return stringList;
	 * 
	 * }
	 */

	// Method to start a dance
	public void startDance() {
		driver.findElement(startDance).click();
	}

	// Method to start a dance
	public boolean startDanceIsDisplayed() {
		return driver.findElements(startDance).size() > 0;
	}

	// Method to start an automated dance (for going to leaderboards or simple
	// tests)
	public void automatedDanceOff() throws InterruptedException {
		enterTeamName(1, "TestTeam1");
		enterTeamName(2, "TestTeam2");

		for (int i = 0; i < listOfRobotsByTeam(1).size(); i++) {
			selectRobotById(listOfRobotsByTeam(1), i);
			selectRobotById(listOfRobotsByTeam(2), i);

			startDance();

		}
	}

	// Method to go to leaderboards
	public void goToLeaderBoards() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(goToLeaderboardButton).click();
	}

	public boolean goToLeaderBoardsIsDisplayed() {

		return driver.findElements(goToLeaderboardButton).size() > 0;
	}

}
