package pageobjects;

import org.openqa.selenium.By;

public class FollowingPage implements WebPage {
    private static final By verifiedAccountName = By.xpath("//*[@aria-label='Verified account']/ancestor::*[3]//span[contains(text(),'@')]");

    public static By getVerifiedAccountName() {
        return verifiedAccountName;
    }
}
