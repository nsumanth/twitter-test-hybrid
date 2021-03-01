package dto;

import java.util.Map;

public class TopTweets {
    Long tweet_id;
    String tweet_date;
    String tweet_text;
    String twitter_handler_name;
    int favorite_count;
    int retweet_count;
    int retweetDifference;
    int favoritedifference;

    public int getRetweetDifference() {
        return retweetDifference;
    }

    public void setRetweetDifference(int retweetDifference) {
        this.retweetDifference = retweetDifference;
    }

    public int getFavoritedifference() {
        return favoritedifference;
    }

    public void setFavoritedifference(int favoritedifference) {
        this.favoritedifference = favoritedifference;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public int getRetweet_count() {
        return retweet_count;
    }

    public void setRetweet_count(int retweet_count) {
        this.retweet_count = retweet_count;
    }

    public Long getTweet_id() {
        return tweet_id;
    }

    public void setTweet_id(Long tweet_id) {
        this.tweet_id = tweet_id;
    }

    public String getTweet_date() {
        return tweet_date;
    }

    public void setTweet_date(String tweet_date) {
        this.tweet_date = tweet_date;
    }

    public String getTweet_text() {
        return tweet_text;
    }

    public void setTweet_text(String tweet_text) {
        this.tweet_text = tweet_text;
    }

    public String getTwitter_handler_name() {
        return twitter_handler_name;
    }

    public void setTwitter_handler_name(String twitter_handler_name) {
        this.twitter_handler_name = twitter_handler_name;
    }



}
