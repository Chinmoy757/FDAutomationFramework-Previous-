package TestNG;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import Test.testClass;
import Utility.PlugInFunctions;
import Utility.openBrowser;

public class TestNG {
	ExtentReports report;
	ExtentTest logger;
	WebDriver driver;
	
	
	@BeforeTest
	@Parameters({"nodeIp","port"})
	public void createReport(String nodeIp,String port) throws InterruptedException, MalformedURLException{
		report = PlugInFunctions.reportGeneration();
		//logger.log(LogStatus.INFO, "Driver initiated");
	String 	nodeurl="http://"+nodeIp+":"+port+"/wd/hub";
	//String 	nodeurl="http://172.16.30.171:4441/wd/hub";
		DesiredCapabilities cap=new DesiredCapabilities().chrome();
		cap.setBrowserName("chrome");
		cap.setPlatform(Platform.WIN10);
		driver = new RemoteWebDriver(new URL(nodeurl), cap);
		
		driver.get("http://incturecwd:50000/oneapp/priceupdate/index.html#/details/dashboard"); //openBrowser.LaunchBrowser("chrome", "http://incturecwd:50000/oneapp/priceupdate/index.html#/details/dashboard");
		//logger.log(LogStatus.INFO, "Maximize the browser and Open the application");
	}
	
	@Test(dataProvider = "datas1", dataProviderClass = DataProviders.class)
	public void main(String fromDate, String toDate) throws InterruptedException, ParseException{
		Thread.sleep(10000);
		
		System.out.println("From: "+fromDate);
		System.out.println("ToDate: "+toDate);
		logger = report.startTest("Test Date: "+fromDate+" to "+toDate);
		testClass.test1(driver, fromDate, toDate, report, logger);
	}
	
	@AfterTest
	public void endReport() throws InterruptedException{
		report.endTest(logger);
		report.flush();
		Thread.sleep(3000);
		driver.close();
	}
}
