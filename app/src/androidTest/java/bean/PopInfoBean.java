package bean;

/**
 * Created by qiang.zhang on 2017/12/8.
 */

public class PopInfoBean {
    private String nickName;
    private int like;

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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getVideo() {
        return video;
    }

    public void setVideo(int video) {
        this.video = video;
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

    private String signature;
    private int video;
    private int following;
    private int follower;

    @Override
    public String toString() {
        return "PopInfoBean{" +
                "nickName='" + nickName + '\'' +
                ", like=" + like +
                ", signature='" + signature + '\'' +
                ", video=" + video +
                ", following=" + following +
                ", follower=" + follower +
                '}';
    }
}
