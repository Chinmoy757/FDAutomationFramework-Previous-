package Repository;

import org.openqa.selenium.By;

public class Popup {
	static By radioButtons = By.xpath("//*[@id='__button63-Button']/div");
	static By okButton = By.xpath("//div[@class='sapMDialog sapMDialog-CTX sapMPopup-CTX sapUiPopupWithPadding sapUiShd sapMDialogOpen']/footer/button/div/span");
	public static By getOkButton() {
		return okButton;
	}
	public static By getRadioButtons() {
		return radioButtons;
	}
}
