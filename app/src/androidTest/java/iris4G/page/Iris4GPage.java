package iris4G.page;

/**
 * Created by elon on 2016/11/21.
 */
public class Iris4GPage {
    public static String logout_btn = "com.hicam:id/logout_btn";
    public static String login_id_input = "com.hicam:id/login_id_input";
    public static String login_password_input = "com.hicam:id/login_password_input";
    public static String login_btn_login = "com.hicam:id/login_btn_login";

    public static String launcher_application_id = "com.android.launcher3:id/application_icon_icon";
    public static String camera_live_shortcut_id= "com.hicam:id/camera_live_shortcut";
    public static String camera_cap_shortcut_id = "com.hicam:id/camera_cap_shortcut";
    public static String camera_video_shortcut_id = "com.hicam:id/camera_video_shortcut";
    public static String camera_setting_shortcut_id = "com.hicam:id/camera_setting_shortcut";

    public static String recording_time_id = "com.hicam:id/recording_time";

    public static String currentvideoquliaty = "com.hicam:id/info";
    public static String currentcameraquality = "com.hicam:id/camera_mode_label";

    public static String playvideobtn = "com.hicam.gallery:id/gallery_bottom_play_im";
    public static String lapsetime1 = "com.hicam:id/recording_time";
    public static String lapsetime2 = "com.hicam:id/recording_time2";

    //Can't play this video.
    //android:id/message
    //android:id/button1
    public static String[] burst ={
            "10P",
            "20P",
            "30P"};
    //delete 480
    public static String[] video_quality ={
            //"480@30FPS",
            //"480@60FPS",
            //"480@120FPS",
            "720@30FPS",
            "720@60FPS",
            "1080@30FPS"};
    public static String[] imsge_size ={
            "4M(16:9)",
            "3M(4:3)",
            "2M(16:9)"};
    public static String[] nav_menu ={
            "Live Stream",
            "Video",
            "Capture",
            "Burst",
            "Slo_Mo",
            "Lapse"
    };
    public static String[] video_Angle ={"Super Wide","Wide","Medium"};

    public static String[] lapse_time ={"2s","3s","5s","10s"};
    //com.hicam:id/camera_mode_label
    //00:00:09

    //camera gallery
    public static String apps_customize_pane_content = "com.android.launcher3:id/apps_customize_pane_content";

    //file manager scroll to find video
    public static String fileManager_list_view = "com.mediatek.filemanager:id/list_view";

}
