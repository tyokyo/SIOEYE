package iris4G.action;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.view.KeyEvent;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

import ckt.base.VP2;

/**
 * Created by elon on 2016/11/21.
 */
public class Iris4GAction extends VP2 {
    private static String FindObject = "[Find Object]: ";
    private static String FindScrollFindObject = "[Scroll Find Object]: ";
    private static String NotFindScrollFindObject = "[Scroll Not Find Object]: ";
    private static String NotFindObject = "[Not Find Object]: ";
    private static Logger logger = Logger.getLogger(Iris4GAction.class.getName());

    /**
     * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
     *
     * @return
     */
    private static ArrayList<String> getDevMountList() throws IOException {
        String[] toSearch = FileUtils.readFileToString(new File("/etc/vold.fstab")).split(" ");
        ArrayList<String> out = new ArrayList<String>();
        for (int i = 0; i < toSearch.length; i++) {
            if (toSearch[i].contains("dev_mount")) {
                if (new File(toSearch[i + 2]).exists()) {
                    out.add(toSearch[i + 2]);
                }
            }
        }
        return out;
    }
    public static void cameraKey() {
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        logger.info("Launch-Camera-Key");
    }
    /**
     * 关闭文件管理器
     */
    public static void stopFileManager() {
        try {
            gDevice.executeShellCommand(
                    "am force-stop com.mediatek.filemanager");
            waitTime(5);
            String name = gDevice.getCurrentPackageName();
            logger.info("当前package:" + name);
            logger.info("关闭filemanager-Success");
        } catch (IOException e) {
            logger.info("关闭filemanager-Fail");
        }
    }

    /**
     * 获取图片的 height-wide
     */
    public static double getPicHeightWidth(String picPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        Bitmap bmp = BitmapFactory.decodeFile(picPath, options);
        logger.info(bmp.getHeight() + "-" + bmp.getWidth());
        double result = bmp.getWidth() / bmp.getHeight();
        return result;
    }

    /**
     * 删除Photo所有图片文件
     */
    public static void deletePhoto() {
        File f = new File("/mnt/sdcard/Photo");
        if (f.exists() && f.isDirectory()) {
            File[] fs = f.listFiles();
            for (File file : fs) {
                logger.info("delete:" + file.getAbsolutePath());
                file.delete();
            }
        } else {
            f.mkdirs();
        }
    }

    /**
     * 删除Video所有视频文件
     */
    public static void deleteVideo() {
        File f = new File("/mnt/sdcard/Video");
        if (f.exists() && f.isDirectory()) {
            File[] fs = f.listFiles();
            for (File file : fs) {
                logger.info("delete:" + file.getAbsolutePath());
                file.delete();
            }
        } else {
            f.mkdirs();
        }

    }

    public static boolean checkVideoInfo(int height, VideoNode vd) {
        boolean result = true;
        if (height == 1080) {
            if (vd.getHeight() == height) {
                result = vd.getWidth() == 1920;
            }
        }
        if (height == 720) {
            if (vd.getHeight() == height) {
                result = vd.getWidth() == 1280;
            }
        }
        if (height == 480) {
            if (vd.getHeight() == height) {
                result = vd.getWidth() == 640;
            }
        }
        return result;
    }

    /**
     * 获取视频文件信息*
     *
     * @param videoPath
     */
    public static VideoNode VideoInfo(String videoPath) {
        VideoNode vd = new VideoNode();
        logger.info(videoPath);
        MediaMetadataRetriever retr = new MediaMetadataRetriever();
        retr.setDataSource(videoPath);
        String duration = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        vd.setDuration(Integer.parseInt(duration));
        logger.info("-VIDEO_DURATION-" + Integer.parseInt(duration) / 1000 + "s");
        String height = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        vd.setHeight(Integer.parseInt(height));
        logger.info("-VIDEO_HEIGHT-" + height);
        String width = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        vd.setWidth(Integer.parseInt(width));
        logger.info("-VIDEO_WIDTH-" + width);
        return vd;
    }

