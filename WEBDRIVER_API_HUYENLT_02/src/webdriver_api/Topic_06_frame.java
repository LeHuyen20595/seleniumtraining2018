package webdriver_api;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Topic_06_frame {
	private static final String WEBDRIVER = "webdriver.chrome.driver";
	private static final String CHROMEDRIVER = ".\\drivers\\chromedriver.exe";
	private static final String URL_01 = "http://www.hdfcbank.com/";
	private static final String URL_02 = "http://daominhdam.890m.com/";
	private WebDriver driver;

	@BeforeMethod
	public void before() {
		System.setProperty(WEBDRIVER, CHROMEDRIVER);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
//	public void test_01_() {
//		try {
//			driver.get(URL_01);
//			if(driver.findElements(By.id("vizury-notification-template")).size() == 1) {
//				driver.switchTo().frame(driver.findElement(By.id("vizury-notification-template")));
//				driver.findElement(By.id("div-close")).click();
//				driver.switchTo().defaultContent();
//			}
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
	
	@Test
	public void test_02_windowhandle() throws InterruptedException {
		driver.get(URL_02);
		String parentWindow = driver.getWindowHandle();
		System.out.println(parentWindow);
		driver.findElement(By.xpath("//fieldset/label[@for='open window']/following-sibling::a")).click();
		Thread.sleep(2000);
		switchToChildWindow(parentWindow);
		Assert.assertEquals(driver.getTitle(), "Google");
		Thread.sleep(1000);
		driver.close();
		driver.switchTo().window(parentWindow);
	}
	
	@Test
	public void test_03_windowhanlde2() {
		
		
	}
	
	
	public void switchToChildWindow(String parentWindow) {
		Set<String> allWindow = driver.getWindowHandles();
		for(String childWindow : allWindow) {
			if(!childWindow.equals(parentWindow)) {
				driver.switchTo().window(childWindow);
				break;
			}
		}
	}
	
		


	@AfterMethod
	public void after() {
		driver.quit();
	}

}
