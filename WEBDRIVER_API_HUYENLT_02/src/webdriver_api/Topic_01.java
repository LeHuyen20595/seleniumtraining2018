package webdriver_api;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Topic_01 {
	private WebDriver driver;
	@BeforeTest
	public void beforeTest() {
		// run with firefox 47
		driver = new FirefoxDriver();
		
		// run with chrome 
		System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		
		// run with ie
		System.setProperty("webdriver.ie.driver", ".\\drivers\\IEDriverServer.exe");
		driver = new InternetExplorerDriver();
		
	}
	
	@Test
	public void test() {
		driver.get("https://www.guru99.com/selenium-tutorial.html");
		
	}
	
	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}
