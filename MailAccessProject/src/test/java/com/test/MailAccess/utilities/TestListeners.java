package com.test.MailAccess.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.test.MailAccess.common.CommonLibrary;

public class TestListeners implements ITestListener {
	
	private static ExtentReports extentReports = ExtentManager.createInstance();
	
	/**
	 * Why do we need to make extentTest as ThreadLocal, refer
	 * 
	 * https://automationinja.medium.com/generating-extent-report-during-parallel-testing-on-suite-level-thread-safe-extent-report-5c3e7457d041
	 */
//	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	public Logger logger = Logger.getLogger(TestListeners.class);
	public static ExtentTest test;
	
	@Override
	public void onTestStart(ITestResult result) {
//		ExtentTest test = extentReports.createTest(result.getTestClass().getName() + " :: " + result.getMethod().getMethodName());
		test = extentReports.createTest(result.getTestClass().getName() + " :: " + result.getMethod().getMethodName());
//		extentTest.set(test);
		logger.info("In Test Class-'" + result.getTestClass().getName() + "', Testcase-'" + result.getMethod().getMethodName() + "' started...");
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		String logText = "<b> Test Method " + result.getMethod().getMethodName() + " Successful<b>";
		Markup markup = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
//		extentTest.get().log(Status.PASS, markup);
		test.log(Status.PASS, markup);
		logger.info("Test " + result.getName() + " Success");
	}
	
	public void onTestFailure(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		test.fail("<details><summary><b><font color=red> Exception occurred, click to see details:</font>"
				+ "</b></summary>" + exceptionMessage.replaceAll(",", "<br>") + "</details> \n");
//		extentTest.get().fail("<details><summary><b><font color=red> Exception occurred, click to see details:</font>"
//								+ "</b></summary>" + exceptionMessage.replaceAll(",", "<br>") + "</details> \n");
		
//		WebDriver driver = ((CommonLibrary)result.getInstance()).driver;
		String path = takeScreenshot(CommonLibrary.driver, result.getMethod().getMethodName());
		try {
			test.fail("<b><font color=red>" + "Screenshot of failure" + "</font></b>",
					MediaEntityBuilder.createScreenCaptureFromPath(path).build());
//			extentTest.get().fail("<b><font color=red>" + "Screenshot of failure" + "</font></b>",
//					MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		} catch (Exception e) {
			test.fail("Test failed, cannot attach screenshot");
//			extentTest.get().fail("Test failed, cannot attach screenshot");
		}
		
		String logText = "<b> TestMethod " + methodName + " Failed</b>";
		Markup markup = MarkupHelper.createLabel(logText, ExtentColor.RED);
		test.log(Status.FAIL, markup);
//		extentTest.get().log(Status.FAIL, markup);
		logger.info("Test " + result.getName() + " Failed");
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		String logText = "<b> Test Method " + result.getMethod().getMethodName() + " Skipped</b>";
		Markup markup = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		test.log(Status.SKIP, markup);
//		extentTest.get().log(Status.SKIP, markup);
		logger.info("Test " + result.getName() + " Skipped");
//		throw new SkipException("Test case Skipped") ;
	}
	
	@Override
	public void onStart(ITestContext context) {
		
	}
	
	@Override
	public void onFinish(ITestContext context) {
		if (extentReports != null) {
			extentReports.flush();
		}
	}
	
	public String takeScreenshot(WebDriver driver, String methodName) {
		String fileName = getScreenshotName(methodName);
		String directory = System.getProperty("user.dir") + "/screenshots/";
		new File(directory).mkdirs();
		String path = directory + fileName;
		
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(path));
			logger.info("Screenshot stored at: " + path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
	
	public static String getScreenshotName(String methodName) {
//		Date date = new Date();
//		String fileName = methodName + "_" + date.toString().replace(":", ".").replace(" ", "_") + ".png";
		String fileName = methodName + "_" + new SimpleDateFormat("dd-MM-yyyy_HH.mm.ss").format(new Date()) + ".png";
		return fileName;
	}
	
}
