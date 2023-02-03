package com.test.MailAccess.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.test.MailAccess.common.CommonLibrary;
import com.test.MailAccess.pages.LoginPage_Outlook;

@Listeners(com.test.MailAccess.utilities.TestListeners.class)
public class TC_LoginTest_Outlook_001 extends CommonLibrary {
//	public static CommonLibrary common = null;
	public static LoginPage_Outlook loginOutlook = null;
//	public static Logger logger = null;
	
	public TC_LoginTest_Outlook_001() {
		DOMConfigurator.configure("log4j.xml");
//		common = new CommonLibrary();
		loginOutlook = new LoginPage_Outlook(CommonLibrary.driver);
//		loginOutlook = new LoginPage_Outlook();
		log = Logger.getLogger(TC_LoginTest_Outlook_001.class);
		log.info("Initialization completed.");
	}
	
	
	
	@Test(dataProvider="LoginData")
	public void loginTest(String userName, String pwd) throws InterruptedException, FileNotFoundException, IOException {
		new TC_LoginTest_Outlook_001();
		getURL("https://outlook.live.com/owa/");
		waitTime(1);
		click(loginOutlook.signInBtn);
		waitTime(1);
//		String[] credentials = readExcelByRow("Sheet0", 0);
//		String userName = credentials[0];
//		String pwd = credentials[1];
		log.info("Entering Username...");
		clearAndEnterText(loginOutlook.emailTxtField, userName);
		waitTime(1);
		click(loginOutlook.nextBtn);
		waitTime(1);
		log.info("Entering Password...");
		clearAndEnterText(loginOutlook.pwdTxtField, pwd);
		waitTime(1);
		click(loginOutlook.loginBtn);
		waitTime(1);
		click(loginOutlook.confirmBtn);
		waitTime(5);
		click(loginOutlook.accManagerLink);
		waitTime(2);
		log.info("Checking account name...");
		compareText(loginOutlook.accName.getText(), userName);
		waitTime(1);
		click(loginOutlook.signOutButton);
		log.info("Signed out...");
		
	}
	
	@DataProvider(name="LoginData")
	public String[][] getCredentials() throws IOException {
		XSSFWorkbook workBook = new XSSFWorkbook(new FileInputStream(System.getProperty("user.dir")+ "\\src\\test\\resources\\TestData.xlsx"));
		XSSFSheet sheet = workBook.getSheetAt(0);
		
		int rows = sheet.getPhysicalNumberOfRows();
		int cols = sheet.getRow(0).getPhysicalNumberOfCells();
		
		String[][] data = new String[rows][cols];
		
		for (int r = 0; r < rows; r++) {
			XSSFRow row = sheet.getRow(r);
			for (int c = 0; c < cols; c++) {
				XSSFCell cell = row.getCell(c);
				data[r][c] = cell.getStringCellValue();
			}
		}
		return data;
	}

}
