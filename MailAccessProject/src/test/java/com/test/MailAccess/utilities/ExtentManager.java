package com.test.MailAccess.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	private static ExtentReports extentReports;
	
	public static ExtentReports createInstance() {
		ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(".\\reports\\" + fileName());
		extentSparkReporter.config().setEncoding("utf-8");
		extentSparkReporter.config().setDocumentTitle("Automation Report");
		extentSparkReporter.config().setReportName("Automation Test Results");
		extentSparkReporter.config().setTheme(Theme.DARK);
		
		extentReports = new ExtentReports();
		extentReports.setSystemInfo("OS", "Windows 10");
		extentReports.setSystemInfo("Browser", "Chrome");
		extentReports.attachReporter(extentSparkReporter);
//		extentReports.attachReporter(null);
		
		return extentReports;
	}
	
	public static String fileName() {
//		Date date = new Date();
//		String fileName = "AutomationReport-" + date.toString().replace(":", ".").replace(" ", "_") + ".html";
		String fileName = "AutomationReport-" + new SimpleDateFormat("dd-MM-yyyy_HH.mm.ss").format(new Date()) + ".html";
		return fileName;
	}

}
