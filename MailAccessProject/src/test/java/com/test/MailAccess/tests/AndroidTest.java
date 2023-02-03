package com.test.MailAccess.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AndroidTest {
	
	public static AndroidDriver driver;
	
	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		WebDriverManager.chromedriver().setup();
		
		/**
		 * To run tests in Android Chrome browser 
		 */
		/* ChromeOptions options = new ChromeOptions();
//		options.addArguments("--no-first-run");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "RZ8N600WQJY");
//		capabilities.setCapability("udid", "adb-RZ8N600WQJY-k0jVUU._adb-tls-connect._tcp");
		capabilities.setCapability("platformName", "Android");
//		capabilities.setCapability("platformVersion", "11.0");
//		capabilities.setCapability("browserName", "chrome");
//		capabilities.setCapability("skipUnlock", "true");
		capabilities.setCapability("appPackage", "com.android.chrome");
		capabilities.setCapability("appActivity", "com.google.android.apps.chrome.Main");
//		capabilities.setCapability("noReset", "false");
//		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		options.merge(capabilities);
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.get("https://outlook.live.com/owa/"); */
		
		
		/**
		 * For Native Apps
		 */
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "RZ8N600WQJY");
//		capabilities.setCapability("platformVersion", "11.0");
		capabilities.setCapability("appPackage", "in.amazon.mShop.android.shopping");
		capabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");

		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		Thread.sleep(3000);
		driver.findElement(By.id("sso_continue")).click();
		
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//android.widget.ImageView[@content-desc=\"Menu. Contains your orders, your account, shop by department, programmes and features, settings and customer service Tab 4 of 4\"]")).click();
		
		Thread.sleep(5000);
		
//		Actions actions = new Actions(driver);

		
		/*Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@aria-label='Enter your email, phone, or Skype.']")).sendKeys("sa.sarvan@outlook.com");
		Thread.sleep(1000);
		driver.findElement(By.cssSelector("#idSIButton9")).click();
		Thread.sleep(1000);
		driver.findElement(By.cssSelector("input[name='passwd'],input[type='password']")).sendKeys("Abinav4413");
		Thread.sleep(1000);
		driver.findElement(By.cssSelector("#idSIButton9,input[value='Sign in']")).click();
		Thread.sleep(1000);
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
		    System.out.println(contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
		}
		driver.context("CHROMIUM");
		driver.findElement(By.cssSelector("#mectrl_headerPicture")).click(); */
//		Thread.sleep(1000);
//		driver.findElement(By.xpath("//*[contains(text(),'Sign out')]")).click();
//		Thread.sleep(2000);
		driver.quit();
	}

}
