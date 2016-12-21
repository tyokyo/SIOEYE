package cn.action;

import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import ckt.base.VP2;
import cn.page.MePage;

/**
 * Created by elon on 2016/11/8.
 */
public class MainAction extends VP2 {
    public static void clickMe(){
        try {
            if (id_exists(MePage.ID_MAIN_TAB_ME)){
                clickById(MePage.ID_MAIN_TAB_ME);
            }else{
                clickByText("我");
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        waitTime(3);
    }
    public static void clickDiscover() throws UiObjectNotFoundException {
        if (id_exists(MePage.ID_MAIN_TAB_DISCOVER)){
            clickById(MePage.ID_MAIN_TAB_DISCOVER);
        }else{
            clickByText("发现");
        }
    }
    //Discover
    public static void navToDiscover() throws UiObjectNotFoundException {
        clickDiscover();
        Spoon.screenshot("navToDiscover");
    }
    public static void clickLive() throws UiObjectNotFoundException {
        if (id_exists(MePage.ID_MAIN_TAB_LIVE)){
            clickById(MePage.ID_MAIN_TAB_LIVE);
        }else{
            clickByText("关注");
        }
    }
    //Discover
    public static void navToLive() throws UiObjectNotFoundException {
        clickLive();
        Spoon.screenshot("navToLive");
    }
    //Discover
    public static void navToMe() throws UiObjectNotFoundException {
        clickMe();
        Spoon.screenshot("navToMe");
    }
    //Discover
    public static void navToDevice() throws UiObjectNotFoundException {
        clickDevice();
        Spoon.screenshot("navToDevice");
    }
    public static void clickDevice() throws UiObjectNotFoundException {
        if (id_exists(MePage.ID_MAIN_TAB_DEVICE)){
            clickById(MePage.ID_MAIN_TAB_DEVICE);
        }else{
            clickByText("相机");
        }
    }
}
