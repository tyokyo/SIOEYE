package bean;

/**
 * Created by qiang.zhang on 2017/12/8.
 */

public class MeBean {
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getVideos() {
        return videos;
    }

    public void setVideos(int videos) {
        this.videos = videos;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    private int like;
    private int videos;
    private int following;
    private int follower;

    @Override
    public String toString() {
        return "MeBean{" +
                "nickName='" + nickName + '\'' +
                ", like=" + like +
                ", videos=" + videos +
                ", following=" + following +
                ", follower=" + follower +
                '}';
    }
}
