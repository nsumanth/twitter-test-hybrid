package dto;

public class TopFriends {

    String screen_name;
    int friend_count;
    int follower_count;

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public int getFriend_count() {
        return friend_count;
    }

    public void setFriend_count(int friend_count) {
        this.friend_count = friend_count;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(int follower_count) {
        this.follower_count = follower_count;
    }

}
