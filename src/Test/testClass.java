package Test;

import java.io.File;
import java.text.ParseException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Repository.Page1;
import Repository.Page2;
import Repository.Popup;
import Utility.CalenderController;
import Utility.Excel;
import Utility.PlugInFunctions;

public class testClass {
	static int rowCountAfterAdd;
	public static void test1(WebDriver driver, String fromDate, String toDate, ExtentReports report, ExtentTest logger) throws InterruptedException, ParseException{
		String fileName = "/opt/tomcat/webapps/Report/"+fromDate.replaceAll("/", "")+" - "+toDate.replaceAll("/", "");
		new File(fileName).mkdir();
		/*WebDriver driver;
		logger.log(LogStatus.INFO, "Driver intiantiated");
		driver = openBrowser.LaunchBrowser(browserName, url);
		logger.log(LogStatus.INFO, "Maximize the browser and Open the application");*/
		TestCase(driver,"OverWrite", fromDate, toDate, fileName, logger);
		logger.log(LogStatus.INFO, "Test Completed");
		
	}
	
	
	public static void TestCase(WebDriver driver, String action, String fromDate, String toDate, String fileName, ExtentTest logger) throws InterruptedException, ParseException{
		
		Excel ex=new Excel();
		String flow="";
		try {
			flow=	ex.xlReadCell("src/Resource/Test_Condition_Records.xlsx", 
					"Flow", "Flow", "yes");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(flow.equals("AM002"))
		driver.findElement(Page1.getElement_AM002()).click();
		else if(flow.equals("0066"))
			driver.findElement(Page1.getElement_0066()).click();
		
		Thread.sleep(5000);
		int rowCountBefore = driver.findElements(Page2.getRow()).size();
		//PlugInFunctions.takeScreenShotWithDynamicPath(driver, "Initially",fileName);
		logger.log(LogStatus.INFO, "Initially : "+logger.addScreenCapture(PlugInFunctions.takeScreenShotWithDynamicPath(driver, "Initially",fileName)));
		driver.findElement(Page2.getElement_addRow()).click();
		rowCountAfterAdd = driver.findElements(Page2.getRow()).size();
		if(rowCountAfterAdd == rowCountBefore+1){
			enterNewRow(driver, fromDate, toDate);
			Thread.sleep(5000);
			List<WebElement> radioButtons = driver.findElements(Popup.getRadioButtons());
			System.out.println(radioButtons.size());
			/*if(action.equalsIgnoreCase("overwrite")){
				
			}
			else if(action.equalsIgnoreCase("ignore")){
				
			}
			*/
			driver.findElement(Popup.getOkButton()).click();
			logger.log(LogStatus.INFO, action+" : "+logger.addScreenCapture(PlugInFunctions.takeScreenShotWithDynamicPath(driver, action,fileName)));
			Thread.sleep(4000);
			clickToFirstPage(driver);
			
		}
		else
			System.out.println("Addition didn't happen");
	}
	
	public static void enterNewRow(WebDriver driver, String enterFromDate, String enterToDate) throws InterruptedException, ParseException{
		List<WebElement> elementList = driver.findElements(By.xpath( "//table/tbody/tr/td/div/div[@class='sapMInputValHelp']/span[@class='sapMInputValHelpInner sapUiIcon sapUiIconMirrorInRTL']"));
		int size = elementList.size();
		WebElement fromDateElement = elementList.get(size-2);
		WebElement toDateElement = elementList.get(size-1);
		CalenderController.selectDate(driver, fromDateElement, enterFromDate);
		CalenderController.selectDate(driver, toDateElement, enterToDate);
	}
	
	public static void clickToFirstPage(WebDriver driver){
		driver.findElement(Page2.getBackButton()).click();
	}
}
