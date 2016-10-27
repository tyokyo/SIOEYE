package bean;

/**
 * Created by elon on 2016/10/27.
 */
public class UserBean {
    public String getZan() {
        return zan;
    }

    public void setZan(String zan) {
        this.zan = zan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBroadcasts() {
        return broadcasts;
    }

    public void setBroadcasts(String broadcasts) {
        this.broadcasts = broadcasts;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }
    private String name;
    private String id;
    private String broadcasts;
    private String following;
    private String followers;
    private String zan;

    @Override
    public String toString() {
        return "UserBean{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", broadcasts='" + broadcasts + '\'' +
                ", following='" + following + '\'' +
                ", followers='" + followers + '\'' +
                ", zan='" + zan + '\'' +
                '}';
    }
}
