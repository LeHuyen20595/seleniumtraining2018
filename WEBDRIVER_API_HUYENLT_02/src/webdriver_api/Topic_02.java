package webdriver_api;

import org.testng.annotations.Test;

import commom.RandomString;

import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class Topic_02 {
	private static final String WEBDRIVER = "webdriver.chrome.driver";
	private static final String CHROMEDRIVER = ".\\drivers\\chromedriver.exe";
	private static final String URL = "http://live.guru99.com/";
	private RandomString rd;
	private WebDriver driver;
	private String expectTitle = "";
	private String realTitle = "";
	private String expectCurrentLink = "";
	private String realCurrentLink = "";

	@BeforeMethod
	public void beforeMethod() {
		System.setProperty(WEBDRIVER, CHROMEDRIVER);
		driver = new ChromeDriver();
	}

	@Test
	public void test_01() {
		driver.get(URL);
		expectTitle = "Home page";
		realTitle = driver.getTitle();

		// Verify page title
		Assert.assertEquals(realTitle, expectTitle);
		driver.findElement(By.linkText("MY ACCOUNT")).click();
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		driver.navigate().back();

		expectCurrentLink = "http://live.guru99.com/index.php/customer/account/login/";
		realCurrentLink = driver.getCurrentUrl();
		Assert.assertEquals(realCurrentLink, expectCurrentLink);

		driver.navigate().forward();

		expectCurrentLink = "http://live.guru99.com/index.php/customer/account/create/";
		realCurrentLink = driver.getCurrentUrl();
		Assert.assertEquals(realCurrentLink, expectCurrentLink);
	}

	@Test
	public void test_02() throws InterruptedException {
		driver.get(URL);
		driver.findElement(By.linkText("MY ACCOUNT")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		Thread.sleep(1000);
		String emailErrorMessage = driver.findElement(By.id("advice-required-entry-email")).getText();
		String pwErrorMessage = driver.findElement(By.id("advice-required-entry-pass")).getText();

		String expectErrorMessage = "This is a required field.";
		Assert.assertEquals(emailErrorMessage, expectErrorMessage);
		Assert.assertEquals(pwErrorMessage, expectErrorMessage);

	}

	@Test
	public void test_03() throws InterruptedException {
		driver.get(URL);
		driver.findElement(By.linkText("MY ACCOUNT")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("123434234@12312.123123");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		Thread.sleep(1000);
		String emailErrorMessage = driver.findElement(By.id("advice-validate-email-email")).getText();

		String expectErrorMessage = "Please enter a valid email address. For example johndoe@domain.com.";
		Assert.assertEquals(emailErrorMessage, expectErrorMessage);
	}

	@Test
	public void test_04() throws InterruptedException {
		driver.get(URL);
		driver.findElement(By.linkText("MY ACCOUNT")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("automation@gmail.com");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("123");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		Thread.sleep(1000);
		String pwErrorMessage = driver.findElement(By.id("advice-validate-password-pass")).getText();

		String expectErrorMessage = "Please enter 6 or more characters without leading or trailing spaces.";
		Assert.assertEquals(pwErrorMessage, expectErrorMessage);
	}

	@Test
	public void test_05() throws InterruptedException {
		driver.get(URL);
		expectTitle = "Home page";
		realTitle = driver.getTitle();

		// Verify page title
		Assert.assertEquals(realTitle, expectTitle);
		driver.findElement(By.linkText("MY ACCOUNT")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("firstname")).sendKeys("Le");
		// Thread.sleep(1000);
		// driver.findElement(By.id("middlename")).sendKeys("Thi");
		Thread.sleep(1000);
		driver.findElement(By.id("lastname")).sendKeys("Huyen");
		rd = new RandomString();
		String rdEmail = rd.generateRandomString();
		Thread.sleep(1000);
		driver.findElement(By.id("email_address")).sendKeys(rdEmail + "@gmail.com");
		Thread.sleep(1000);
		driver.findElement(By.id("password")).sendKeys("76114311");
		Thread.sleep(1000);
		driver.findElement(By.id("confirmation")).sendKeys("76114311");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@title='Register']")).click();
		Thread.sleep(1000);
		String expectMessage = "Thank you for registering with Main Website Store.";
		Thread.sleep(1000);
		String realMessage = driver.findElement(By.xpath("//*[@class='success-msg']//span")).getText();

		Assert.assertEquals(realMessage, expectMessage);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='account-cart-wrapper']//a//span[contains(text(),'Account')]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id='header-account']//li[5]")).click();
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}
