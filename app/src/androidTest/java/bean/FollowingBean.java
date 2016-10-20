package bean;

import android.support.test.uiautomator.UiObject2;

/**
 * Created by elon on 2016/10/20.
 */
public class FollowingBean {
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

    private String name;
    private UiObject2 avatar;

}
