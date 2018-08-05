package webdriver_api;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import commom.Utils;

public class Topic_09_Wait {
	private WebDriver driver;
	private static final String URL_01 = "http://the-internet.herokuapp.com/dynamic_loading/2";
	private static final String URL_02 = "https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx";
	private static final String URL_03 = "https://stuntcoders.com/snippets/javascript-countdown/";
	private static final String URL_04 = "http://toolsqa.com/automation-practice-switch-windows/";
	private static final int DEFAULT_TIMEOUT_IN_SECONDS = 45;
	private static final int DEFAULT_SLEEP_TIME_IN_SECONDS = 1;
	private static final int DEFAULT_TIMEOUT_IN_SECONDS_ = 45;
	private static final int DEFAULT_SLEEP_TIME_IN_SECONDS_ = 1;

	@BeforeTest
	public void beforeTest() {
		System.setProperty(Utils.WEBDRIVER, Utils.CHROMEDRIVER);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	 @Test
	 public void test01_implicitWait() {
	 driver.get(URL_01);
	 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	 driver.findElement(By.xpath("//button[text()='Start']")).click();
	 Assert.assertEquals(driver.findElement(By.xpath("//div[@id='finish']//h4")).getText(),
	 "Hello World!");
	 }

	@Test
	public void test02_explicitWait() {
		driver.get(URL_02);
		waitTillVisible(By.id("ctl00_ContentPlaceholder1_ctl00_ContentPlaceholder1_RadCalendar1Panel"), 10);
		WebElement elSelectedDate = waitTillVisible(By.id("ctl00_ContentPlaceholder1_Label1"), 10);
		System.out.println(elSelectedDate.getText());

		driver.findElement(By.xpath("//td[@title='Thursday, August 23, 2018']")).click();
		waitTillVisible(By.xpath("//div[@class='raDiv']"), 5);
		waitTillVisible(By.xpath("//td[@class='rcSelected']//a[text()='23']"), 20);
		if (waitTillInvisible(By.xpath("//td[@class='rcSelected']//a[text()='23']"), 20) == true) {
			elSelectedDate = waitTillVisible(By.id("ctl00_ContentPlaceholder1_Label1"), 10);
			Assert.assertEquals(elSelectedDate.getText(), "Thursday, August 23, 2018");
		}
	}

	@Test
	public void test03_fluentWait() {
		driver.get(URL_03);
		waitTillVisible(By.id("javascript_countdown_time"), 10);
		waitUntilElementExistsAndGetsValue("javascript_countdown_time", "00");
	}
	
	@Test 
	public void test04_fluentWait() {
		driver.get(URL_04);
		waitUntilColorChangeToRed("colorVar", "color: red;");
	}

	public WebElement waitTillVisible(By locator, int timeOut) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return element;
	}

	public boolean waitTillInvisible(By locator, int timeOut) {
		boolean elInvisible = true;
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		elInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		return elInvisible;
	}

	public void waitUntilElementExistsAndGetsValue(final String elementId, final String value) {
		new FluentWait<WebDriver>(driver).withTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
				.pollingEvery(DEFAULT_SLEEP_TIME_IN_SECONDS, TimeUnit.SECONDS).until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver wd) {
						WebElement element = wd.findElement(By.id(elementId));
						String second = getSecondInTime(element.getText().toString());
						return second.equals(value);
					}
				});
	}
	
	public void waitUntilColorChangeToRed(final String elementId, final String value) {
		new FluentWait<WebDriver>(driver).withTimeout(DEFAULT_TIMEOUT_IN_SECONDS_, TimeUnit.SECONDS)
				.pollingEvery(DEFAULT_SLEEP_TIME_IN_SECONDS_, TimeUnit.SECONDS).until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver wd) {
						WebElement element = wd.findElement(By.id(elementId));
						return element.getAttribute("style").toString().equalsIgnoreCase(value);
					}
				});
	}
	
	
	public String getSecondInTime(String time) {
		String[] lsTime = time.split(":");
		System.out.println(lsTime[2]);
		return lsTime[2].toString();
	}
	
}
