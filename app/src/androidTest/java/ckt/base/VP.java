package ckt.base;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import com.sioeye.MainActivity;
import com.squareup.spoon.Spoon;

import org.junit.Rule;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import ckt.tools.Common;
//import ckt.tools.Watcher;

/**
 * Created by admin on 2016/9/6.
 */
public class VP {
    private static Logger logger = Logger.getLogger(VP.class.getName());
    public static UiDevice gDevice = null;
    public static Instrumentation instrument = null;
    public static Context context = null;
    public static boolean isStop = false;
    public static String logAbsPath;
    public static String logName;
    private static Thread logThread;
    //permission allow dialog
    public static String PERMISSION_ALLOW = "com.android.packageinstaller:id/permission_allow_button";
    //pop up dialog
    public static String ID_MESSAGE = "android:id/message";

    /**
     * You can get a UiDevice Instance if you call this method.
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    public static void doAskPermission(boolean isAsk) throws UiObjectNotFoundException {
        if (isAsk){
            for (int t = 1; t < 3; t++) {
                if ("com.android.packageinstaller".equals(gDevice.getCurrentPackageName())) {
                    //权限请求确认-pkg com.google.android.packageinstaller
                    UiObject uiObject_permission = gDevice.findObject(new UiSelector().resourceId(PERMISSION_ALLOW));
                    if (uiObject_permission.exists()) {
                        logger.info("["+t+"]"+"click allow-permission setting");
                        uiObject_permission.clickAndWaitForNewWindow();
                    }
                /*//异常弹出框-click-OK
                UiObject uiObject_popup = gDevice.findObject(new UiSelector().resourceId(ID_MESSAGE));
                if (uiObject_popup.exists()){
                    logger.info("doNotAskPermission-click android:id/button1");
                    gDevice.findObject(new UiSelector().resourceId("android:id/button1")).clickAndWaitForNewWindow();
                }*/
                }
           /* if ("com.sioeye".equals(gDevice.getCurrentPackageName())) {
                logger.info("["+t+"]"+"com.sioeye launch success");
                break;
            }*/
            }
        }
    }
    public static void initDevice() {
        if (instrument == null) {
            instrument = InstrumentationRegistry.getInstrumentation();
            context = instrument.getContext();
        }
        if (gDevice == null) {
            gDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        }
        //registerWatcher();
        try {
            doAskPermission(false);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void logStart() throws InterruptedException {
        Date date = new Date();
        DateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
        logName = "log_" + format2.format(date);
        System.out.println(logName);
        isStop = false;

        String logDir = Environment.getExternalStorageDirectory() + File.separator + "log";
        logAbsPath = logDir + File.separator + logName + ".txt";
        if (!new File(logDir).exists()) {
            new File(logDir).mkdirs();
        }
        if (logThread != null) {
            if (!logThread.isInterrupted()) {
                logThread.interrupt();
            }
        }
        logThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Process logcatProcess = null;
                BufferedReader bufferedReader = null;
                try {
                    logcatProcess = Runtime.getRuntime().exec("logcat -v time *.E");
                    bufferedReader = new BufferedReader(new InputStreamReader(logcatProcess.getInputStream()));
                    String line;
                    while (true) {
                        if (isStop == true) {
                            logcatProcess.destroy();
                            break;
                        }
                        if ((line = bufferedReader.readLine()) != null) {
                            Common.writeToFile(logAbsPath, line, true);
                        }
                    }
                   /* while ((line = bufferedReader.readLine()) != null) {
                        Common.writeToFile(logAbsPath, line, true);
                        if (isStop==true) {
                            logcatProcess.destroy();
                        }
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        logThread.start();
        Logger.getLogger("start logcat").info(logName);
    }

    public static void logStop() {
        isStop = false;
        Logger.getLogger("stop logcat").info(logName);
    }

    public static void saveLogToReport() {
        Spoon.save(instrument.getContext(), logAbsPath);
        Logger.getLogger("LOGGER").info("spoon save log file " + logAbsPath);
    }
    // boolean Result = ImageLib.sameAs("/mnt/sdcard/other_word.jpg", "/mnt/sdcard/origin.jpg");
}
