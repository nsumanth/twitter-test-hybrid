package pageobjects;

import org.openqa.selenium.By;

public class HomePage implements WebPage {
    public static final String homepageTitle = "https://twitter.com/";
    private static final By SearchBox = By.xpath("//input[@data-testid='SearchBox_Search_Input']");

    public static By getFollowersLink(String username) {
        return By.xpath("//*[contains(@href,'/"+username+"/following') and (@role='link')]");
    }

    public static By getSearchedUser( String User){
        return By.xpath("//span[text()='@"+User+"']/ancestor::*[9]");
    }
    public static By getTweetElement(String id){
        return By.xpath("//a[contains(@href,'"+id+"') and @aria-label]");
    }

    public static By getTweetTextFromElement(String id){
        return By.xpath("//a[contains(@href,'"+id+"') and @aria-label]/ancestor::*[6]//div[@dir='auto']//span");
    }

    public static String getHomepageTitle() {
        return homepageTitle;
    }

    public static By getSearchBox() {
        return SearchBox;
    }

    public static By getNumberofRetweets(String text){
        return By.xpath("//a[contains(@href,'"+text+"') and @aria-label]/ancestor::*[6]//*[contains(@aria-label,'replies')]");
    }

}
