package dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataDrivers {
    public static Map<String , List<TopTweets>> topTweetsMap = new HashMap<>();
    public static Map<String , List<TopFriends>> topFriendsMap = new HashMap<>();
    public static Map<String,Object> friendsTestMap = new HashMap<>();
    public static List<String> userList = new ArrayList<>();
    public static final String TOP_TWEETS = "_TOP_TWEETS";
    public static final String TOP_FRIENDS = "_TOP_FRIENDS";
}
