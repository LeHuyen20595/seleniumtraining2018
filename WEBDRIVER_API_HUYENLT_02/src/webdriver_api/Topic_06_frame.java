package webdriver_api;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

	@Test
	public void test_01_() {
		driver.get(URL_01);
		
		//check pup up is showed or not
		try {
			if (driver.findElements(By.id("vizury-notification-template")).size() == 1) {
				driver.switchTo().frame(driver.findElement(By.id("vizury-notification-template")));
				driver.findElement(By.id("div-close")).click();
				driver.switchTo().defaultContent();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		// switch to flip banner frame
		WebElement iFrameFlipBanner = driver.findElement(By.xpath("//div[@class='flipBannerWrap']//iframe"));
		driver.switchTo().frame(iFrameFlipBanner);
		
		// verify "What are you looking for?"
		Assert.assertEquals(driver.findElement(By.id("messageText")).getText(), "What are you looking for?");
		driver.switchTo().defaultContent();
		
		// Switch to advertising iframe 
		WebElement iframe = driver.findElement(By.xpath("//div[@class='slidingbanners']//iframe"));
		driver.switchTo().frame(iframe);
		
		// Verify advertising iframe has 6 images
		List<WebElement> lsImage = driver.findElements(By.xpath("//div[@id='productcontainer']//div"));
		Assert.assertEquals(lsImage.size(), 6);
		driver.switchTo().defaultContent();
		
		// Verify flip banner has 8 items
		int sizeListFlipBanner = driver.findElements(By.xpath("//div[@class='flipBanner']/div[contains(@class, 'product')]")).size();
		Assert.assertEquals(sizeListFlipBanner, 8);
	}

	@Test
	public void test_02_windowhandle() throws InterruptedException {
		// Navigate to the page
		driver.get(URL_02);
		String parentWindow = driver.getWindowHandle();
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
		driver.get(URL_01);
		try {
			if (driver.findElements(By.id("vizury-notification-template")).size() == 1) {
				driver.switchTo().frame(driver.findElement(By.id("vizury-notification-template")));
				driver.findElement(By.id("div-close")).click();
				driver.switchTo().defaultContent();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		driver.findElement(By.xpath("//div[@class='sectionnav']//li//a[text()='Agri']")).click();
		String parentWindow = driver.getWindowHandle();
		switchToChildWindow(parentWindow);
		driver.findElement(By.xpath("//ul[@class='grid_list clearfix']//li//a//p[text()='Account Details']")).click();
		switchToWindowByTitle("Welcome to HDFC Bank NetBanking");

		driver.switchTo().frame(driver.findElement(By.name("footer")));
		driver.findElement(By.linkText("Privacy Policy")).click();
		switchToWindowByTitle(
				"HDFC Bank - Leading Bank in India, Banking Services, Private Banking, Personal Loan, Car Loan");
		driver.findElement(By.xpath("//a[@title='Corporate Social Responsibility']")).click();
		switchToWindowByTitle("HDFC Bank: Personal Banking Services");
		closeAllWithoutParentWindows(parentWindow);
	}

	public void switchToChildWindow(String parentWindow) {
		Set<String> allWindow = driver.getWindowHandles();
		for (String childWindow : allWindow) {
			if (!childWindow.equals(parentWindow)) {
				driver.switchTo().window(childWindow);
				break;
			}
		}
	}

	public void switchToWindowByTitle(String title) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			driver.switchTo().window(runWindows);
			String currentWin = driver.getTitle();
			if (currentWin.equals(title)) {
				break;
			}
		}
	}

	public boolean closeAllWithoutParentWindows(String parentWindow) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			if (!runWindows.equals(parentWindow)) {
				driver.switchTo().window(runWindows);
				driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}

	@AfterMethod
	public void after() {
		driver.quit();
	}

}
