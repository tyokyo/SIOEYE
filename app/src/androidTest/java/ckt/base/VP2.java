package ckt.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.hamcrest.Asst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import usa.page.App;

import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * Created by admin on 2016/9/6.
 */
public class VP2 extends  VP{
    private static final int LAUNCH_TIMEOUT = 10000;
    public static Logger logger = Logger.getLogger(VP.class.getName());
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
    public static void shellInputText(String text) throws IOException {
        initDevice();
        String command = String.format("input text %s",text);
        logger.info(command);
        gDevice.executeShellCommand("input text "+text);
    }
    public static void makeToast(String message,float time) throws IOException{
        initDevice();
        String command = String.format("am broadcast -a com.sioeye.alert.action -e message %s -e time %f",message,time);
        logger.info(command);
        gDevice.executeShellCommand(command);
    }
    public static void makeToast(String message,int time) throws IOException{
        initDevice();
        String command = String.format("am broadcast -a com.sioeye.alert.action -e message %s -e time %d",message,time);
        logger.info(command);
        gDevice.executeShellCommand(command);
    }
    public static void startLog() throws IOException{
        initDevice();
        stopLog();
        String command = "am broadcast -a com.sioeye.log.action -e TAG true";
        logger.info(command);
        gDevice.executeShellCommand(command);
    }
    public static void stopLog() throws IOException{
        initDevice();
        String command = "am broadcast -a com.sioeye.log.action -e TAG false";
        logger.info(command);
        gDevice.executeShellCommand(command);
    }
    /**
     * You can call this method to find and click a UiObject.
     *
     * @param ObjectName The target object name
     */
    public static boolean clickTextContain(String ObjectName) {
        initDevice();// init UiDevice.
        try {
            if (gDevice.findObject(new UiSelector().textContains(ObjectName)).clickAndWaitForNewWindow()) {
                return true;
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return false;
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
     * This method use regular expression to find a ui element
     *
     * @param textStartsWith
     * @return  UiObject
     */
    public static UiObject getObjectByTextStartsWith(String textStartsWith) throws UiObjectNotFoundException {
        initDevice();
        return gDevice.findObject(new UiSelector().textStartsWith(textStartsWith));
    }
    /**
     * This method use regular expression to find a ui element
     *
     * @param textContains
     * @return  UiObject
     */
    public static UiObject getObjectByTextContains(String textContains) throws UiObjectNotFoundException {
        initDevice();
        return gDevice.findObject(new UiSelector().textContains(textContains));
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
    public static UiObject getUiObjectByTextContains(String TragetObject) {
        initDevice();
        return gDevice.findObject(new UiSelector().textContains(TragetObject));
    }
    /**
     * This method can return a UI Element by text.
     *
     * @param regex
     * @return UiObject
     */
    public static UiObject getUiObjectByTextMatches(String regex) {
        initDevice();
        return gDevice.findObject(new UiSelector().textMatches(regex));
    }
    /**
     * This method can return a UI Element by text.
     *
     * @param TragetObject
     * @return UiObject2
     */
    public static UiObject2 getUiObject2ByText(String TragetObject) {
        initDevice();
        return gDevice.findObject(By.text(TragetObject));
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
     * This method can return a UI Element by text.
     *
     * @param id
     * @return UiObject
     */
    public static UiObject getUiObjectById(String id) {
        initDevice();
        return gDevice.findObject(new UiSelector().resourceId(id));
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
            Asst.fail("Time Out,Not found the UI Element："+ResourceID);
        }
        return false;
    }
    /**
     * Find a UI Element by Resource ID.
     *
     * @param ResourceID
     */
    public static boolean clickById(String ResourceID,int x_offsize,int y_offsize) throws  UiObjectNotFoundException{
        initDevice();
        gDevice.wait(Until.findObject(By.res(ResourceID)), 10000);
        if (gDevice.findObject(new UiSelector().resourceId(ResourceID)).exists()) {
            UiObject obj = gDevice.findObject(new UiSelector().resourceId(ResourceID));
            int x = obj.getBounds().centerX()-x_offsize;
            int y = obj.getBounds().centerY()-y_offsize;
            gDevice.click(x,y);
            return true;
        } else {
            Asst.fail("Time Out,Not found the UI Element："+ResourceID);
        }
        return false;
    }
    /**
     * Find a UI Element by Resource ID.
     *
     * @param ResourceID
     * @param index
     */
    public static boolean clickById(String ResourceID,int index) throws UiObjectNotFoundException {
        initDevice();
        gDevice.wait(Until.findObject(By.res(ResourceID)), 10000);
        if (gDevice.findObject(new UiSelector().resourceId(ResourceID).index(index)).exists()) {
            gDevice.findObject(new UiSelector().resourceId(ResourceID).index(index)).clickAndWaitForNewWindow();
            return true;
        } else {
            Asst.fail("Time Out,Not found the UI Element："+ResourceID);
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
     * This Method  will return a UI Element by class.
     *
     * @param ClassName
     */
    public static void clickByClass(String ClassName) {
        try {
            gDevice.findObject(new UiSelector().className(ClassName)).clickAndWaitForNewWindow();
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
     * This method  wil click a point on screen.
     *
     * @param point
     */
    public static void clickByPoint(Point point) {
        initDevice();
        gDevice.click(point.x, point.y);
    }
    /**
     * 获得随机字符
     */
    public String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    /**
     * 获得随机特殊符号
     */
    public String getRandomSymbol(int length) {
        String str = "~!@#$%^&*()-+_=./\\':\"";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(21);
            sb.append(str.charAt(number));
        }
        return sb.toString();
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
        UiScrollable obj = new UiScrollable(new UiSelector()).setAsVerticalList();
        if (obj.scrollTextIntoView(TargetText)) {
            return getObjectByTextReg(TargetText);
        } else {
            Log.e("scrollAndFindObject", "Can not found text:"+TargetText);
            return null;
        }
    }
    public static UiObject scrollAndGetUIObject(String TargetText,int maxSearchStrps) throws UiObjectNotFoundException {
        UiScrollable obj = new UiScrollable(new UiSelector()).setAsVerticalList();
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

        UiScrollable obj = new UiScrollable(new UiSelector().resourceId(Performs));
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
        logger.info("openAppByPackageName-"+BASIC_PACKAGE_NAME);
        initDevice();
        //Start form the home screen.
        gDevice.pressHome();
        //Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        Asst.assertThat(launcherPackage, notNullValue());
        gDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), 5000);

        //Launch the blueprint app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(BASIC_PACKAGE_NAME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//Clear out any previous instances.
        context.startActivity(intent);
        // Wait for the app to appear
        gDevice.wait(Until.hasObject(By.pkg(BASIC_PACKAGE_NAME).depth(0)), LAUNCH_TIMEOUT);
        //cn.sioeye.sioeyeapp:id/txt_cancel
        //cn.sioeye.sioeyeapp:id/txt_ok
        UiObject update_ok=gDevice.findObject(new UiSelector().resourceId("cn.sioeye.sioeyeapp:id/txt_cancel"));
        if (update_ok.exists()){
            try {
                update_ok.clickAndWaitForNewWindow();
            }catch (UiObjectNotFoundException e){
                e.printStackTrace();
            }
        }
    }
    /**
     * You can use the method to open a new app by activity.
     * if you want test open one app, please DO NOT use this method.
     *
     * @param BASIC_PACKAGE_NAME The package Name
     */
    public static void openAppByPackageNameInLogin(String BASIC_PACKAGE_NAME){
        initDevice();
        //Start form the home screen.
        gDevice.pressHome();
        //Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        Asst.assertThat(launcherPackage, notNullValue());
        gDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), 5000);

        //Launch the blueprint app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(BASIC_PACKAGE_NAME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//Clear out any previous instances.
        context.startActivity(intent);
        // Wait for the app to appear
        gDevice.wait(Until.hasObject(By.pkg(BASIC_PACKAGE_NAME).depth(0)), LAUNCH_TIMEOUT);
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
     * Get a UI Element.
     *
     * @param ResourceID
     * @return UiObject
     */
    public static UiObject2 getObject2ById(String ResourceID) {
        initDevice();
        return gDevice.findObject(By.res(ResourceID));
    }
    /**
     * Get a UI Element.
     *
     * @param classObj
     * @return UiObject
     */
    public static UiObject2 getObject2ByClass(Class classObj) {
        initDevice();
        return gDevice.findObject(By.clazz(classObj));
    }
    /**
     * Get a UI Element.
     *
     * @param ResourceID
     * @return UiObject
     */
    public static List<UiObject2> getObjectsById(String ResourceID) {
        initDevice();
        List<UiObject2> lists =gDevice.findObjects(By.res(ResourceID));
        return  lists;
    }
    /**
     * Get a UI Element.
     *
     * @param ResourceID
     * @param index
     * @return UiObject
     */
    public static UiObject getObjectById(String ResourceID,int index) {
        initDevice();
        return gDevice.findObject(new UiSelector().resourceId(ResourceID).index(index));
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
    //add permission for READ_EXTERNAL_STORAGE WRITE_EXTERNAL_STORAGE
    public static void grantAll(){
        try {
            String READ_EXTERNAL_STORAGE = "pm grant " + App.SIOEYE_PACKAGE_NAME_USA + " android.permission.READ_EXTERNAL_STORAGE";
            gDevice.executeShellCommand(READ_EXTERNAL_STORAGE);
            String WRITE_EXTERNAL_STORAGE = "pm grant " + App.SIOEYE_PACKAGE_NAME_USA + " android.permission.WRITE_EXTERNAL_STORAGE";
            gDevice.executeShellCommand(WRITE_EXTERNAL_STORAGE);
            String CAMERA = "pm grant " + App.SIOEYE_PACKAGE_NAME_USA + " android.permission.CAMERA";
            gDevice.executeShellCommand(CAMERA);
            String RECORD_AUTO = "pm grant " + App.SIOEYE_PACKAGE_NAME_USA + " android.permission.RECORD_AUDIO";
            gDevice.executeShellCommand(RECORD_AUTO);

        } catch (Exception e) {
            Logger.getLogger("GrantAll").info("Exception while granting external storage access to application apk" + "on device ");
            e.printStackTrace();
        }
    }
    public static String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final String[] email_suffix="@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@aol.com,@ask.com,@live.com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net,@googlemail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn".split(",");

    private static int getNum(int start, int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }
    /**
     * 返回Email
     * @param lMin 最小长度
     * @param lMax 最大长度
     * @return
     */
    public static String getRandomEmail(int lMin,int lMax) {
        int length=getNum(lMin,lMax);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = (int)(Math.random()*base.length());
            sb.append(base.charAt(number));
        }
        sb.append(email_suffix[(int)(Math.random()*email_suffix.length)]);
        return sb.toString();
    }
    /**
     * 返回手机号码
     */
    private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
    public static String getRandomTel() {
        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String thrid=String.valueOf(getNum(1,9100)+10000).substring(1);
        return first+second+thrid;
    }
    /**
     * Get the Launcher Package Name.
     * @param resourceID id
     * @param timeout timeout for wait
     */
    public static void waitUntilFind(String resourceID,int timeout){
        gDevice.wait(Until.findObject(By.res(resourceID)),timeout);
    }
    /**
     * Get the Launcher Package Name.
     * @param resourceID id
     * @param timeout timeout for wait
     */
    public static void waitUntilGone(String resourceID,int timeout){
        gDevice.wait(Until.gone(By.res(resourceID)),timeout);
    }
    /**
     * Get the Launcher Package Name.
     * @param regex String
     * @param timeout timeout for wait
     */
    public static void waitUntilRegexGone(String regex,int timeout){
        gDevice.wait(Until.gone(By.text(regex)),timeout);
    }
    public static void clickRect(Rect rect){
        gDevice.click(rect.centerX(),rect.centerY());
    }
    /**
     *set text for input
     * @param ResourceID id
     * @param text timeout for wait
     */
    public static void setText(String ResourceID,String text) throws UiObjectNotFoundException {
        getObjectById(ResourceID).setText(text);
    }
    /**
     *set text for input
     * @param ResourceID id
     */
    public static String getTex(String ResourceID) throws UiObjectNotFoundException {
        return  getObjectById(ResourceID).getText();
    }
    /**
     *set text for input
     * @param ResourceID id
     */
    public static boolean id_exists(String ResourceID) throws UiObjectNotFoundException {
        return  getObjectById(ResourceID).exists();
    }
    /**
     *set text for input
     * @param text id
     */
    public static boolean text_exists(String text) throws UiObjectNotFoundException {
        return  getUiObjectByText(text).exists();
    }
    /**
     * @param regex
     */
    public static boolean text_exists_match(String regex) throws UiObjectNotFoundException {
        initDevice();
        return gDevice.findObject(new UiSelector().textMatches(regex)).exists();
    }
    /**
     * @param text_contain
     */
    public static boolean text_exists_contain(String text_contain) throws UiObjectNotFoundException {
        initDevice();
        return gDevice.findObject(new UiSelector().textContains(text_contain)).exists();
    }
    /**
     *get rectangle for object
     * @param ResourceID id
     */
    public static Rect getRect(String ResourceID) throws UiObjectNotFoundException {
        return  getObjectById(ResourceID).getBounds();
    }
    /**
     *clear text in the textField
     */
    public static void clearText(String ResourceID) throws UiObjectNotFoundException {
        getObjectById(ResourceID).clearTextField();
    }
    /**
     *get objects list
     * @param ResourceID id
     */
    public static List<UiObject2> findObjects(String ResourceID){
        return  gDevice.findObjects(By.res(ResourceID));
    }
    /**给定日期字符串
     *  * @param recordTime   00:01:20
     * @return int
     */
    public static int dateInSeconds(String recordTime) throws ParseException {
        int seconds = 0;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = format.parse(recordTime);
            cal.setTime(date);
            int hour=cal.get(Calendar.HOUR);//小时
            int minute=cal.get(Calendar.MINUTE);//分
            int second=cal.get(Calendar.SECOND);//秒
            seconds = hour*60*60+minute*60+second;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return seconds;
    }
}
