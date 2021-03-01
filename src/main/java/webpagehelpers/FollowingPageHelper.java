package webpagehelpers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageobjects.FollowingPage;

import java.util.List;
import java.util.stream.Collectors;

public class FollowingPageHelper {
    WebDriver driver;
    Logger logger = LoggerFactory.getLogger(FollowingPageHelper.class);
    public FollowingPageHelper(WebDriver driver) {
        this.driver = driver;
    }

    private List<String> getListOfVerifiedAccounts(){
      return driver.findElements(FollowingPage.getVerifiedAccountName()).stream()
              .map(WebElement::getText).collect(Collectors.toList());
    }

    public List<String> getListOf200VerifiedUsers() throws InterruptedException {
        List<String> listOfUsers = getListOfVerifiedAccounts();
        int numberOfScrolls = 0;
        while( (numberOfScrolls < 25) && (listOfUsers.size() < 200 ) ) {
            TwitterHomePage.scrollWindowHeight((JavascriptExecutor)driver,"100" );
            List<String> tempList = getListOfVerifiedAccounts();
            listOfUsers.addAll(tempList);
            listOfUsers = listOfUsers.stream().distinct().collect(Collectors.toList());
            numberOfScrolls++;
        }
        return listOfUsers;
    }


}
