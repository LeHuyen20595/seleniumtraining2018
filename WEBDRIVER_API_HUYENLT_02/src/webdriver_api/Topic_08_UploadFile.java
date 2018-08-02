package webdriver_api;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Topic_08_UploadFile {
	private static final String WEBDRIVER = "webdriver.chrome.driver";
	private static final String CHROMEDRIVER = ".\\drivers\\chromedriver.exe";
	private static final String FILEPATH = "C:\\Users\\huyenlet\\git\\seleniumtraining2018Ver2\\WEBDRIVER_API_HUYENLT_02\\fileupload\\1.PNG";
	private static final String URL_01 = "http://blueimp.github.io/jQuery-File-Upload/";
	private static final String URL_02 = "https://encodable.com/uploaddemo/";
	private WebDriver driver;
	private RandomString rd;

	@BeforeMethod
	public void setUp() {
		System.setProperty(WEBDRIVER, CHROMEDRIVER);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		rd = new RandomString();
	}

	@Test
	public void test_01_uploadFileBySendkeyDirectly() throws InterruptedException {
		driver.get(URL_01);
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(FILEPATH);
		Thread.sleep(2000);
		Assert.assertEquals(driver.findElement(By.xpath("//p[@class='name']")).getText(), "1.PNG");
	}

	@Test
	public void test_02_uploadFileByAutoIT() throws InterruptedException, IOException {
		driver.get(URL_01);
		// click on add file button
		driver.findElement(By.cssSelector(".fileinput-button")).click();
		Runtime.getRuntime().exec(new String[] { ".\\libs\\chrome.exe", FILEPATH });
		Thread.sleep(2000);
		Assert.assertEquals(driver.findElement(By.xpath("//p[@class='name']")).getText(), "1.PNG");
	}

	@Test
	public void test_03_uploadFileByRobotClass() throws InterruptedException, AWTException {
		driver.get(URL_01);
		StringSelection fileSelect = new StringSelection(FILEPATH);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelect, null);
		driver.findElement(By.cssSelector(".fileinput-button")).click();

		Robot robot = new Robot();
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(1000);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		Thread.sleep(1000);

		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		Thread.sleep(1000);

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		Thread.sleep(3000);
		Assert.assertEquals(driver.findElement(By.xpath("//p[@class='name']")).getText(), "1.PNG");
	}

	@Test
	public void test_04_uploadFile() throws InterruptedException, AWTException {
		// navigate to link URL_02
		driver.get(URL_02);

		// Choose file to upload
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(FILEPATH);

		// Choose subdir folder
		Select slSubdir = new Select(driver.findElement(By.name("subdir1")));
		slSubdir.selectByValue("/");

		// Random a file name and put to the NewSubdir1
		String rdFileName = "huyenle" + rd.generateRandomString();
		System.out.println(rdFileName);
		driver.findElement(By.id("newsubdir1")).sendKeys(rdFileName);

		// Enter email
		driver.findElement(By.id("formfield-email_address")).sendKeys("lehuyen20595@gmail.com");

		// Enter First name
		driver.findElement(By.id("formfield-first_name")).sendKeys("Le Huyen");

		// Click on Upload button
		driver.findElement(By.id("uploadbutton")).click();

		// Wait for upload completely
		Thread.sleep(10000);

		// verify email, first name and file name after upload
		verifyText("lehuyen20595@gmail.com", "email");
		verifyText("Le Huyen", "first name");
		verifyText("1.PNG", "file upload");

		// Click on link View Uploaded Files
		driver.findElement(By.linkText("View Uploaded Files")).click();
		Thread.sleep(1000);

		// Click on the uploaded file
		driver.findElement(By.xpath("//a[text()='" + rdFileName + "']")).click();
		Thread.sleep(1000);

		// Verify uploaded file name
		Assert.assertEquals(driver.findElement(By.xpath("//a[@id='fclink-1PNG']")).getText(), "1.PNG");
	}

	public void verifyText(String expectText, String elementName) {
		String pageSource = driver.getPageSource();
		if (pageSource.contains(expectText)) {
			System.out.println("Verify " + elementName + " pass");
		} else {
			System.out.println("Verify " + elementName + " failed");
		}
		Assert.assertTrue(pageSource.contains(expectText), "Verify " + elementName + " pass");
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
