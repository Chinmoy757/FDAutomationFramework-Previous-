package Repository;

import org.openqa.selenium.By;

public class Page2 {
	static By addRow = By.xpath("//button[@title='Add']/div/span");
	static By row = By.xpath("//table[@aria-labelledby='__xmlview3--PRICE_UPDATE_MAT_TABLE-labelledby']/tbody/tr");
	static By backButton = By.xpath("//button[@title='Navigate Back']/div/span");
	public static By getBackButton() {
		return backButton;
	}
	public static By getRow() {
		return row;
	}
	public static By getElement_addRow() {
		return addRow;
	}
}