    /**
     * 返回文件差集
     * tSet1-tSet2
     */
    public static HashSet<String> result(HashSet<String> tSet1, HashSet<String> tSet2) {
        HashSet<String> result = new HashSet<String>();
        result.addAll(tSet1);
        result.removeAll(tSet2);
        logger.info("旧文件夹列表:" + tSet2);
        logger.info("新文件夹列表:" + tSet1);
        logger.info("文件差集:" + result);
        return result;
    }

    /**
     * 获取指定文件夹下的所有文件列表
     */
    public static HashSet<String> FileList(String filePath) {
        logger.info(filePath);
        HashSet<String> tSet1 = new HashSet<String>();
        File f = new File(filePath);
        File[] flists = f.listFiles();
        for (File file : flists) {
            tSet1.add(file.getAbsolutePath());
        }
        logger.info(tSet1.toString());
        return tSet1;
    }

    /**
     * 关闭CAMERA
     */
    public static void stopCamera() {
        try {
            gDevice.executeShellCommand(
                    "am force-stop com.hicam");
            waitTime(5);
            String name = gDevice.getCurrentPackageName();
            logger.info("当前package:" + name);
            logger.info("关闭Camera-Success");
        } catch (IOException e) {
            logger.info("关闭Camera-Fail");
        }
    }

    /**
     * clear data
     * pm clear com.hicam
     */
    public static void pmClear() {
        try {
            gDevice.executeShellCommand("pm clear com.hicam");
            logger.info("pm clear com.hicam-Success");
        } catch (IOException e) {
            logger.info("pm clear com.hicam-Fail");
        }
    }

    /**
     * 启动CAMERA
     */
    public static void startCamera() throws Exception {
        gDevice.pressHome();
        gDevice.pressHome();
        waitTime(2);
        gDevice.executeShellCommand("am start -n com.hicam/.application.HiCam");
        gDevice.wait(Until.findObject(By.pkg("com.hicam")),40000);
        String pkg = gDevice.getCurrentPackageName();
        logger.info("current-package:"+pkg);
    }

    /**
     * 启动Settings
     */
    public static void startSettings() throws Exception {
        gDevice.pressHome();
        gDevice.pressHome();
        waitTime(2);
        gDevice.executeShellCommand("am start -n com.android.settings/.CustomSettings");
        gDevice.wait(Until.findObject(By.pkg("com.android.settings")),40000);
        String pkg = gDevice.getCurrentPackageName();
        logger.info("current-package:"+pkg);
    }

    /**
     * 启动FILE MANAGER
     */
    public static void startFileManager() throws Exception {
        gDevice.pressHome();
        gDevice.pressHome();
        waitTime(2);
        gDevice.executeShellCommand("am start -n com.mediatek.filemanager/.FileManagerOperationActivity");
        gDevice.wait(Until.findObject(By.pkg("com.mediatek.filemanager")),40000);
        String pkg = gDevice.getCurrentPackageName();
        logger.info("current-package:"+pkg);
    }

    /**
     * 关闭相册
     */
    public static void stopGallery() {
        try {
            gDevice.executeShellCommand("am force-stop com.hicam.gallery");
            waitTime(5);
            String name = gDevice.getCurrentPackageName();
            logger.info("当前package:" + name);
            logger.info("关闭Gallery-Success");
        } catch (IOException e) {
            logger.info("关闭Gallery-Fail");
        }
    }

    /**
     * 启动相册
     */
    public void startGallery() throws Exception {
        gDevice.pressHome();
        gDevice.pressHome();
        waitTime(2);
        gDevice.executeShellCommand("am start -n com.hicam.gallery/.ui.GalleryPage");
        gDevice.wait(Until.findObject(By.pkg("com.mediatek.filemanager")),40000);
        String pkg = gDevice.getCurrentPackageName();
        logger.info("current-package:"+pkg);
    }

    /**
     * 安装手机存储内的APP
     *
     * @param path path=/sdcard/***.apk
     */
    public void install(String path) {
        try {
            gDevice.executeShellCommand("pm install  com.hicam");
            logger.info("pm clear com.sioeye.sioeyeapp - Success");
        } catch (IOException e) {
            logger.info("pm clear com.sioeye.sioeyeapp - Failed");
            e.printStackTrace();
        }
    }

