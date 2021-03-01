package pageobjects;

import org.openqa.selenium.By;

public class LoginPage implements WebPage {
    private static final By loginScreenButton = By.xpath("//span[(text()='Log in')]");
    private static final By userName = By.xpath("//input[contains(@name,'username')]");
    private static final By passWord = By.xpath("//input[contains(@name,'password')]");
    private static final By loginButton = By.xpath("//div[@role='button']//span[(text()='Log in')]");

    public static By getUserName() {
        return userName;
    }

    public static By getPassWord() {
        return passWord;
    }

    public static By getLoginButton() {
        return loginButton;
    }

    public static By getLoginScreenButton() {
        return loginScreenButton;
    }
}
