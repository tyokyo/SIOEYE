package ckt;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Logger;

import page.App;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by admin on 2016/9/6.
 */
public class VP2 extends  VP{
    private static String TAG = VP2.class.getName();
    /**
     * 获取视频文件信息
     * @param videoPath
     *
     */
    public VideoNode VideoInfo(String videoPath){
        VideoNode vd = new VideoNode();
        Logger.getLogger(TAG).info(videoPath);
        MediaMetadataRetriever retr = new MediaMetadataRetriever();
        retr.setDataSource(videoPath);
        String duration = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        vd.setDuration(Integer.parseInt(duration));
        Logger.getLogger(TAG).info("-VIDEO_DURATION-"+Integer.parseInt(duration)/1000+"s");
        String height = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        vd.setHeight(Integer.parseInt(height));
        Logger.getLogger(TAG).info("-VIDEO_HEIGHT-"+height);
        String width = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        vd.setWidth(Integer.parseInt(width));
        Logger.getLogger(TAG).info("-VIDEO_WIDTH-"+width);
        return vd;
    }
    /**
     * 返回文件差集
     * tSet1-tSet2
     * @param tSet1 tSet2
     *
     */
    public HashSet<String> result(HashSet<String> tSet1, HashSet<String> tSet2){
        HashSet<String> result = new HashSet<String>();
        result.addAll(tSet1);
        result.removeAll(tSet2);
        Logger.getLogger(TAG).info("Old Folder List:"+tSet2);
        Logger.getLogger(TAG).info("New FolderList:"+tSet1);
        Logger.getLogger(TAG).info("The File added:"+result);
        return result;
    }
    /**
     * 等待时间设置
     *
     * @param n
     *            等待时间，单位为秒
     */
    public static void waitTime(int n) {
        long time = n * 1000;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 配置timeout时间
     * @param time Integer
     */
    public void configTimeout(int time){
        Configurator confg = Configurator.getInstance();
        long timeout = confg.getWaitForSelectorTimeout();
        //获取Selector timeout
        confg.setWaitForSelectorTimeout(timeout+time);
    }

    public static void makeToast(String message,int time) throws IOException{
        String command = String.format("am broadcast -a com.sioeye.alert.action -e %s -e %d",message,time);
        Logger.getLogger(TAG).info(command);
        gDevice.executeShellCommand(command);
    }
    public static void playVideo(String path) throws IOException, InterruptedException {
        //Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/Video/VID_20160909030920_30fps.mp4");
        File videoFile = new File(path);
        if (videoFile.exists()){
            Uri uri = Uri.parse(path);
            //调用系统自带的播放器
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Logger.getLogger(TAG).info("play video-"+path);
            intent.setDataAndType(uri, "video/mp4");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            for (int i=0;i<30;i++){
                String currentPackageName= gDevice.getCurrentPackageName();
                if (App.SIOEYE_PACKAGE_NAME.equals(currentPackageName)){
                    Logger.getLogger(TAG).info("Gallery Launch Success");
                    break;
                }else{
                    Thread.sleep(2000);
                }
            }
        }
        else{
            Logger.getLogger(TAG).info(path + " not exists");
        }

    }
    /**
     * You can call this method to find and click a UiObject.
     *
     * @param ObjectName The target object name
     */
    public static boolean clickByText(String ObjectName) {
        initDevice();// init UiDevice.
        try {
            if (gDevice.findObject(new UiSelector().text(ObjectName)).clickAndWaitForNewWindow()) {
                return true;
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method will find a UiObject by description and click it.
     *
     * @param ObjectDescription
     */
    public static void clickByDescription(String ObjectDescription) {
        initDevice();// init UiDevice.
        try {
            gDevice.findObject(
                    new UiSelector().description(ObjectDescription)).clickAndWaitForNewWindow();

        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method using regular expression to find a UI Object and click.
     *
     * @param ObjectDescriptionReg
     * @return if succeed return true,else return false.
     */

    public static boolean clickByDescReg(String ObjectDescriptionReg) {
        initDevice();// init UiDevice.
        try {
            if (gDevice.findObject(
                    new UiSelector().descriptionMatches(ObjectDescriptionReg))
                    .clickAndWaitForNewWindow()) {
                return true;
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method use description with keyword to find a ui element and click.
     *
     * @param KeyWord
     */
    public static void clickByDescKeyword(String KeyWord) {
        initDevice();// init UiDevice.
        try {
            gDevice.findObject(
                    new UiSelector().descriptionContains(KeyWord)).clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method use regular expression to find a ui element and click(Text)
     *
     * @param ObjectTextReg
     * @return  true of false.
     */

    public static boolean clickByTextReg(String ObjectTextReg) {
        initDevice();
        try {
            if (getObjectByTextReg(ObjectTextReg).click()) {
                return true;
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * This method use regular expression to find a ui element
     *
     * @param ObjectByTextReg
     * @return  UiObject
     */
    public static UiObject getObjectByTextReg(String ObjectByTextReg) throws UiObjectNotFoundException {
        initDevice();
        return gDevice.findObject(new UiSelector().textMatches(ObjectByTextReg));
    }
    /**
     * Find a TextEdit by class name,  and set text.
     *
     * @param ObjectClass
     * @param TragetText
     */
    public static void setTextByClass(String ObjectClass, String TragetText) {
        initDevice();
        try {
            gDevice.findObject(new UiSelector().className(ObjectClass)).setText(TragetText);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check a Ui element if exist.in duration time
     *
     * @param resId
     * @param time
     * @return true of false.
     */
    public static boolean waitHasObject(String resId, int time) {
        initDevice();
        return gDevice.wait(Until.hasObject(By.res(resId)), time);
    }


    /**
     * Find a UI Element by Class index.
     *
     * @param fatherClass
     * @param childClass
     * @param fatherIndex
     * @param childIndex
     */
    public static void clickByClass(String fatherClass, String childClass, int fatherIndex, int childIndex) {
        initDevice();
        try {
            gDevice.findObject(new UiSelector()
                    .childSelector(new UiSelector().className(childClass).index(childIndex))
                    .className(fatherClass).index(fatherIndex)).clickAndWaitForNewWindow();

        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }


    }

    /**
     * This method is use for Scroll.
     *
     * @param steps
     */
    public static void scrollByHorizontal(int steps) {
        initDevice();
        try {
            UiScrollable scr = new UiScrollable(new UiSelector().scrollable(true));
            scr.setAsHorizontalList();
            scr.scrollForward(steps);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }


    }


    /**
     * This method can return a UI Element by text.
     *
     * @param TragetObject
     * @return UiObject
     */
    public static UiObject getUiObjectByText(String TragetObject) {
        initDevice();
        return gDevice.findObject(new UiSelector().text(TragetObject));
    }


    /**
     * 精确查找
     * @param ObjectDescription 文本描述
     * @throws UiObjectNotFoundException
     * @description 通过文本描述来查找并返回对象
     */
    public static UiObject getUiObjectByDes(String ObjectDescription) {
        initDevice();
        UiObject gbottc = gDevice.findObject(
                new UiSelector().description(ObjectDescription));
        return gbottc;
    }


    /**
     * Find a UI Element by Resource ID.
     *
     * @param ResourceID
     */
    public static boolean clickById(String ResourceID) {
        initDevice();
        gDevice.wait(Until.findObject(By.res(ResourceID)), 10000);
        if (gDevice.findObject(new UiSelector().resourceId(ResourceID)).exists()) {
            gDevice.wait(Until.findObject(By.res(ResourceID)), 500).click();
            return true;
        } else {
            fail("Time Out,Not found the UI Element："+ResourceID);
        }
        return false;
    }


    /**
     * This method can be use for operation hardware key.
     * eg:Menu/Back/Home
     *
     * @param keyName
     * @throws UiObjectNotFoundException
     */

    public static void pressKey(String keyName)
            throws UiObjectNotFoundException {
        initDevice();
        // Use split function to identify operation.
        String[] keyOperation = keyName.split("/");
        int j = 0;
        int i = keyOperation.length;
        for (; ; ) {
            if (j < i) {
                String opera = keyOperation[j];
                if ("MENU".equals(opera.toUpperCase())) {
                    gDevice.pressMenu();
                }
                if ("HOME".equals(opera.toUpperCase())) {
                    gDevice.pressHome();
                }
                if ("BACK".equals(opera.toUpperCase())) {
                    gDevice.pressBack();
                }
                if ("DELETE".equals(opera.toUpperCase())) {
                    gDevice.pressDelete();
                }
                if ("WAKEUP".equals(opera.toUpperCase())) {
                    try {
                        gDevice.wakeUp();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                if ("SLEEP".equals(opera.toUpperCase())) {
                    try {
                        gDevice.sleep();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    if ("RECENT".equals(opera.toUpperCase())) {
                        try {
                            gDevice.pressRecentApps();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                break;
            }
            j++;
        }
    }
    /**
     * This Method  will return a UI Element by class.
     *
     * @param ClassName
     * @param index
     */
    public static void clickByClass(String ClassName, int index) {
        try {
            gDevice.findObject(new UiSelector().className(ClassName)
                    .index(index)).clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method  wil click a point on screen.
     *
     * @param x
     * @param y
     */
    public static void clickByPonit(int x, int y) {
        initDevice();
        gDevice.click(x, y);
    }



    /**
     * Scroll to Found a UI Element
     * The default orientation is Horizontal
     *
     * @param TargetText The target name.
     * @return UiObject, if found any match UI Element.
     * @throws UiObjectNotFoundException
     */
    public static UiObject scrollAndGetUIObject(String TargetText) throws UiObjectNotFoundException {
        UiScrollable obj = new UiScrollable(new UiSelector().scrollable(true));
        obj.setAsHorizontalList();
        if (obj.scrollTextIntoView(TargetText)) {
            return getObjectByTextReg(TargetText);
        } else {
            Log.e("scrollAndFindObject", "Can not found any match Element!");
            return null;
        }
    }
    public static UiObject scrollAndGetUIObject(String TargetText,int maxSearchStrps) throws UiObjectNotFoundException {
        UiScrollable obj = new UiScrollable(new UiSelector().scrollable(true));
        obj.setAsHorizontalList();
        obj.setMaxSearchSwipes(maxSearchStrps);
        if (obj.scrollTextIntoView(TargetText)) {
            return getObjectByTextReg(TargetText);
        } else {
            Log.e("scrollAndFindObject", "Can not found any match Element!");
            return null;
        }
    }
    /**
     * @param TargetName The target object name.
     * @param Performs   The scrollable layout element.
     * @return return the match element.
     * @throws UiObjectNotFoundException
     */
    public static UiObject scrollAndGetUIObject(String TargetName, String Performs) throws UiObjectNotFoundException {
        initDevice();
        // Back to home

        UiScrollable obj = new UiScrollable(
                new UiSelector().resourceId(Performs));
        obj.setAsVerticalList();

        if (obj.scrollTextIntoView(TargetName)) {
            return getObjectByTextReg(TargetName);
        } else {
            Log.e("scrollAndFindObject", "Can not found any match Element!");
            return null;
        }
    }


    /**
     * Get the prop info form the command
     *
     * @param key
     * @return
     */
    public static String getProp(String key) {
        try {
            Process p = Runtime.getRuntime().exec("getprop");
            InputStream in = p.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader buff = new BufferedReader(reader);
            String line = "";
            while ((line = buff.readLine()) != null) {
                if (line.contains("[" + key + "]")) {
                    String[] s = line.split("\\[");
                    return s[2].replaceAll("\\].*", "");
                }
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        return "Not Found";
    }

    /**
     * Turn off screen and wait for few mantes and then turn on screen.
     *
     * @param sleeptime
     */
    public static void coolDown(int sleeptime) {
        initDevice();
        try {
            pressKey("Sleep");
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        try {
            int i = sleeptime * 60000;
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            pressKey("WakeUp");
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * You can use the method to open a new app by activity.
     * if you want test open one app, please DO NOT use this method.
     *
     * @param BASIC_PACKAGE_NAME The package Name
     */
    public static void openAppByPackageName(String BASIC_PACKAGE_NAME)

    {
        initDevice();
        //Initialize UiDevice instance.
        gDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        //Start form the home screen.
        gDevice.pressHome();
        //Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        gDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), 5000);

        //Launch the blueprint app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(BASIC_PACKAGE_NAME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//Clear out any previous instances.
        context.startActivity(intent);

    }
    public static void killAppByPackage(String packageName) {
        initDevice();
        try {
            gDevice.executeShellCommand("am force-stop  " + packageName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method will using activity name to open a app.
     * if you want test open one app, please DO NOT use this method.
     *
     * @param activityName
     */

    public static void openAppByActivity(String activityName) {
        initDevice();
        try {
            gDevice.executeShellCommand("am start -n " + activityName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return true of false,If the target app is open return true,else return false.
     * If you want to test open one app,please use this method.else you can use openAppByActivity.
     */
    public static boolean openAppByText(String TargetText) {
        try {
            UiObject Target = scrollAndGetUIObject(TargetText);
            if (Target.exists()) {
                if (Target.click()) {
                    return true;
                }
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get a UI Element.
     *
     * @param ResourceID
     * @return UiObject
     */
    public static UiObject getObjectById(String ResourceID) {
        initDevice();
        return gDevice.findObject(new UiSelector().resourceId(ResourceID));
    }

    /**
     * Get the Launcher Package Name.
     *
     * @return
     */
    private static String getLauncherPackageName() {
        //Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name.
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
    /**
     * Check the screen status.
     */
    public static void checkScreenStatus() {
        initDevice();
        try {
            if (!gDevice.isScreenOn()) {
                gDevice.wakeUp();
            }
            Log.i("CheckScreenStatus", "The screen is screen is on");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
