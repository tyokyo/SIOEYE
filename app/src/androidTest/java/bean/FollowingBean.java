package bean;

import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import ckt.base.VP2;

/**
 * Created by elon on 2016/10/20.
 */
public class FollowingBean extends VP2{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UiObject2 getAvatar() {
        return avatar;
    }

    public void setAvatar(UiObject2 avatar) {
        this.avatar = avatar;
    }

    private UiObject2 avatar;
    private String name;
    private String followers;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private String info;
    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    private String videos;

    public int getIndex_linearLayout() {
        return index_linearLayout;
    }

    public void setIndex_linearLayout(int index_linearLayout) {
        this.index_linearLayout = index_linearLayout;
    }

    private int index_linearLayout;
    public void click() throws UiObjectNotFoundException {
        getAvatar().click();
    }

    @Override
    public String toString() {
        return "FollowingBean{" +
                "avatar=" + avatar +
                ", name='" + name + '\'' +
                ", followers='" + followers + '\'' +
                ", info='" + info + '\'' +
                ", videos='" + videos + '\'' +
                ", index_linearLayout=" + index_linearLayout +
                '}';
    }
}
