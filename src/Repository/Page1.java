package Repository;

import org.openqa.selenium.By;

public class Page1 {
	static By element_AM002 = By.xpath("//table[@class = 'sapMListModeSingleSelectMaster sapMListShowSeparatorsAll sapMListTbl sapMListUl']/tbody/tr/td[1]/span[contains(text(),'A-M0002')]");
	
	static By element_0066 = By.xpath("//table[@class = 'sapMListModeSingleSelectMaster sapMListShowSeparatorsAll sapMListTbl sapMListUl']/tbody/tr/td[1]/span[contains(text(),'000000000000000066')]");

	public static By getElement_AM002() {
		return element_AM002;
	}
	public static By getElement_0066() {
		return element_0066;
	}
	 
}
