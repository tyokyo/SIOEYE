package iris4G.action;


import org.junit.Assert;

import iris4G.page.Iris4GPage;
import iris4G.page.NavPage;

import static ckt.base.VP2.clickById;
import static ckt.base.VP2.text_exists;
import static ckt.base.VP2.waitTime;

public class VideoNode {
    private int duration;
    private int height;
    private int width;

    public static void navToVideoAndLiveLoginPage() throws Exception {
        CameraAction.navConfig(NavPage.navConfig_Video);
        clickById(Iris4GPage.camera_setting_shortcut_id);
        waitTime(1);
        CameraAction.openCompoundButton(Iris4GPage.videoAndLive);
        waitTime(3);
        if (!text_exists("Scan the QRcode generated in sioeye app.")){
            Assert.fail("notOpenLoginPage");
        }
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "VideoNode{" +
                "duration=" + duration +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
