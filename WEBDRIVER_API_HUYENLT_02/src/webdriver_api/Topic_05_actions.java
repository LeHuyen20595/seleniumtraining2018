package webdriver_api;

import java.awt.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.webdriven.ElementFinder;

public class Topic_05_actions {
	private static final String WEBDRIVER = "webdriver.chrome.driver";
	private static final String CHROMEDRIVER = ".\\drivers\\chromedriver.exe";
	private static final String URL_01 = "http://daominhdam.890m.com/";
	private static final String URL_02 = "https://www.myntra.com/";
	private static final String URL_03 = "http://jqueryui.com/resources/demos/selectable/display-grid.html";
	private static final String URL_04 = "http://www.seleniumlearn.com/double-click";
	private static final String URL_05 = "http://swisnl.github.io/jQuery-contextMenu/demo.html";
	private static final String URL_06 = "http://demos.telerik.com/kendo-ui/dragdrop/angular";
	private static final String URL_07 = "http://jqueryui.com/resources/demos/droppable/default.html";
	private WebDriver driver;
	private Actions action;

	@BeforeTest
	public void beforeTest() {
		System.setProperty(WEBDRIVER, CHROMEDRIVER);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		action = new Actions(driver);
	}

	@Test
	public void test_01_moveMouseToElement() throws InterruptedException {
		driver.get(URL_01);
		action.moveToElement(driver.findElement(By.linkText("Hover over me"))).perform();
		String actualTooltips = driver.findElement(By.xpath("//div[@class='tooltip-inner']")).getText();
		String expectTooltips = "Hooray!";
		Assert.assertEquals(actualTooltips, expectTooltips);

	}

	@Test
	public void test_02() throws InterruptedException {
		driver.get(URL_02);
		action.moveToElement(
				driver.findElement(By.xpath("//span[@class='myntraweb-sprite desktop-iconUser sprites-user']")))
				.perform();
		driver.findElement(By.xpath(".//*[@id='desktop-header-cnt']//a[@data-track='login']")).click();
		boolean checkLoginBox = driver.findElement(By.xpath(".//*[@id='mountRoot']//div[@class='login-box']"))
				.isDisplayed();
		Assert.assertEquals(true, checkLoginBox);

		if (checkLoginBox == true) {
			System.out.println("Login box was displayed");
		} else {
			System.out.println("Login box was not displayed");
		}
	}

	@Test
	public void test_03_selectMultipleItem() throws InterruptedException {
		driver.get(URL_03);
		java.util.List<WebElement> lsItems = driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		System.out.println(lsItems.size());
		action.clickAndHold(lsItems.get(0)).moveToElement(lsItems.get(3)).release().build().perform();

		int numberOfSelected = driver.findElements(By.xpath("//li[@class='ui-state-default ui-selectee ui-selected']"))
				.size();
		Assert.assertEquals(4, numberOfSelected);
	}

	@Test
	public void test_04_DoubleClick() throws InterruptedException {
		driver.get(URL_04);
		action.doubleClick(driver.findElement(By.xpath("//*[@id='node-104']//button"))).build().perform();
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("The Button was double-clicked.", alert.getText());
		alert.accept();
	}

	@Test
	public void test_05_RightClickToElement() throws InterruptedException {
		driver.get(URL_05);
		action.contextClick(driver.findElement(By.xpath("//span[text()='right click me']"))).perform();
		;
		action.moveToElement(driver.findElement(By.xpath("//span[text()='Quit']"))).perform();
		Assert.assertTrue(driver.findElement(By.xpath(
				"//li[contains(@class,'context-menu-visible') and contains(@class,'context-menu-hover')]/span[text()='Quit']"))
				.isDisplayed());
		action.click().perform();
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	@Test
	public void test_06_dragDrop1() {
		driver.get(URL_06);
		WebElement start = driver.findElement(By.xpath("//*[@id='draggable']"));
		WebElement end = driver.findElement(By.xpath("//*[@id='droptarget']"));
		action.dragAndDrop(start, end).build().perform();
		Assert.assertEquals(end.getText(), "You did great!");
	}

	@Test
	public void test_07_dragDrop2() {
		driver.get(URL_07);
		WebElement start = driver.findElement(By.xpath("//*[@id='draggable']"));
		WebElement end = driver.findElement(By.xpath("//*[@id='droppable']"));
		Actions actions = new Actions(driver);
		actions.dragAndDrop(start, end).build().perform();
		Assert.assertEquals(end.getText(), "Dropped!");
	}
	
	@AfterMethod
	public void after() {
		driver.quit();
	}

}
