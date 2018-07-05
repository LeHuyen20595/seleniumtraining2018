package selenium_api;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class Topic_03_WebDriverMethod_WebDriverElement {
	private WebDriver driver;
	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.chrome.driver", "D:\\workspace\\TestGoogleSearch\\driver\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	@Test
	public void testEasy() {
		driver.get("http://demo.guru99.com/test/guru99home/");
		String title = driver.getTitle();
		Assert.assertTrue(title.contains("Demo Guru99 Page")); 	
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}
