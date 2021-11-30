package com.fullCreative.Trello;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FullCreative {

	public static WebDriver driver;
	public WebDriverWait wait;
	public Actions act;

	public static void main(String[] args) throws InterruptedException {

		WebDriverManager.chromedriver().setup();

		ChromeOptions co = new ChromeOptions();
		co.addArguments("--disable-notifications");

		driver = new ChromeDriver(co);

		FullCreative fc = new FullCreative();
		fc.LoginApplication();
		fc.verifyInitalAssertion();
		fc.createWorkScape();
		fc.createBoardandDetails();
		fc.movingCard();
		fc.logout();

	}

	/* 6. Logout */
	public void logout() throws InterruptedException {
		driver.findElement(By.xpath("//*[@class='_2LKdO6O3n25FhC']")).click();
			driver.findElement(By.xpath("//button[@data-test-id='header-member-menu-logout']")).click();
			
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//button[@id='logout-submit']")));
			driver.findElement(By.xpath("//button[@id='logout-submit']")).click();
			
			Thread.sleep(3000);
			driver.close();
		
	}

	/* 3. Creating Workspace in the Board */
	public void createWorkScape() {
		try {

			wait = new WebDriverWait(driver, 5);

			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Create a workspace')]")));

			driver.findElement(By.xpath("//button[contains(text(),'Create a workspace')]")).click();

			driver.findElement(By.xpath("//input[@class='_3wY_Q5bDf-T1iP']")).sendKeys("Shiva Full Creative");

			driver.findElement(By.xpath("//div[@data-test-id='header-create-team-type-input']")).click();
			driver.findElement(By.xpath("//li[text()='Engineering-IT']")).click();

			driver.findElement(By.xpath("//button[@data-test-id='header-create-team-submit-button']")).click();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/* 5. Moving the CARD_A to ListB from ListA and get their Coordinates */
	public void movingCard() {
		try {
			act = new Actions(driver);

			WebElement source1 = driver.findElement(By.xpath("//div[contains(@class,'list-card-details')]"));

			Point beforeXandY = source1.getLocation();
			System.out.println("The Before Coordinates of CardA is : X - " + beforeXandY.getX() + " and Y - "
					+ beforeXandY.getY());

			WebElement target = driver.findElement(By.xpath("(//a[contains(@class,'open-card-composer')])[5]"));
			act.clickAndHold(source1).moveToElement(target).release().perform();

			System.out.println("The CardA is Successsfully moved from List_A to List_B");
			
			Thread.sleep(3000);
			driver.navigate().refresh();
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'list-card-details')]")));

			WebElement source2 = driver.findElement(By.xpath("//div[contains(@class,'list-card-details')]"));
			Point afterXandY = source2.getLocation();
			System.out.println(
					"The After Coordinates of CardA is : X - " + afterXandY.getX() + " and Y - " + afterXandY.getY());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * 4. Creating the Board and Adding multiple List as ListA,B in the Board and
	 * adding the card in ListA
	 */
	public void createBoardandDetails() {

		try {

			// Creating the Board
			driver.findElement(By.xpath("//li[@data-test-id='create-board-tile']//div//p")).click();

			driver.findElement(By.xpath("//input[@data-test-id='create-board-title-input']"))
					.sendKeys("Automation Interview Board");

			wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//button[@data-test-id='create-board-submit-button']")));

			driver.findElement(By.xpath("//button[@data-test-id='create-board-submit-button']")).click();

			// checking whether the page Loaded
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[contains(@class,'js-board-editing-target')]")));

			// Addina Lists in the Board
			driver.findElement(By.xpath("//span[@class='placeholder']")).click();
			driver.findElement(By.xpath("//input[@class='list-name-input']")).sendKeys("ListA");
			driver.findElement(By.xpath("//input[@value='Add list']")).click();

			driver.findElement(By.xpath("//input[@class='list-name-input']")).sendKeys("ListB");
			driver.findElement(By.xpath("//input[@value='Add list']")).click();

			driver.findElement(By.xpath("(//*[contains(@class,'js-list')])[3]")).click();

			// Adding the CARD in lIST A
			driver.findElement(By.xpath("(//span[contains(text(),'Add a card')])[4]")).click();

			driver.findElement(By.xpath("//textarea[contains(@class,'list-card-composer-textarea')]"))
					.sendKeys("Card_A");

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Add card']")));
			driver.findElement(By.xpath("//input[@value='Add card']")).click();

			driver.findElement(By.xpath("(//*[contains(@class,'js-list')])[3]")).click();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/* 1.Login into the Application */

	public void LoginApplication() {
		try {

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

			driver.get("https://trello.com/login");

			driver.findElement(By.id("user")).sendKeys("learningmethods123@gmail.com");

			wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//input[@value='Log in with Atlassian' and @type='submit']")));

			driver.findElement(By.xpath("//input[@value='Log in with Atlassian' and @type='submit']")).click();

			driver.findElement(By.id("password")).sendKeys("$Bun%$&#");

			driver.findElement(By.xpath("//button[@id='login-submit' and @type='submit']")).click();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/* 2.Verify the Title and url of the Application */

	public void verifyInitalAssertion() {
		String expectedTitle = "Boards | Trello";
		String expectedURL = "https://trello.com/sivaprasad130/boards";

		wait = new WebDriverWait(driver, 5);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@class='boards-page-section-header-name']")));

		if (driver.getCurrentUrl().equals(expectedURL)) {
			System.out.println("The Title of the Page is Expected");
			if (driver.getTitle().equals(expectedTitle)) {
				System.out.println("The Title of the Page is Expected");
			}
		} else {
			System.out.println("The Title the Url Navigated is not Expected");
		}
	}

}
