package webdriver_api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Topic_04_ButtonCheckboxRadioAlert {
	private static final String WEBDRIVER = "webdriver.chrome.driver";
	private static final String CHROMEDRIVER = ".\\drivers\\chromedriver.exe";
	private static final String URL_01 = "http://live.guru99.com/";
	private static final String URL_02 = "http://demos.telerik.com/kendo-ui/styling/checkboxes";
	private static final String URL_03 = "https://demos.telerik.com/kendo-ui/styling/radios";
	private static final String URL_04 = "http://daominhdam.890m.com/";
	private WebDriver driver;

	@BeforeTest
	public void beforeTest() {
		System.setProperty(WEBDRIVER, CHROMEDRIVER);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void clickByJavaScript(WebElement element) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].click()", element);
	}

	@Test
	public void test_01() throws InterruptedException {
		driver.get(URL_01);
		driver.findElement(By.linkText("MY ACCOUNT")).click();
		Thread.sleep(2000);
		String realUrl = driver.getCurrentUrl();
		String expectUrl = "http://live.guru99.com/index.php/customer/account/login/";
		Assert.assertEquals(expectUrl, realUrl);
		Thread.sleep(2000);
		clickByJavaScript(driver.findElement(By.xpath(".//a[@title='Create an Account']")));
		realUrl = driver.getCurrentUrl();
		expectUrl = "http://live.guru99.com/index.php/customer/account/create/";
		Assert.assertEquals(expectUrl, realUrl);
	}

	@Test
	public void test_02() {
		driver.get(URL_02);

		WebElement elCheckBox = driver.findElement(By.xpath(".//label[@class='k-checkbox-label' and @for='eq5']"));
		elCheckBox.click();
		boolean check = elCheckBox.isSelected();
		Assert.assertEquals(true, check);

		if (elCheckBox.isSelected()) {
			elCheckBox.click();
		}

		check = elCheckBox.isSelected();
		Assert.assertEquals(false, check);

	}

	@Test
	public void test_03() {
		driver.get(URL_03);
		WebElement elRadio = driver.findElement(By.xpath("https://demos.telerik.com/kendo-ui/styling/radios"));
		if (!elRadio.isSelected()) {
			elRadio.click();
		}
	}

	@Test
	public void test_04() {
		driver.get(URL_04);
		driver.findElement(By.xpath(".//*[@id='content']/div/ul/li[1]/button")).click();
		Alert alert = driver.switchTo().alert();
		String textOnAlert = alert.getText();
		Assert.assertEquals(textOnAlert, "I am a JS Alert");
		alert.accept();
		Assert.assertEquals(driver.findElement(By.id("result")), "You clicked an alert successfully ");

	}

	@Test
	public void test_05() {
		driver.get(URL_04);
		driver.findElement(By.xpath(".//*[@id='content']/div/ul/li[2]/button")).click();
		Alert alert = driver.switchTo().alert();
		String textOnAlert = alert.getText();
		Assert.assertEquals(textOnAlert, "I am a JS Confirm");
		alert.dismiss();
		Assert.assertEquals(driver.findElement(By.id("result")), "You clicked: Cancel");
	}

	@Test
	public void test_06() {
		driver.get(URL_04);
		driver.findElement(By.xpath(".//*[@id='content']/div/ul/li[3]/button")).click();
		Alert alert = driver.switchTo().alert();
		String textOnAlert = alert.getText();
		Assert.assertEquals(textOnAlert, "I am a JS prompt");
		alert.sendKeys("daominhdam");
		alert.accept();
		Assert.assertEquals(driver.findElement(By.id("result")), "You entered: daominhdam");
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}
