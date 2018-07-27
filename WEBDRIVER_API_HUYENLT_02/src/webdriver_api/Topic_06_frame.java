package webdriver_api;

import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class Topic_06_frame {
	private static final String WEBDRIVER = "webdriver.chrome.driver";
	private static final String CHROMEDRIVER = ".\\drivers\\chromedriver.exe";
	private static final String URL_01 = "http://www.hdfcbank.com/";
	private WebDriver driver;

	@BeforeMethod
	public void before() {
		System.setProperty(WEBDRIVER, CHROMEDRIVER);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void test_01_() {
		driver.get(URL_01);
		if (driver.findElement(By.xpath("//iframe[@id='vizury-notification-template']")).isDisplayed()) {
			driver.switchTo().frame(driver.findElement(By.id("vizury-notification-template")));
			driver.findElement(By.id("div-close")).click();
			driver.switchTo().defaultContent();
		}

		WebElement iFrameFirst = driver.findElement(By.xpath("//div[@class='flipBannerWrap']//iframe"));
		driver.switchTo().frame(iFrameFirst);
		Assert.assertEquals(driver.findElement(By.id("messageText")).getText(), "What are you looking for?");
		driver.switchTo().defaultContent();

		WebElement iFrameSed = driver.findElement(By.xpath("//div[@class='slidingbanners']//iframe"));
		driver.switchTo().frame(iFrameSed);
		Assert.assertEquals(driver.findElements(By.xpath("//div[@class='products-list']/div")).size(), 6);
		driver.switchTo().defaultContent();

		Assert.assertEquals(
				driver.findElements(By.xpath("//div[@class='flipBanner']/div[contains(@class, 'product')]")).size(), 8);
		driver.switchTo().defaultContent();

	}

	@AfterMethod
	public void after() {
		driver.quit();
	}

}
