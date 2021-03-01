package webpagehelpers;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageobjects.HomePage;
import utils.twitterTestFileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TwitterHomePage {
    WebDriver driver;
    WebDriverWait wait;
    Logger logger = LoggerFactory.getLogger(TwitterHomePage.class);

    public TwitterHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,20);
    }

    public void goToHomePage(String userName) {
        logger.info("home page {}",HomePage.homepageTitle+userName);
        driver.get(HomePage.homepageTitle+userName);
        driver.manage().window().maximize();
        ((JavascriptExecutor)driver).executeScript("document.body.style.zoom = '0.8'");

    }

    public void goToFollowersList(String username) throws InterruptedException {
        logger.info("clicking followers list page for   {}",username);
        wait.until(ExpectedConditions.elementToBeClickable(HomePage.getFollowersLink(username)));
        System.out.println(driver.findElement(HomePage.getFollowersLink(username)).getText());
        wait.until(ExpectedConditions.visibilityOfElementLocated(HomePage.getFollowersLink(username)));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();",
                driver.findElement(HomePage.getFollowersLink(username)));
        Thread.sleep(5000);
    }

    public void searchForUser(String user) throws InterruptedException {
        logger.info("Searching for user {} in Search ",user);
        wait.until(ExpectedConditions.visibilityOfElementLocated(HomePage.getSearchBox()));
        driver.findElement(HomePage.getSearchBox()).sendKeys(user);
        clickOnSearchedUser(user);
        wait.until(ExpectedConditions.titleContains(user));
    }

    public void clickOnSearchedUser(String user) throws InterruptedException {
        logger.info("clicking on user {} after Search ",user);
        wait.until(ExpectedConditions.visibilityOfElementLocated(HomePage.getSearchedUser(user)));
        WebElement element = driver.findElement(HomePage.getSearchedUser(user));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(HomePage.getSearchedUser(user)));
        element.sendKeys(Keys.ENTER);
    }

    public void takeScreenShot(String fileName) throws IOException {
        TakesScreenshot screenShot =((TakesScreenshot)driver);
        File screen = screenShot.getScreenshotAs(OutputType.FILE);
        twitterTestFileUtils.createDirectories("reports/");
        twitterTestFileUtils.copyFiles(screen,new File(fileName+".jpg"));
    }

    public void scrollUntillElementVisible( String  element) throws InterruptedException {
        logger.info("scrolling for element {}",element);
        scrollToTop();
        while(! isElementPresent(HomePage.getTweetElement(element))) {
            scrollWindowHeight((JavascriptExecutor)driver,"100");
        }
    }

    public String getRetweetsElement(String text) {
        return driver.findElement(HomePage.getNumberofRetweets(text)).getAttribute("aria-label");
    }

    public int getNumberTweetAttributes(String tweetNumbers,String attribute) {
        String[] attributes = tweetNumbers.split(",");
        if("Retweets".equals(attribute)){
            return Integer.parseInt(attributes[1].split(" ")[1]);
        } else if ("Favorites".equals(attribute)) {
            return Integer.parseInt(attributes[2].split(" ")[1]);
        } else return 0;

    }

    public String getText(String id){
        List<WebElement> elements = driver.findElements(HomePage.getTweetTextFromElement(id));
        StringBuilder textFromTweet = new StringBuilder();
        for(WebElement ele :elements ) {
            textFromTweet.append(ele.getText());
        }
        return textFromTweet.toString();
    }
    private boolean isElementPresent(By element) throws InterruptedException {
        try{
            WebElement element1 = driver.findElement(element);
            if(element1.isDisplayed()){
                scrollIntoView((JavascriptExecutor)driver,element1);
                Thread.sleep(1000);
                return true;
            }
        } catch (NoSuchElementException exception){
            return false ;
        }
        return false;
    }

    private void scrollIntoView(JavascriptExecutor driver,WebElement element) throws InterruptedException {
        driver.executeScript("arguments[0].scrollIntoView();",element);
    }

    private void scrollToTop(){
        logger.info("scrolling to top of page");
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
    }

    public static void scrollWindowHeight(JavascriptExecutor driver,String scrollTarget) throws InterruptedException {
        driver.executeScript("window.scrollBy(0,"+ scrollTarget+")","");
        Thread.sleep(1000);
    }

}
