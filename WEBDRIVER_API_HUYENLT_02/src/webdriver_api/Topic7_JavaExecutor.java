package webdriver_api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Topic7_JavaExecutor {
	private static final String WEBDRIVER = "webdriver.chrome.driver";
	private static final String IEDRIVER = ".\\drivers\\IEDriverServer.exe";
	private static final String URL_01 = "http://live.guru99.com/";
	private static final String URL_02 = "https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_input_disabled";
	private WebDriver driver;
	private JavascriptExecutor js;
	
	@BeforeTest
	public void beforeTest() {
		System.setProperty(WEBDRIVER, IEDRIVER);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		js = (JavascriptExecutor) driver;
	}

	@Test
	public void test_01() throws InterruptedException {
		// navigate to the page
		driver.get(URL_01);
		
		// Get and verify domain
		String realDomain = getDomainByJS();
		Assert.assertEquals(realDomain, "live.guru99.com");
		
		// Get and verify page link
		String realURL = getCurrentURLByJS();
		Assert.assertEquals(realURL, "http://live.guru99.com/");
		
		// Open Mobile Page
		clickButtonByJS(driver.findElement(By.xpath("//div[@id='header-nav']//a")));
		
		// Add Samsung galaxy to card
		clickButtonByJS(driver.findElement(By.xpath("//li[@class='item last']//a[@title='Samsung Galaxy']//following-sibling::div//button")));
		
		// verify text "Samsung Galaxy was added to your shopping cart." by testNG
		String msAddSuccess = driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText();
		Assert.assertEquals(msAddSuccess, "Samsung Galaxy was added to your shopping cart.");
		
		// Verify text by js
		if(verifyByJS("Samsung Galaxy was added to your shopping cart.") == true) {
			System.out.println("Samsung Galaxy was added to your shopping cart.");
		}else {
			System.out.println("Add product failed");
		}
		
		// Click on Privacy Policy 
		clickButtonByJS(driver.findElement(By.xpath("//li//a[text()='Privacy Policy']")));
		Thread.sleep(2000);
		
		// Verify title page : Privacy Policy
		String realTitle = getTitleByJS();
		Assert.assertEquals(realTitle, "Privacy Policy");
		
		// Croll to the bottom of page
		scrollToTheBottomOfPage();
		
		// step 09 verify with just one xpath
		WebElement wishlist = driver.findElement(By.xpath("//table//th[text()='WISHLIST_CNT']/following-sibling::td"));
		Assert.assertEquals(wishlist.getText(), "The number of items in your Wishlist.");
		
		// navigate to "http://demo.guru99.com/v4/"
		navigateByJS();
		
		// verify domain 
		Assert.assertEquals(getDomainByJS(), "live.guru99.com");
	}
	
	//@Test
	public void test_02_removeAttribute() {
		//navigate to the page
		driver.get(URL_02);
		
		// switch to iframe
		driver.switchTo().frame(driver.findElement(By.id("iframeResult")));
		
		// remove disabled attribute
		removeAttribute(driver.findElement(By.name("lname")));
		
		// enter key to lastname textbox
		driver.findElement(By.name("lname")).sendKeys("Huyen");
		
		//click on button submit
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		// verify text by JS
		if(verifyByJS("fname=&lname=Huyen") == true) {
			System.out.println("Test 02 passed");
		}else {
			System.out.println("Test 02 failed");
		}
	}
	
	public String getDomainByJS() {
		String domain = "";
		try {
			domain = (String) js.executeScript("return document.domain");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return domain;
	}
	
	public String getCurrentURLByJS() {
		String currentURL = "";
		try {
			currentURL = (String) js.executeScript("return document.URL");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		return currentURL;
	}
	
	public void clickButtonByJS(WebElement element) {
		js.executeScript("arguments[0].click()", element);
	}
	
	public boolean verifyByJS(String expectMessage) {
		boolean check = false;
		String innerText = (String) js.executeScript("return document.documentElement.innerText");
		if(innerText.contains(expectMessage)) {
			check = true;
		}
		return check;
	}
	
	public String getTitleByJS() {
		String title = "";
		try {
			title = (String) js.executeScript("return document.title");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		return title;
	}
	
	public void scrollToTheBottomOfPage() {
		js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
	}
	
	public void navigateByJS() {
		js.executeScript("window.location = 'http://demo.guru99.com/v4/'");
	}
	
	public void removeAttribute(WebElement element) {
		js.executeScript("arguments[0].removeAttribute('disabled');", element);
	}
}
