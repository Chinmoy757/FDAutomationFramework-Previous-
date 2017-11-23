package Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CalenderController {
	public static void selectDate(WebDriver driver, WebElement dateWidget, String expectedDate) throws InterruptedException, ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date parse = sdf.parse(expectedDate);
		Calendar c = Calendar.getInstance();
		c.setTime(parse);
		String month = Month.of(c.get(Calendar.MONTH)+1).name();
		String date = String.valueOf(c.get(Calendar.DATE));
		String year = String.valueOf(c.get(Calendar.YEAR));
		
		dateWidget.click();
		Thread.sleep(3000);
		CalenderController sl = new CalenderController();
		sl.getMonth(dateWidget, month).click();
		sl.getYear(dateWidget, year).click();
		sl.getDay(dateWidget, date).click();
	}
	
	public WebElement getDay(WebElement dateWidget, String day){
		List<WebElement> columns=dateWidget.findElements(By.xpath("//div[@class='sapUiCalItems']/div/div"));
		int dayInInt = Integer.parseInt(day);
		List<WebElement> el = new ArrayList<WebElement>();
		for (WebElement cell : columns)
        {
			
			if (cell.getText().equals(day))
           {
              el.add(cell);
           }
        }
		if(dayInInt>0 && dayInInt<25){
			return el.get(0);
		}
		return el.get(el.size()-1);
	}
	
	public WebElement getMonth(WebElement dateWidget, String month){
		dateWidget.findElement(By.xpath("(//div[@class='sapUiCalHead']/button[@class='sapUiCalHeadB sapUiCalHeadB1 sapUiCalHeadBFirst'])[last()]")).click();
		List<WebElement> columns= dateWidget.findElements(By.xpath("//div[@class='sapUiCalMonthPicker']/div/div"));
		for(WebElement cell : columns)
		{
			 if(cell.getText().equalsIgnoreCase(month))
	         {
	        	 return cell;
	         }
		}
		return null;
	}
	
	public WebElement getYear(WebElement dateWidget, String year){
		dateWidget.findElement(By.xpath("(//div[@class='sapUiCalHead']/button[@class='sapUiCalHeadB sapUiCalHeadB2 sapUiCalHeadBLast'])[last()]")).click();
		int maxYearShowed = getMaxYearShowed(dateWidget);
		int minYearShowed = getMinYearShowed(dateWidget);
		if(Integer.parseInt(year)>maxYearShowed){
			do{
				clickNextIcon(dateWidget);
				maxYearShowed = getMaxYearShowed(dateWidget);
			}while(Integer.parseInt(year)>maxYearShowed);
		}
		else if(Integer.parseInt(year)<minYearShowed){
			do{
				clickPreviousIcon(dateWidget);
				minYearShowed = getMinYearShowed(dateWidget);
			}while(Integer.parseInt(year)<minYearShowed);
		}
		List<WebElement> columns= dateWidget.findElements(By.xpath("//div[@class='sapUiCalYearPicker']/div/div"));
		for(WebElement cell : columns)
		{
			 if(cell.getText().equals(year))
	         {
	        	 return cell;
	         }
		}
		return null;
	}
	
	public static void clickNextIcon(WebElement dateWidget){
		dateWidget.findElement(By.xpath("//div[@class='sapUiCalHead']/button[@class='sapUiCalHeadNext']")).click();
	}
	
	public static void clickPreviousIcon(WebElement dateWidget){
		dateWidget.findElement(By.xpath("//div[@class='sapUiCalHead']/button[@class='sapUiCalHeadPrev']")).click();
	}
	
	public static int getMaxYearShowed(WebElement dateWidget){
		List<WebElement> columns= dateWidget.findElements(By.xpath("//div[@class='sapUiCalYearPicker']/div/div"));
		int colSize = columns.size();
		return Integer.parseInt(columns.get(colSize-1).getText());
	}
	
	public static int getMinYearShowed(WebElement dateWidget){
		List<WebElement> columns= dateWidget.findElements(By.xpath("(//div[@class='sapUiCalYearPicker'])[last()]/div/div"));
		return Integer.parseInt(columns.get(0).getText());
	}
}
