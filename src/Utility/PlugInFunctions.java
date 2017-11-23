package Utility;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;

import com.google.common.base.Function;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class PlugInFunctions {

	public static ExtentReports reportGeneration(){
		
		ExtentReports report = new ExtentReports("/opt/tomcat/webapps/Report/" +Constants.reportName+ ".html",true);
		return report;
	}
	
	public static ExtentTest LogGeneration(String testName){
		
		ExtentTest logger = reportGeneration().startTest(testName);
		return logger;
	}
	
	/*public static void errorHandling(WebDriver driver, Boolean results, ExtentTest logger, String sText ){
		if( results == true )
			logger.log(LogStatus.PASS, sText + " is passed");
		else if ( results == false)
			{
			String screenShotpath = PlugInFunctions.takeScreenShot(driver, sText);
			String image = logger.addScreenCapture(screenShotpath);
			logger.log(LogStatus.FAIL, sText + " is failed", image);
			
			}
	}*/
	
	
	/*public static void warningHandling(WebDriver driver, Boolean results, ExtentTest logger, String sText ){
		if( results == true )
			logger.log(LogStatus.WARNING, sText + " is passed");
		else if ( results == false)
			{
			String screenShotpath = PlugInFunctions.takeScreenShot(driver, "LogIn_Error");
			String image = logger.addScreenCapture(screenShotpath);
			logger.log(LogStatus.WARNING, sText + " is failed", image);
			
			}
	}*/
	/*public static String takeScreenShot(WebDriver driver, String sName){
		
		try{
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String dest = "C:\\Users\\chinmoy.dey\\Desktop\\screen_shot"+"\\" +sName+ ".png";
			File destination = new File(dest);
			org.apache.commons.io.FileUtils.copyFile(source, destination);
			return dest;
		}
		catch (Exception e)
		{
			System.out.println("Exception while traking screenshot" + e.getMessage());
			return e.getMessage();
		}

	}*/
	
public static String takeScreenShotWithDynamicPath(WebDriver driver, String sName, String directoryPath){
		
		try{
			//directoryPath="/opt/workspace/FreshDirect/Report/04012017 - 04012017";
			String path=null;
			
			/*File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			toLocation = new File(ConstantsValuesProj.imagesPath + scrFile.getName());

			FileUtils.copyFile(scrFile, toLocation);*/
			
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			//String dest = directoryPath+"\\" +sName+ ".png";
			
			path=directoryPath.split("/")[directoryPath.split("/").length-1];
			String dest = sName+ ".png";
			
			File destination = new File(directoryPath+"/"+dest);
			org.apache.commons.io.FileUtils.copyFile(source, destination);
			return path+"/"+dest;
		}
		catch (Exception e)
		{
			System.out.println("Exception while traking screenshot" + e.getMessage());
			return e.getMessage();
		}

	}
	public static void AlertAccept(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 10);

		if (wait.until(ExpectedConditions.alertIsPresent()) == null)
			System.out.println("Not alert present");
		else {
			Alert alert = driver.switchTo().alert();
			System.out.println("Alert Message == " + alert.getText());
			alert.accept();
		}
	}

	private static Properties propGet;
	public static Properties loadXPathfile() {
		if (propGet == null) {
			propGet = new Properties();
			FileInputStream file = null;
			try {
				file = new FileInputStream(Constants.xPathfile);
				propGet.load(file);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return propGet;
	}

	public static void inputFieldEntry(WebDriver driver, WebElement element, String Value){		
	
		try {
			element.sendKeys(Value);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		js.executeScript("arguments[0].setAttribute('value', '"+Value+"')", element);
		System.out.println("Element" +element+ "value == " +element.getAttribute("value")+ ", against actual value ==" +Value);
		//wait.until(ExpectedConditions.attributeContains(element, "value", Value));
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.pollingEvery(2, TimeUnit.SECONDS)
				.withTimeout(60, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class,org.openqa.selenium.StaleElementReferenceException.class);

				Function<WebDriver, Boolean> predicate = new Function<WebDriver, Boolean>()
						{
							@Override
							public Boolean apply(WebDriver driver) {
								String eValue = element.getAttribute("Value");
								//wait.until(ExpectedConditions.attributeContains(element, "value", Value));
								//Boolean status = wait.until(ExpectedConditions.attributeToBeNotEmpty(element, "value"));
								System.out.println("Element is " +element+ " and Entered value is " +eValue);
								//if (eValue.equalsIgnoreCase(Value))
								if(eValue.equalsIgnoreCase(Value))
									{
									return true;
									}
								return false;
							}
						};
						
				wait.until(predicate);
				
	}
	
	
	public static void waitForAjaxRefresh(WebDriver driver) {
	       System.out.println("Waiting for Ajax Refresh");
	        try {
	            WebDriverWait wait = new WebDriverWait(driver,35);
	            final JavascriptExecutor javascript = (JavascriptExecutor) (driver instanceof JavascriptExecutor ? driver
	                    : null);

	            wait.until(new ExpectedCondition<Boolean>() {
	                @Override
	                public Boolean apply(WebDriver d) {
	                    boolean outcome = Boolean.parseBoolean(javascript
	                            .executeScript("return jQuery.active == 0")
	                            .toString());
	                    return outcome;
	                }
	            });
	        } catch (TimeoutException ex) {
	            throw new TimeoutException("Timed out after "
	                    + 35
	                    + " seconds while waiting for Ajax to complete.");
	        } catch (WebDriverException e) {
	            System.out.println("JQuery libraries are not present on page "
	                    + driver.getCurrentUrl() + " - "
	                    + driver.getTitle());
	        }
	    }
	
	
	public static void waitForPageLoad(WebDriver driver) {
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		final boolean response = js.executeScript("return document.readyState").equals("complete");
		WebDriverWait wait = new WebDriverWait(driver,20000,5000);
		/*wait.until(new Predicate<WebDriver>() {
			public boolean apply(WebDriver arg0) {
			return response;
			}
			});*/
		}
	
	public static void switchFrame(WebDriver driver, String xPath) {
		expWait(driver, xPath);
		driver.switchTo().frame(driver.findElement(By.xpath(xPath)));
	}

	
	
	
	public static WebElement expWait(WebDriver driver, String expEle) {
		WebElement element = null;
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			.pollingEvery(2, TimeUnit.SECONDS)
			.withTimeout(120, TimeUnit.SECONDS)
			.ignoring(NoSuchElementException.class,org.openqa.selenium.StaleElementReferenceException.class);

			Function<WebDriver, Boolean> predicate = new Function<WebDriver, Boolean>()
					{
						@Override
						public Boolean apply(WebDriver driver) {
							Boolean status = driver.findElement(By.xpath(expEle)).isEnabled();
							if (status == true)
								{
								return true;
								}
							return false;
						}
					};
					
			wait.until(predicate);
			
			element = driver.findElement(By.xpath(expEle));
			return element;
		
	}

	
	public static Boolean checkVisibiltyOfElement(WebDriver driver, String element) {
		
		Boolean visibility = null;
		try{
			WebDriverWait wait = new WebDriverWait(driver, 120);
			visibility = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(element)));
		}
		catch(TimeoutException e)
			{
			System.out.println("Expectected element is still avaiable after 3 minutes");
			}
		
		return visibility;
	}
	
	public static void impWait(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	
	public static void RemoveSpace(WebDriver driver)
	
	{
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_BACK_SPACE);
			robot.keyRelease(KeyEvent.VK_BACK_SPACE);
			PlugInFunctions.impWait(driver);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_TAB);
			
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void OVSLookUp(WebDriver driver, int popUpId, String Value, int rowId) {
		try {
			int temp = rowId - 1;
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_F4);
			robot.keyRelease(KeyEvent.VK_F4);
			driver.switchTo().defaultContent();
			//PlugInFunctions.popUpOpen(driver, PlugInFunctions.loadXPathfile().getProperty("popUpBlocker"));
			PlugInFunctions.switchFrame(driver,
					PlugInFunctions.loadXPathfile().getProperty("PopUpFrame1") + popUpId + PlugInFunctions.loadXPathfile().getProperty("common_xpathEnd"));
			if(!(Value.length() == 0))
				{
				Actions action = new Actions(driver);
				action.moveToElement(PlugInFunctions.expWait(driver, "//*[contains(@id,'OVSHandlerView._7')]"));
				//action.moveToElement(driver.findElement(By.xpath("//*[contains(@id,'OVSHandlerView._7')]")));
				action.click();
				action.sendKeys(Value);
				action.build().perform();
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				}
			PlugInFunctions.expWait(driver, "//*[contains(@id,'OVSHandlerView.Result:" + rowId + "." + temp + "')]").click();
			//PlugInFunctions.popUpClose(driver, PlugInFunctions.loadXPathfile().getProperty("popUpBlocker"));
			//driver.findElement(By.xpath("//*[contains(@id,'OVSHandlerView.Result:" + rowId + "." + temp + "')]")).click();
			driver.switchTo().defaultContent();
			if(popUpId == 0)
				{
				PlugInFunctions.switchFrame(driver, PlugInFunctions.loadXPathfile().getProperty("ContentFrame"));
				PlugInFunctions.switchFrame(driver, PlugInFunctions.loadXPathfile().getProperty("IsolatedFrame"));
				}
		} catch (AWTException e) {
			e.printStackTrace();
		}// catch (InterruptedException e) {
		//	e.printStackTrace();
		//}
	}

	
	public static void EVSLookUp(WebDriver driver, int popUpId, String Value, int rowId) {
		try {
			int temp = rowId - 1;
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_F4);
			robot.keyRelease(KeyEvent.VK_F4);
			//PlugInFunctions.popUpOpen(driver, PlugInFunctions.loadXPathfile().getProperty("popUpBlocker"));
			driver.switchTo().defaultContent();
			PlugInFunctions.switchFrame(driver,
					PlugInFunctions.loadXPathfile().getProperty("PopUpFrame1") + popUpId + PlugInFunctions.loadXPathfile().getProperty("common_xpathEnd"));
			//Thread.sleep(2000);
			if(!(Value.length() == 0))
				{
				Actions action = new Actions(driver);
				action.moveToElement(PlugInFunctions.expWait(driver, "//*[contains(@id,'EVSHandlerView.RootElement:2147483641')]"));
				//action.moveToElement(driver.findElement(By.xpath("//*[contains(@id,'EVSHandlerView.RootElement:2147483641')]")));
				action.click();
				action.sendKeys(Value);
				action.build().perform();
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				//Thread.sleep(2000);
				}
			PlugInFunctions.expWait(driver, "//*[contains(@id,'EVSHandlerView.RootElement:" + rowId + "." + temp + "')]").click();
			//PlugInFunctions.popUpClose(driver, PlugInFunctions.loadXPathfile().getProperty("popUpBlocker"));
			//driver.findElement(By.xpath("//*[contains(@id,'EVSHandlerView.RootElement:" + rowId + "." + temp + "')]")).click();
			driver.switchTo().defaultContent();
			if(popUpId == 0)
				{
				PlugInFunctions.switchFrame(driver, PlugInFunctions.loadXPathfile().getProperty("ContentFrame"));
				PlugInFunctions.switchFrame(driver, PlugInFunctions.loadXPathfile().getProperty("IsolatedFrame"));
				}
		} catch (AWTException e) {
			e.printStackTrace();
		}
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}

	}
	
	
	public static WebElement getElementByLocator( WebDriver driver, String expEle) {
		  System.out.println( "Get element by locator: " + expEle.toString() );  
		  final long startTime = System.currentTimeMillis();
		  
		  FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		    .withTimeout(30, TimeUnit.SECONDS)
		    .pollingEvery(5, TimeUnit.SECONDS)
		    .ignoring(org.openqa.selenium.NoSuchElementException.class)
			.ignoring(org.openqa.selenium.StaleElementReferenceException.class);
		 
		  int tries = 0;
		  boolean found = false;
		  WebElement we = null;
		  while ( (System.currentTimeMillis() - startTime) < 91000 ) {
			  System.out.println( "Searching for element. Try number " + (tries++) ); 
		   try {
		    we = wait.until( ExpectedConditions.presenceOfElementLocated(By.xpath(expEle)));
		    found = true;
		    break;
		   } catch ( StaleElementReferenceException e ) {      
			   System.out.println( "Stale element: \n" + e.getMessage() + "\n");
		   }
		  }
		  long endTime = System.currentTimeMillis();
		  long totalTime = endTime - startTime;
		  if ( found ) {
			  System.out.println("Found element after waiting for " + totalTime + " milliseconds." );
		  } else {
			  System.out.println( "Failed to find element after " + totalTime + " milliseconds." );
		  }
		  return we;
		}

	
	public class WebElementWait  extends FluentWait<WebElement>  {
	    public final static long DEFAULT_SLEEP_TIMEOUT = 500;

	      public WebElementWait(WebElement element, long timeOutInSeconds) {
	            this(element, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT);
	      }

	      public WebElementWait(WebElement element, long timeOutInSeconds, long sleepInMillis) {
	            this(element, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInSeconds, sleepInMillis);
	      }

	      protected WebElementWait(WebElement element, Clock clock, Sleeper sleeper, long timeOutInSeconds, long sleepTimeOut) {
	            super(element, clock, sleeper);
	            withTimeout(timeOutInSeconds, TimeUnit.SECONDS);
	            pollingEvery(sleepTimeOut, TimeUnit.MILLISECONDS);
	            ignoring(NotFoundException.class);
	      }

	}
	
	public boolean isAvailable(WebElement e, String xPath) {
	    try {
	        WebElementWait wait = new WebElementWait(e, 1);
	        //@SuppressWarnings("unused")
	        //WebElement icon = 
	        wait.until(new Function<WebElement, WebElement>() {
	                    public WebElement apply(WebElement d) {
	                        return d.findElement(By.xpath(xPath));
	                    }
	                });
	        return true;
	    } catch (TimeoutException exception) {
	        return false;
	    }
	}
	
	
	public static void popUpClose(WebDriver driver, String xPath){
		
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.pollingEvery(2, TimeUnit.SECONDS)
				.withTimeout(60, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class,org.openqa.selenium.StaleElementReferenceException.class);

				Function<WebDriver, Boolean> predicate = new Function<WebDriver, Boolean>()
						{
							@Override
							public Boolean apply(WebDriver driver) {
								driver.switchTo().defaultContent();
								String eValue = driver.findElement(By.xpath(xPath)).getAttribute("style");
								if(!(eValue.contains("block")))
									{
									return true;
									}
								return false;
							}
						};
						
				wait.until(predicate);
				
	}
	
	public static void popUpOpen(WebDriver driver, String xPath){
		
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.pollingEvery(2, TimeUnit.SECONDS)
				.withTimeout(60, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class,org.openqa.selenium.StaleElementReferenceException.class);

				Function<WebDriver, Boolean> predicate = new Function<WebDriver, Boolean>()
						{
							@Override
							public Boolean apply(WebDriver driver) {
								driver.switchTo().defaultContent();
								String eValue = driver.findElement(By.xpath(xPath)).getAttribute("style");
								if(eValue.contains("block"))
									{
									return true;
									}
								return false;
							}
						};
						
				wait.until(predicate);
				
	}
	
}
