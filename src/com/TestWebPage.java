package com;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
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
			System.setProperty("webdriver.chrome.driver", "E:\\Selenium\\chromedriver_win32\\chromedriver.exe");
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
	public void testDoubleClickButton() {
		WebElement target = driver.findElement(By.id("dblClkBtn"));
		Actions act = new Actions(driver);
		act.doubleClick(target);
		Alert confirmBox = driver.switchTo().alert();
		Assert.assertEquals(confirmBox.getText(), "hi, JavaTpoint Testing");
		confirmBox.accept();
	}
	
	@Test
	public void testAlertBox() {
		//JavascriptExecutor js = (JavascriptExecutor)driver;  
	    //js.executeScript("scroll(0, 450)");
		driver.findElement(By.linkText("Generate Alert Box")).click();
		Alert alertBox = driver.switchTo().alert();
		Assert.assertTrue(alertBox.getText().contains("hi, JavaTpoint Testing"));
		alertBox.accept();
	}
	
	@Test
	public void testConfirmBox() {
	    //JavascriptExecutor js = (JavascriptExecutor)driver;  
	    //js.executeScript("scroll(0, 450)");  
		driver.findElement(By.linkText("Generate Alert Box")).click();
		Alert confirmBox = driver.switchTo().alert();
		Assert.assertTrue(confirmBox.getText().contains("Press a button!"));;
		confirmBox.accept();
		driver.findElement(By.id("demo")).getText().contains("You pressed OK!");
		driver.findElement(By.linkText("Generate Confirm Box")).click();
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
