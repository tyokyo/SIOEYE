package iris4G.action;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import java.io.IOException;

import ckt.base.VP2;
import iris4G.page.SettingPage;

/**
 * Created by yyun on 2017/6/11.
 */

public class SettingAction extends VP2 {
    public static void navToStorage() throws Exception {
        waitTime(2);
        clickByText("Device");
        Iris4GAction.ScrollViewByText("Storage");
        clickByText("Storage");
    }
    public static float getUesd() throws UiObjectNotFoundException {
        String originalUsed=getTex(SettingPage.storage_used_tx);
        String originalUsedValue=originalUsed.substring(0,originalUsed.length()-1);
        float valueOriginalUsed=Float.valueOf(originalUsedValue).floatValue();
        logger.info("Used:"+valueOriginalUsed+" G");
        return valueOriginalUsed;
    }
    public static float getFree() throws UiObjectNotFoundException {
        String originalFree=getTex(SettingPage.storage_avial_tx);
        String originalFreeValue =originalFree.substring(0,originalFree.length()-1);
        float valuerOriginalFree=Float.valueOf(originalFreeValue).floatValue();
        logger.info("Free:"+valuerOriginalFree+" G");
        return valuerOriginalFree;
    }
    public static float floatAbs(float valueUpUsed,float valueOriginalUsed,float valueUpFree,float valuerOriginalFree){
        float result=valueUpUsed-valueOriginalUsed+valueUpFree-valuerOriginalFree;
        logger.info(valueUpUsed+"-"+valueOriginalUsed+"+"+valueUpFree+"-"+valuerOriginalFree+"="+result);
        float resultAbS=Math.abs(result);
        logger.info("resultAbS："+resultAbS);
        return resultAbS;
    }
    public static void navToPreferredNetworkType() throws Exception {
        Iris4GAction.startSettings();
        clickByText("Connection");
        Iris4GAction.ScrollViewByText("SimCard Info");
        clickByText("SimCard Info");
        clickByText("Preferred network type");
    }
    public static void navToSleepTime() throws Exception {
        Iris4GAction.startSettings();
        clickByText("Device");
        Iris4GAction.ScrollViewByText("Display");
        clickByText("Display");
        Iris4GAction.ScrollViewByText("Sleep");
        clickByText("Sleep");
    }
    public static void navToWifi() throws Exception {
        Iris4GAction.startSettings();
        clickByText("Connection");
        Iris4GAction.ScrollViewByText("Wi-Fi");
        clickByText("Wi-Fi");
    }
    public static void cancelFormat(){
        clickById(SettingPage.storage_delete_img);
        clickById(SettingPage.storage_delete_cancel_text);
        waitTime(3);
    }
    public static void format(){
        clickById(SettingPage.storage_delete_img);
        clickById(SettingPage.storage_delete_ok_text);
        clickById(SettingPage.storage_delete_ok_text);
        waitTime(8);
        gDevice.pressBack();
    }
    public static void closeWifi() throws Exception {
        Iris4GAction.startSettings();
        clickByText("Connection");
        clickByText("Wi-Fi");
        waitTime(2);
        UiObject addNewNetWork = getObjectByTextContains("Add new network...");
        if (!addNewNetWork.exists()) {
            clickById(SettingPage.wifi_switch_Widget);
            waitTime(1);
        }
        Iris4GAction.stopSettings();
    }
    public static void openWifi() throws Exception {
        Iris4GAction.startSettings();
        clickByText("Connection");
        clickByText("Wi-Fi");
        waitTime(2);
        UiObject addNewNetWork = getObjectByTextContains("Add new network...");
        if (addNewNetWork.exists()) {
            clickById(SettingPage.wifi_switch_Widget);
            waitTime(5);
        }
        Iris4GAction.stopSettings();
    }
    public static void disableSimData() throws IOException {
        gDevice.executeShellCommand("svc data disable");
    }
    public static void enableSimData() throws IOException {
        gDevice.executeShellCommand("svc data enable");
    }
    public static void navToUpdate() throws Exception {
        Iris4GAction.startSettings();
        clickById(SettingPage.advance_id);
        clickByText("Update");
        waitTime(1);
    }
    public static void navToSetDate() throws Exception {
        Iris4GAction.startSettings();
        clickById(SettingPage.system_id);
        clickByText("Date & time");
        clickByText("Automatic date & time");
        clickByText("Off");
        clickByText("Set date");
    }
    public static String getCurrentDate() throws UiObjectNotFoundException {
        String currentDateString=new UiObject(new UiSelector().checked(true)).getContentDescription();
//        int currentDate= Integer.parseInt(currentDateString);
        logger.info("currentDateIs:"+currentDateString);
        return currentDateString;
    }
    public static String getDispayYear() throws UiObjectNotFoundException {
        String year=getObjectById("android:id/date_picker_header_year").getText();
        logger.info("yearIs:"+year);
        return year;
    }
    public static String getDispayWeek() throws UiObjectNotFoundException {
        String week_month_day=getObjectById(SettingPage.dispay_week_month_day).getText();
        logger.info("weekMonthDayIs:"+week_month_day);
        String week=week_month_day.substring(0,3);
        logger.info("weekIs:"+week);
        return week;
        }
    public static String getDispayMonth()throws UiObjectNotFoundException {
        String week_month_day = getObjectById(SettingPage.dispay_week_month_day).getText();
        String month = week_month_day.substring(5, 8);
        logger.info("monthIs:"+month);
        return month;
    }
    public static String getDispayDay()throws UiObjectNotFoundException {
        String week_month_day = getObjectById(SettingPage.dispay_week_month_day).getText();
        String day = week_month_day.substring(week_month_day.length()-2,week_month_day.length());
        logger.info("dayIs:"+day);
        return day;
    }
    public static Boolean checkYear() throws UiObjectNotFoundException {
       logger.info("yyyyy:"+getCurrentDate().substring(getCurrentDate().length()-4,getCurrentDate().length()));
        if (getCurrentDate().substring(getCurrentDate().length()-4,getCurrentDate().length()) .equals(getDispayYear())){
            return true;
        }else {
            logger.info("checkYearFailed");
            return false;
        }
    }
    public static Boolean checkMonth() throws UiObjectNotFoundException {
        logger.info("mmmmm:"+getCurrentDate().substring(3,6));
        if (getDispayMonth().equals(getCurrentDate().substring(3,6))){
            return true;
        }else {
            logger.info("checkMonthFailed");
            return false;
        }
    }
    public static Boolean checkDay() throws UiObjectNotFoundException {
        logger.info("dddddd:"+getCurrentDate().substring(0,2));
        if (getCurrentDate().charAt(0)!=('0')) {
            if (getDispayDay().equals(getCurrentDate().substring(0, 2))){
                return true;
            } else {
                logger.info("checkDayFailed1");
                return false;
            }
        }else {
            if (getDispayDay().charAt(1)==(getCurrentDate().charAt(1))) {
                return true;
            } else {
                logger.info("checkDayFailed2");
                return false;
            }
        }
    }
    public static String getRandomDay(){
        String result=String.valueOf((int)((Math.random()*30+1)));
        logger.info(result);
        return result;
    }
    //设置从不休眠
    public static void setNeverTime() throws Exception {
        Iris4GAction.startSettings();
        clickByText("Device");
        Iris4GAction.ScrollViewByText("Display");
        clickByText("Display");
        clickByText("Sleep");
        Iris4GAction.ScrollViewByText("Never");
        clickByText("Never");
    }
}
