package Utility;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CW_Calendar {
public static void cw_cal(WebDriver driver,String date) throws InterruptedException{
		
		//click on month-year button
		
		driver.findElement(By.xpath("//div[@class='uib-datepicker ng-isolate-scope']/table/thead/tr/th[@colspan='6']")).click();
		Thread.sleep(3000);
		
		//Click on Year Button
		
		driver.findElement(By.xpath("//div[@class='uib-datepicker ng-isolate-scope']/table/thead/tr/th/button[@class='btn btn-default btn-sm uib-title']")).click();
		

		String splitter[] = date.split("-");
		int checkInyear = Integer.parseInt(splitter[2]);
		String checkInmonth = splitter[1];
		String checkInday = splitter[0];
		
		//select Year
	
		boolean Status = false;
		while(Status==false){
			try{
				WebElement Year = driver.findElement(By.xpath("//div[@class='uib-datepicker ng-isolate-scope']/table/tbody/tr/td/button/span[text()='"+checkInyear+"']"));
				if(Year.isDisplayed()==true){
					Year.click();
					//break;
					Status=true;
					break;
				}}
					catch (Exception e) {}
																
			arrow(checkInyear, driver);
		}
		
		//select Month
		
WebElement Month = driver.findElement(By.xpath("//div[@class='uib-datepicker ng-isolate-scope']/table/tbody/tr/td/button/span[text()='"+checkInmonth+"']"));
		Month.click();
	
		//select Date
		
		List<WebElement> days = driver.findElements(By.xpath("//div[@class='uib-datepicker ng-isolate-scope']/table/tbody/tr/td//span[@class='ng-binding'][text()='"+checkInday+"']"));
		
		try{for(WebElement d:days){
		System.out.println(d.getText());
		if(d.getText().equals(checkInday)&&d.isEnabled()==true){
		d.click();
		System.out.println("Date is clicked");}
		}}
		 
		catch(Exception e){}
		}
	
	//Comparing previous and next Year
	
public static void arrow(int checkInyear,WebDriver driver)
{
	if(checkInyear>2017==true){
		driver.findElement(By.xpath("//div[@class='uib-datepicker ng-isolate-scope']/table/thead/tr/th/button[@class='btn btn-default btn-sm pull-right uib-right']")).click();
		}
	else{
		driver.findElement(By.xpath("//div[@class='uib-datepicker ng-isolate-scope']/table/thead/tr/th/button[@class='btn btn-default btn-sm pull-left uib-left']")).click();
	}
	
}
}
