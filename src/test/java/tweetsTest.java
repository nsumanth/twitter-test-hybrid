import dto.TestDataDrivers;
import dto.TopFriends;
import dto.TopTweets;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import pageobjects.HomePage;
import reporter.TwitterHtmlReport;
import resthelpers.TwitterApiHelper;
import utils.twitterTestFileUtils;
import webpagehelpers.FollowingPageHelper;
import webpagehelpers.LoginPageHelper;
import webpagehelpers.SearchPageHelper;
import webpagehelpers.TwitterHomePage;

import java.io.IOException;
import java.util.*;

import static dto.TestDataDrivers.*;

@Listeners(TwitterHtmlReport.class)
public class tweetsTest {
    Logger logger = LoggerFactory.getLogger(tweetsTest.class);

    WebDriver driver;
    TwitterHomePage homePage;
    SearchPageHelper searchPage;
    LoginPageHelper loginPage;
    FollowingPageHelper followingPage;
    TwitterApiHelper twitterApiHelper;

    @BeforeClass
    public void beforeClass() throws IOException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        homePage = new TwitterHomePage(driver);
        searchPage = new SearchPageHelper(driver);
        loginPage = new LoginPageHelper(driver);
        followingPage = new FollowingPageHelper(driver);
        loginPage.loginUser();
    }

    @BeforeTest
    public void beforeTest() throws IOException {
        logger.info("Before Class");
        userList = twitterTestFileUtils.getUsersFromFile();
        logger.info("got users {}",userList);
        twitterApiHelper = new TwitterApiHelper();
        for(String user:userList){
            topTweetsMap.put(user.toUpperCase()+TOP_TWEETS,twitterApiHelper.getTweetsListsforUser(user));
            topFriendsMap.put(user.toUpperCase()+TOP_FRIENDS,twitterApiHelper.getFriendsListsforUser(user));
        }
    }

    @Test( dataProvider = "UserList")
    public void testChrome(String user) throws IOException, InterruptedException {
        homePage.goToHomePage( user);
        homePage.takeScreenShot( user);
        for (TopTweets tweet : topTweetsMap.get(user.toUpperCase()+TOP_TWEETS)) {
            logger.info("searching for top tweet with id {} and text {} at date{}",
                    tweet.getTweet_id() ,tweet.getTweet_text().substring(0,5),tweet.getTweet_date());
            String textToMatch = tweet.getTweet_text();
            homePage.scrollUntillElementVisible(Long.toString(tweet.getTweet_id()));
            homePage.takeScreenShot(user+tweet.getTweet_id());
            String textFromPage = homePage.getText(Long.toString(tweet.getTweet_id()));
            logger.info("text we got from page is {}",textFromPage);
            if(textFromPage.contains(textToMatch)){
                logger.info(" text matches to the page");
            } else {
                logger.error("text did not match");
            }
            String attributes =homePage.getRetweetsElement(Long.toString(tweet.getTweet_id()));
            logger.info("num of retweets from page is {}",attributes);
            int numRetweets = homePage.getNumberTweetAttributes(attributes,"Retweets");
            logger.info("num of retweets from page : {} and from api : {}",numRetweets,tweet.getRetweet_count());
            if(numRetweets == tweet.getRetweet_count()) {
                logger.info("retweets Match");
            } else {
                tweet.setRetweetDifference(tweet.getRetweet_count() - numRetweets);
            }
            int numFavorites = homePage.getNumberTweetAttributes(attributes,"Favorites");
            logger.info("num of Favorites from page : {} and from api : {}",numFavorites,tweet.getFavorite_count());
            if(numFavorites == tweet.getFavorite_count()) {
                logger.info("Favorites Match");
            } else {
                tweet.setFavoritedifference(tweet.getFavorite_count() - numFavorites);
            }
        }
        TopFriends friendSelected = topFriendsMap.get(user.toUpperCase()+TOP_FRIENDS).get(0);
        friendsTestMap.put(user+"_friend_selected",friendSelected.getScreen_name());
        homePage.searchForUser(friendSelected.getScreen_name());
        homePage.goToFollowersList(friendSelected.getScreen_name());
        List<String> list = followingPage.getListOf200VerifiedUsers();
        logger.info("{} is following {} users",friendSelected.getScreen_name(),list);
        friendsTestMap.put(user+"_"+friendSelected.getScreen_name()+"followers_List",list);
    }

    @DataProvider(name = "UserList")
    public Object[][] userListProvider(){
        return userList.stream()
                .map(user -> new Object[] { user })
                .toArray(Object[][]::new);
    }


    @AfterTest
    public void afterTest() {
        if (driver != null) {
            driver.quit();
        }
    }
}