    /**
     * 清除数据
     *
     * @param ks ks = false 删除全部数据
     *           ks = true 卸载应用且保留数据与缓存
     */
    public void uninstall(boolean ks) {
        try {
            if (ks) {
                gDevice.executeShellCommand("pm uninstall -k com.hicam");
                logger.info("pm uninstall -k com.sioeye.sioeyeapp");
            } else {
                gDevice.executeShellCommand("pm uninstall  com.sioeye.sioeyeapp");
            }
        } catch (IOException e) {
            logger.info("pm clear com.sioeye.sioeyeapp - Failed");
            e.printStackTrace();
        }
    }

    /**
     * 在手机路径/sdcard/CktTest/创建对应用例的文件夹，用于存放失败截图，性能测试的LOG等
     */
    public void createFolder() throws IOException {
        waitTime(1);
        gDevice.executeShellCommand("mkdir -p /sdcard/CktTest/screen/");
        waitTime(1);
        Runtime.getRuntime().exec("rm -r /sdcard/CktTest/Performance/");
        waitTime(1);
        gDevice.executeShellCommand("mkdir -p /sdcard/CktTest/Performance/");
    }

    /**
     * 抓取并保存bugreport
     */
    public String takeBugReport(String crashType, String currentTime)
            throws IOException {
        logger.info("开始抓取崩溃日志");
        try {
            // Executes the command.
            String logname = "/sdcard/CktTest/" + "screen" + "/Crash_"
                    + crashType + "_" + currentTime + ".txt";
            File file = new File(logname);
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file, true);
            Process process = Runtime.getRuntime()
                    .exec("/system/bin/bugreport");
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            int read;
            char[] buffer = new char[4096];
            while ((read = reader.read(buffer)) > 0) {
                StringBuffer output = new StringBuffer();
                output.append(buffer, 0, read);
                out.write(output.toString().getBytes("utf-8"));
            }
            // Waits for the command to finish.
            process.waitFor();
            reader.close();
            out.close();
            logger.info("崩溃日志文件抓取成功-" + logname);
            return logname;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 配置timeout时间
     *
     * @param time Integer
     */
    public void configTimeout(int time) {
        Configurator confg = Configurator.getInstance();
        long timeout = confg.getWaitForSelectorTimeout();
        //获取Selector timeout
        confg.setWaitForSelectorTimeout(timeout + time);
    }

    public static void initIris4G() throws Exception {
        try {
            initDevice();
            initDevice();
            deleteVideo();
            deletePhoto();
            startCamera();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static UiObject scrollTextIntoView(String text) throws Exception {
        UiScrollable listScrollable = new UiScrollable(new UiSelector().scrollable(true));
        listScrollable.setMaxSearchSwipes(10);
        try {
            if (listScrollable.scrollTextIntoView(text)) {
                logger.info(FindScrollFindObject + text);
            }
        } catch (UiObjectNotFoundException e) {
            // TODO Auto-generated catch block
            logger.info(NotFindScrollFindObject + text);
            throw new Exception("ScrollFindObject" + text);
        }
        return getUiObjectByText(text);
    }
    /**
     * 强查找可翻滚控件，存在返回控件对象，不存在抛异常，当前测试停止
     *
     * @throws Exception
     */
    public static void ScrollViewByText(String text) throws Exception {
        UiScrollable listScrollable = new UiScrollable(new UiSelector().scrollable(true));
        listScrollable.setMaxSearchSwipes(10);
        try {
            if (listScrollable.scrollTextIntoView(text)) {
                logger.info(FindScrollFindObject + text);
            }
        } catch (UiObjectNotFoundException e) {
            // TODO Auto-generated catch block
            logger.info(NotFindScrollFindObject + text);
            throw new Exception("ScrollFindObject" + text);
        }
    }

    /**
     * 强查找可翻滚控件，存在返回控件对象，不存在抛异常，当前测试停止
     *
     * @throws Exception
     */
    public static boolean ScrollViewByTextNotFind(String text) throws Exception {
        boolean isFind = true;
        UiScrollable listScrollable = new UiScrollable(new UiSelector().scrollable(true));
        listScrollable.setMaxSearchSwipes(80);
        isFind = listScrollable.scrollTextIntoView(text);
        if (isFind) {
            logger.info(FindScrollFindObject + text);
        } else {
            logger.info(NotFindScrollFindObject + text);
        }
        return isFind;
    }
}
