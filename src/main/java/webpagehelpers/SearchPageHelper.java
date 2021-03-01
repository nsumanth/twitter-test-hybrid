package webpagehelpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.SearchPage;

public class SearchPageHelper {
    WebDriver driver;
    public SearchPageHelper(WebDriver driver) {
        this.driver = driver;
    }
    public  void clickOnUser(String user) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(SearchPage.searchTimeLine));
        Thread.sleep(4000);
        wait.until(ExpectedConditions.elementToBeClickable(SearchPage.peopleSpan));
        Actions actions  = new Actions(driver);
        actions.click(driver.findElement(SearchPage.peopleSpan));
        wait.until(ExpectedConditions.elementToBeClickable(SearchPage.getUserURL(user)));
        driver.findElement(SearchPage.getUserURL(user)).click();
        Thread.sleep(4000);
    }

}
