package resthelpers;

import dto.TopFriends;
import dto.TopTweets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequests;
import io.restassured.response.Response;
import utils.twitterTestFileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dto.TestDataDrivers.userList;

public class TwitterApiHelper {
    Logger logger = LoggerFactory.getLogger(TwitterApiHelper.class);
    public List<TopTweets> getTweetsListsforUser(String username) {
        Map<String, String> getParams = new HashMap<>();
        getParams.put("screen_name", username);
        getParams.put("include_rts", "false");
        getParams.put("count", "100");

        HttpRequests requests = new HttpRequests();
        List<Map<String, Object>> tweetList = requests.getRequestWithBearer("1.1/statuses/user_timeline.json", getParams)
                .then().extract().jsonPath().get("");
        ;
        tweetList.sort((user1, user2) -> (int) user2.get("retweet_count") - (int) user1.get("retweet_count"));
        int topTweets = tweetList.size();
        if (topTweets > 10)
            topTweets = Integer.parseInt(twitterTestFileUtils.prop.getProperty("numberOfTweets"));


        List<TopTweets> listOfTweets = new ArrayList<>();
        for (int i = 0; i < topTweets; i++) {
            TopTweets tweetMap = new TopTweets();
            tweetMap.setTweet_id((Long) tweetList.get(i).get("id"));
            tweetMap.setTweet_date((String) tweetList.get(i).get("created_at"));
            tweetMap.setTweet_text((String)tweetList.get(i).get("text"));
            tweetMap.setFavorite_count((int)tweetList.get(i).get("favorite_count"));
            tweetMap.setRetweet_count((int)tweetList.get(i).get("retweet_count"));
            tweetMap.setTwitter_handler_name(((Map<String, Object>) tweetList.get(i).get("user"))
                    .get("screen_name").toString());
            listOfTweets.add(tweetMap);
        }
        logger.info("got tweets with ids {} for {}",listOfTweets.stream().map(TopTweets::getTweet_id)
                        .collect(Collectors.toList()),username);
        return listOfTweets;
    }

    public List<TopFriends> getFriendsListsforUser(String username) throws IOException {
        Map<String, String> getParams = new HashMap<>();

        getParams.put("cursor", "-1");
        getParams.put("include_rts", "false");
        getParams.put("skip_status", "true");
        getParams.put("screen_name", username);
        getParams.put("include_user_entities", "false");
        getParams.put("count", "100");
        HttpRequests requests = new HttpRequests();

        Response response = requests.getRequestWithBearer("1.1/friends/list.json", getParams);
        List<Map<String, Object>> friendsList = response.getBody().jsonPath().getList("users");
        friendsList = friendsList.stream().filter(friend -> ! userList.contains((String) friend.get("screen_name")))
                .collect(Collectors.toList());
        friendsList.sort((friend1, friend2) -> (int) friend2.get("followers_count") - (int) friend1.get("followers_count"));
        int topFriends = friendsList.size();
        logger.info("size of friends list is {}",topFriends);
        if (topFriends > 10)
            topFriends = Integer.parseInt(twitterTestFileUtils.prop.getProperty("numberOfFriends"));

        List<TopFriends> listOfTweets = new ArrayList<>();
        for (int i = 0; i < topFriends; i++) {
            TopFriends friendmap = new TopFriends();
            friendmap.setFriend_count((int)friendsList.get(i).get("friends_count"));
            friendmap.setFollower_count((int)friendsList.get(i).get("followers_count"));
            friendmap.setScreen_name((String) friendsList.get(i).get("screen_name"));
            listOfTweets.add(friendmap);
        }
        logger.info("got friends with ids {} for {}",listOfTweets.stream().map(TopFriends::getScreen_name)
                .collect(Collectors.toList()),username);
        return listOfTweets;
    }


}
