package com.test.MailAccess.tests;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.test.MailAccess.common.CommonLibrary;
import com.test.MailAccess.pages.LoginPage_NOPCOM;

@Listeners(com.test.MailAccess.utilities.TestListeners.class)
public class TC_LoginTest_NOPCOM_001 extends CommonLibrary{
//	public static CommonLibrary common = null;
	public static LoginPage_NOPCOM loginPage = null;
//	public static Logger logger = null;
	
	public TC_LoginTest_NOPCOM_001() {
		DOMConfigurator.configure("log4j.xml");
//		common = new CommonLibrary();
		loginPage = new LoginPage_NOPCOM(CommonLibrary.driver);
		log = Logger.getLogger(TC_LoginTest_NOPCOM_001.class);
		log.info("In Constructor, initialization done.");
	}

	@Test(priority=1)
	public void loginAndverifyTitle() throws IOException, InterruptedException {
		new TC_LoginTest_NOPCOM_001();
		getURL("https://admin-demo.nopcommerce.com");
		waitTime(1);
		log.info("Entering username...");
		clearAndEnterText(loginPage.txtFieldEmail, getConfigProperty("usrName"));
		waitTime(1);
		log.info("Entering password...");
		clearAndEnterText(loginPage.txtFieldPassword, getConfigProperty("pwd"));
		waitTime(1);
		click(loginPage.btnLogin);
		log.info("Comparing page title...");
		Assert.assertEquals(getTitle(), "Dashboard / nopCommerce administration");
		waitTime(1);
		log.info("Signing out...");
		click(loginPage.btnSignout);
	}
}
