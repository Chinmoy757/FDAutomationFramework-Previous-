package Utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class openBrowser {
	
	static WebDriver driver;
	public static WebDriver LaunchBrowser(String browserName, String url) throws InterruptedException {
		// TODO Auto-generated method stub
		if(browserName.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", "E:/chromedriver.exe");
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(url);			
		}
		return driver;
	}

}
