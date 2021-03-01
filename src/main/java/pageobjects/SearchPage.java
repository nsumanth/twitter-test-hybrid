package pageobjects;

import org.openqa.selenium.By;

public class SearchPage implements WebPage {

    public static final By peopleSpan = By.xpath("//a//span[text()='People']");
    public static final By searchTimeLine = By.xpath("//*[contains(@aria-label,'Search timeline')]");
    public static By getUserURL(String userName) {
        return By.xpath("//*[contains(@aria-label,'Search timeline')]//a[@href='/"+userName+"']");
    }

}
