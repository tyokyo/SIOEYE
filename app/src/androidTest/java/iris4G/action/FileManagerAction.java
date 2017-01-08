package iris4G.action;

import com.squareup.spoon.Spoon;

import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.page.Iris4GPage;

public class FileManagerAction extends VP2 {
    private static Logger logger = Logger.getLogger(FileManagerAction.class.getName());
    /**
     * Click launcher_application_id按钮
     */
    public static void playVideoByFileManager(String videoName) throws Exception {
        Iris4GAction.startFileManager();
        clickByText("Internal storage");
        Iris4GAction.ScrollViewByText(Iris4GPage.fileManager_list_view,"Video");
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
