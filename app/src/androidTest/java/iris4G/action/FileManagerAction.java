package iris4G.action;

import com.squareup.spoon.Spoon;

import ckt.base.VP2;

public class FileManagerAction extends VP2 {
    /**
     * Click launcher_application_id按钮
     */
    public static void playVideoByFileManager(String videoName) throws Exception {
        Iris4GAction.startFileManager();
        clickByText("Internal storage");
        scrollAndGetUIObject("Video");
        clickByText("Video");

        Spoon.screenshot("video",videoName);
        //common.ScrollViewByText(videoName);
        clickByText(videoName);
        CameraAction.playVideoBtn().clickAndWaitForNewWindow();
        waitTime(3);
        Spoon.screenshot("play_video",videoName);
        //Can't play this video.
        //android:id/message
        //android:id/button1
    }
}
