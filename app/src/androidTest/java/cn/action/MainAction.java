package cn.action;

import com.squareup.spoon.Spoon;

import ckt.base.VP2;
import cn.page.MePage;

/**
 * Created by elon on 2016/11/8.
 */
public class MainAction extends VP2 {
    //Discover
    public static void navToDiscover(){
        clickById(MePage.ID_MAIN_TAB_DISCOVER);
        Spoon.screenshot("navToDiscover");
    }
    //Discover
    public static void navToLive(){
        clickById(MePage.ID_MAIN_TAB_LIVE);
        Spoon.screenshot("navToLive");
    }
    //Discover
    public static void navToMe(){
        clickById(MePage.ID_MAIN_TAB_ME);
        Spoon.screenshot("navToMe");
    }
    //Discover
    public static void navToDevice(){
        clickById(MePage.ID_MAIN_TAB_DEVICE);
        Spoon.screenshot("navToDevice");
    }
}
