package webdriver_api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Topic_03_ElementsControl {
	private static final String WEBDRIVER = "webdriver.chrome.driver";
	private static final String CHROMEDRIVER = ".\\drivers\\chromedriver.exe";
	private static final String URL_01 = "http://daominhdam.890m.com/";
	private static final String URL_02 = "http://demo.guru99.com/v4";
	private WebDriver driver;
	
	
	@BeforeTest
	public void beforeTest() {
		System.setProperty(WEBDRIVER, CHROMEDRIVER);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
	}
	
	@Test
	public void test_01_workWithDropdownList() {
		driver.get(URL_01);
		Select jobRole01 = new Select(driver.findElement(By.id("job1")));
		boolean isSupportMulti = jobRole01.isMultiple();
		Assert.assertTrue(!isSupportMulti);
		
		jobRole01.selectByVisibleText("Automation Tester");
		WebElement jobRoleSelectedByVisibleText = jobRole01.getFirstSelectedOption();
		Assert.assertEquals("Automation Tester", jobRoleSelectedByVisibleText.getText());
		
		jobRole01.selectByValue("manual");
		WebElement jobRoleSelectedByValue = jobRole01.getFirstSelectedOption();
		Assert.assertEquals("Manual Tester", jobRoleSelectedByValue.getText());
		
		jobRole01.selectByIndex(3);
		WebElement jobRoleSelectedByIndex = jobRole01.getFirstSelectedOption();
		Assert.assertEquals("Mobile Tester", jobRoleSelectedByIndex.getText());
		
		Assert.assertEquals(5, jobRole01.getOptions().size());
	}
	
	@Test
	public void test_02_workWithTextbox() throws InterruptedException {
		driver.get(URL_02);
		driver.findElement(By.name("uid")).clear();
		driver.findElement(By.name("uid")).sendKeys("mngr143229");
		
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("mUtesEz");
		
		driver.findElement(By.name("btnLogin")).submit();
		driver.findElement(By.linkText("New Customer")).click();
		
		String customerName = "Huyen Le";
		String customerAddr = "HITC 239 Xuan Thuy";
		
		driver.findElement(By.name("name")).sendKeys(customerName);
		driver.findElement(By.xpath("//input[@type='radio' and @value ='f']")).click();
		driver.findElement(By.id("dob")).sendKeys("05/20/1995");
		driver.findElement(By.name("addr")).sendKeys(customerAddr);
		driver.findElement(By.name("city")).sendKeys("Ha Noi");
		driver.findElement(By.name("state")).sendKeys("Cau Giay");
		driver.findElement(By.name("pinno")).sendKeys("100000");
		driver.findElement(By.name("telephoneno")).sendKeys("01652039078");
		
		RandomString rd = new RandomString();
		String rdEmail = rd.generateRandomString() + "@gmail.com";
		driver.findElement(By.name("emailid")).sendKeys(rdEmail);
		driver.findElement(By.name("password")).sendKeys("76114311");
		Thread.sleep(2000);
		driver.findElement(By.name("sub")).click();
		
		String customerId = driver.findElement(By.xpath("//td[contains(text(), 'Customer ID')]//following-sibling::td")).getText();
		System.out.println(customerId);
		driver.findElement(By.linkText("Edit Customer")).click();
		driver.findElement(By.name("cusid")).clear();
		driver.findElement(By.name("cusid")).sendKeys(customerId);
		Thread.sleep(2000);
		driver.findElement(By.name("AccSubmit")).click();
		
		Thread.sleep(3000);
		
//		WebElement name = driver.findElement(By. xpath( "//input[@name='name']"));
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		js.executeScript( "arguments[0].removeAttribute('disabled')" ,name );
		
		String actuallyName = driver.findElement(By.xpath("//input[@name ='name']")).getAttribute("value");
		Thread.sleep(2000);
		System.out.println(actuallyName);
		String actuallyAddr = driver.findElement(By.name("addr")).getText();
		Thread.sleep(2000);
		System.out.println(actuallyAddr);
		
		Assert.assertEquals(actuallyName, customerName);
		Assert.assertEquals(actuallyAddr, customerAddr);
		
		String addChange = "Xuan Thuy";
		String cityChange = "Thanh Hoa";
		driver.findElement(By.name("addr")).clear();;
		driver.findElement(By.name("addr")).sendKeys(addChange);
		driver.findElement(By.name("city")).clear();
		driver.findElement(By.name("city")).sendKeys(cityChange);
		driver.findElement(By.name("sub")).click();
		Thread.sleep(2000);
		String actuallyAdd = driver.findElement(By.xpath("//td[contains(text(), 'Address')]//following-sibling::td")).getText();
		String actuallyCity = driver.findElement(By.xpath("//td[contains(text(), 'City')]//following-sibling::td")).getText();
		
		Assert.assertEquals(actuallyAdd, addChange);
		Assert.assertEquals(actuallyCity, cityChange);
	}
	
	@AfterMethod
	public void afterTest() {
		driver.quit();
		
	}
}
