package webpagehelpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageobjects.HomePage;
import pageobjects.LoginPage;
import utils.twitterTestFileUtils;

import java.io.IOException;

public class LoginPageHelper {

    WebDriver driver;
    WebDriverWait wait;
    Logger logger = LoggerFactory.getLogger(LoginPageHelper.class);

    public LoginPageHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,20);

    }

    public void loginUser() throws IOException {
        String username = twitterTestFileUtils.prop.getProperty("com.twitter.username");
        String password = twitterTestFileUtils.prop.getProperty("com.twitter.password");
        logger.info("logging in with {} and {}",username,password);
        driver.get(HomePage.getHomepageTitle());
        wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.getLoginScreenButton()));
        driver.findElement(LoginPage.getLoginScreenButton()).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.getUserName()));
        driver.findElement(LoginPage.getUserName()).sendKeys(username);
        driver.findElement(LoginPage.getPassWord()).sendKeys(password);
        driver.findElement(LoginPage.getLoginButton()).click();
        wait.until(ExpectedConditions.titleContains("Home"));
    }
}
