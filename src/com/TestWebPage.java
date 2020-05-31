package com;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestWebPage {
	
	private WebDriver driver;
	
	@BeforeClass(alwaysRun = true)
	@Parameters("browser")			// To Run On Parallel Browser
	  public void setUp(String browser) throws Exception {
		if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "E:\\Selenium\\chromedriver_win32_83\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if (browser.equalsIgnoreCase("FireFox")) {
			System.setProperty("webdriver.gecko.driver", "E:\\Selenium\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    driver.get("https://www.testandquiz.com/selenium/testing.html");
	  }
	
	@Test
	public void testWebPageTitle() {
		Assert.assertTrue(driver.getTitle().contains("Sample Test"),"Application Title Page is Wrong");		
	}
	
	@Test
	public void testRedirectLink() {
		driver.findElement(By.linkText("This is a link")).click();
		Assert.assertTrue(driver.getTitle().contains("Tutorials List - Javatpoint"),"Redirected Title Page is Wrong");
		driver.navigate().back();
	}
	
	@Test
	public void testPageHeading() {
		Assert.assertEquals(driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='This is sample webpage with dummy elements that will help you in learning selenium automation.'])[1]/preceding::div[1]")).getText(), "Sample WebPage for Automation Testing");;
	}
	
	@Test
	public void testDrowpDown() {
		Select dropDown = new Select(driver.findElement(By.id("testingDropdown")));
		dropDown.selectByValue("Manual");
		dropDown.selectByValue("Database");
		dropDown.selectByValue("Performance");
	}
	
	@Test
	public void testRadioButton() {
		Assert.assertFalse(driver.findElement(By.xpath("//input[@id='male']")).isSelected());
		Assert.assertFalse(driver.findElement(By.xpath("//input[@id='female']")).isSelected());
		driver.findElement(By.xpath("//input[@id='female']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//input[@id='female']")).isSelected());
		driver.findElement(By.xpath("//input[@id='male']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//input[@id='male']")).isSelected());
		Assert.assertFalse(driver.findElement(By.xpath("//input[@id='female']")).isSelected());
	}
	
	@Test
	public void testCheckBox() {
		Assert.assertFalse(driver.findElement(By.className("Automation")).isSelected());
		Assert.assertFalse(driver.findElement(By.className("Performance")).isSelected());
		driver.findElement(By.className("Performance")).click();
		Assert.assertTrue(driver.findElement(By.className("Performance")).isSelected());
	}
	
	@Test
	public void testDoubleClickButton() {
		Actions act = new Actions(driver);
		WebElement target = driver.findElement(By.id("dblClkBtn"));
		act.doubleClick(target).perform();
		Alert confirmBox = driver.switchTo().alert();
		Assert.assertEquals(confirmBox.getText(), "hi, JavaTpoint Testing");
		confirmBox.accept();
	}
	
	@Test
	public void testAlertBox() {
		WebElement inputElement = driver.findElement(By.xpath("//button[starts-with(@onclick,'alert(')]"));
		((JavascriptExecutor)driver).executeScript("arguments[0].checked = true;", inputElement);
		inputElement.click();
		Alert alertBox = driver.switchTo().alert();
		Assert.assertTrue(alertBox.getText().contains("hi, JavaTpoint Testing"));
		alertBox.accept();
	}
	
	@Test
	public void testConfirmBox() {  
		WebElement inputElement = driver.findElement(By.xpath("//button[starts-with(@onclick,'generateConfirmBox()')]"));
		((JavascriptExecutor)driver).executeScript("arguments[0].checked = true;", inputElement);
		inputElement.click();
		Alert confirmBox = driver.switchTo().alert();
		Assert.assertTrue(confirmBox.getText().contains("Press a button!"));;
		confirmBox.accept();
		driver.findElement(By.id("demo")).getText().contains("You pressed OK!");
		driver.findElement(By.xpath("//button[starts-with(@onclick,'generateConfirmBox()')]")).click();
		confirmBox.dismiss();
		driver.findElement(By.id("demo")).getText().contains("You pressed Cancel!");
	}
	
	@Test
	public void testDragandDrop() {
		WebElement from = driver.findElement(By.id("targetDiv"));
		WebElement to = driver.findElement(By.id("sourceImage"));
		Actions act = new Actions(driver);
		act.dragAndDrop(from, to).build().perform();
	}
	
	@AfterClass(alwaysRun=true)
	public void tearDown() throws Exception {
		driver.quit();
	}

}
