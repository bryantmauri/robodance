package Pages;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Leaderboards {

	WebDriver driver;

	// Constructor that will be automatically called as soon as the object of the
	// class is created
	public Leaderboards(WebDriver driver) {
		this.driver = driver;
	}

	// Locator for final winners
	By finalWinners = By.cssSelector(".final-winner div");

	// Locator for final winners
	By finalWinnersNames = By.cssSelector(".final-winner div div p.robot-name");

	// Locator for winners
	By winners = By.cssSelector("div[data-test-id='winnerRobot']");

	// Locator for winners names
	By winnersNames = By.cssSelector("div[data-test-id='winnerRobot'] p");

	// Locator for losers
	By losers = By.cssSelector("div[data-test-id='loserRobot']");

	// Locator for losers names
	By losersNames = By.cssSelector("div[data-test-id='loserRobot'] p");

	// Method to return last winner from the leaderboard
	public String returnLastFinalWinnerName() {
		String name = driver.findElements(finalWinnersNames).get(0).getText();
		return name;
	}

	// Method to return list of webelements for final winners
	@SuppressWarnings("null")
	public List<WebElement> returnListWithLastMatchFinalWinners() {
		List<WebElement> fws = new ArrayList<WebElement>();
		for (int i = 0; i < 5; i++)
			fws.add(driver.findElements(finalWinners).get(i));
		return fws;
	}

	// Method to return list of webelements for final losers
	@SuppressWarnings("null")
	public List<WebElement> returnListWithLastMatchLosers() {
		List<WebElement> loserList = new ArrayList<WebElement>();
		for (int i = 0; i < 5; i++)
			loserList.add(driver.findElements(losers).get(i));
		return loserList;
	}

	@SuppressWarnings("null")
	public List<String> returnListWithLastMatchLosersNames(int results) {
		List<String> loserList = driver.findElements(losersNames).stream().map(s -> s.getText()).limit(results)
				.collect(Collectors.toList());
		return loserList;
	}

	@SuppressWarnings("null")
	public List<String> returnListWithLastMatchWinersNames(int results) {
		List<String> winnersList = driver.findElements(winnersNames).stream().map(s -> s.getText()).limit(results)
				.collect(Collectors.toList());
		return winnersList;
	}

	// Brings the left side of the robots (winner side) so we can compare if its the
	// same as "final winners" panel
	@SuppressWarnings("null")
	public boolean compareWinnerNames() {
		List<String> winnersList = driver.findElements(winnersNames).stream().map(s -> s.getText())
				.collect(Collectors.toList());
		List<String> finalWinnersList = driver.findElements(finalWinnersNames).stream().map(s -> s.getText())
				.collect(Collectors.toList());

		boolean bothEquals = winnersList.equals(finalWinnersList);

		return bothEquals;

	}

	@SuppressWarnings("null")
	public boolean compareLastMatchWinnerNames(List<String> ws) {
		List<String> winnersList = driver.findElements(winnersNames).stream().map(s -> s.getText()).limit(ws.size())
				.collect(Collectors.toList());
		Collections.reverse(ws);
		boolean bothEquals = winnersList.equals(ws);

		return bothEquals;

	}

	@SuppressWarnings("null")
	public boolean compareLastMatchLoserNames(List<String> ws) {
		List<String> losersList = driver.findElements(losersNames).stream().map(s -> s.getText()).limit(ws.size())
				.collect(Collectors.toList());
		Collections.reverse(ws);
		boolean bothEquals = losersList.equals(ws);

		return bothEquals;

	}

}
