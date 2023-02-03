package com.test.MailAccess.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.LogManager;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;


public class CommonLibrary {
	public Properties properties = null;
	public FileInputStream fis = null;
	public Logger log = null;
	public static WebDriver driver;
	
	@Parameters("browser")
	@BeforeClass
	public void setUp(String browser) throws IOException {
		DOMConfigurator.configure("log4j.xml");
		log = Logger.getLogger(CommonLibrary.class);
//		launchBrowser(browser);
		log.info("From setUp method...");
		
		if (browser.equals("chrome")) {
//		case "chrome":
			WebDriverManager.chromedriver().setup();
			LogManager.getLogManager().reset();
			driver = new ChromeDriver();
			log.info("Chrome browser launched...");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(getConfigProperty("implicitWait"))));
//			driver.get(getConfigProperty("url"));
//			break;
		} else if (browser.equals("firefox")) {
//		case "firefox":
			System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "null");
			LogManager.getLogManager().reset();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(getConfigProperty("implicitWait"))));
//			driver.get(getConfigProperty("url"));
//			break;
		} else if (browser.equals("edge")) {
//		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(getConfigProperty("implicitWait"))));
//			driver.get(getConfigProperty("url"));
//			break;
	}
	}
	

	public String getConfigProperty(String conigPropertyName) throws IOException {
		properties = new Properties();
		fis = new FileInputStream("config.properties");
		properties.load(fis);
		String configPropertyValue = properties.getProperty(conigPropertyName);
		return configPropertyValue;
	}
	
	
	/*public void launchBrowser(String browser) throws IOException {
//		switch (getConfigProperty("browser").toLowerCase()) {
		switch ("browser") {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				LogManager.getLogManager().reset();
				driver = new ChromeDriver();
				log.info("Chrome browser launched...");
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(getConfigProperty("implicitWait"))));
//				driver.get(getConfigProperty("url"));
				break;
				
			case "firefox":
				System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
				System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "null");
				LogManager.getLogManager().reset();
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(getConfigProperty("implicitWait"))));
//				driver.get(getConfigProperty("url"));
				break;
				
			case "edge":
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(getConfigProperty("implicitWait"))));
//				driver.get(getConfigProperty("url"));
				break;
		}
	} */
	
	
	public String[] readExcelByRow(String sheetName, int rowNumber) throws FileNotFoundException, IOException {
		XSSFWorkbook workBook = new XSSFWorkbook(new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\TestData.xlsx"));
		XSSFSheet sheet = workBook.getSheet(sheetName);
		
		int cols = sheet.getRow(rowNumber).getPhysicalNumberOfCells();
		String[] array = new String[cols];
		XSSFRow row = sheet.getRow(rowNumber);
		for (int c = 0; c < cols; c++) {
			XSSFCell cell = row.getCell(c);
			switch (cell.getCellType()) {
			case STRING:
				array[c] = cell.getStringCellValue();
				break;
				
			case NUMERIC:
				array[c] = String.valueOf(cell.getNumericCellValue());
				break;
				
			default:
				break;
			}
		}
		return array;
	}
	
	public boolean isElementDisplayed(WebElement element) {
		if (element.isDisplayed()) {
			return true;
		} 
		return false;
	}
	
	public void getURL(String url) {
		driver.get(url);
	}
	
	public boolean isElementEnabled(WebElement element) {
		if (element.isEnabled()) {
			return true;
		} 
		return false;
	}
	
	public void clearAndEnterText(WebElement element, String txtToEnter) {
		element.clear();
		element.sendKeys(txtToEnter);
		log.info("Text entered...");
	}
	
	public String getTitle() {
		return driver.getTitle();
	}
	
//	public void quit() {
//		driver.quit();
//	}
	
	public void click(WebElement element) {
		element.click();
	}
	
	public void waitTime(int seconds) throws InterruptedException {
		Thread.sleep(1000 * seconds);
	}
	
	public String getText(WebElement element) {
		return element.getText();
	}
	
	public void compareText(String actualTxt, String expectedTxt) {
		System.out.println("Actual Text: " + actualTxt);
		System.out.println("Expected Text: " + expectedTxt);
		Assert.assertEquals(actualTxt, expectedTxt);
	}
	
	public String randomString() {
		return RandomStringUtils.randomAlphabetic(5);
	}
	
	public String randomNumeric() {
		return RandomStringUtils.randomNumeric(5);
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
