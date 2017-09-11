package iris4G.action;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;

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
}
