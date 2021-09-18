package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

	WebDriver driver;
	
	//Constructor that will be automatically called as soon as the object of the class is created
	public HomePage(WebDriver driver) {
		this.driver=driver;
	}
	
	//Locator for welcome message
	By WelcomeMessage = By.cssSelector("h1[data-test-id='welcomeMessage']");
	
	//Locator for comp message
	By CompMessage = By.cssSelector("p[data-test-id='noCompStarted']");
	
	//Locator for comp message
	By StartCompetitionButton = By.cssSelector("button[data-test-id='start']");
	
	//Method to click start button
	public void clickStartCompetition() {
		driver.findElement(StartCompetitionButton).click();
		
	}
	
	//Method to verify welcome message
	public boolean checkWelcomeMessage(String s) {
		String message = driver.findElement(WelcomeMessage).getText();
		
		return message.equals(s);
	}
	
	//Method to verify competition message
	public boolean checkCompMessage(String s) {
		String message = driver.findElement(CompMessage).getText();
		
		if(message == s) {
			return true;
		} else {
			return false;
		}
	}
		
	//Method to verify competition message
		public boolean checkButtonMessage(String s) {
			String message = driver.findElement(StartCompetitionButton).getText();
			
			if(message == s) {
				return true;
			} else {
				return false;
			}
		}
}