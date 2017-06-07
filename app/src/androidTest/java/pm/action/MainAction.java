package pm.action;

import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import ckt.base.VP2;
import pm.page.MePage;

/**
 * Created by elon on 2016/11/8.
 */
public class MainAction extends VP2 {
    public static void clickMe(){
        try {
            if (id_exists(MePage.ID_MAIN_TAB_ME)){
                clickById(MePage.ID_MAIN_TAB_ME);
            }else{
                clickByText("Me");
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        waitTime(3);
    }
    public static void clickDiscover() throws UiObjectNotFoundException {
        waitUntilFind(MePage.ID_MAIN_TAB_DISCOVER,10000);
        if (id_exists(MePage.ID_MAIN_TAB_DISCOVER)){
            clickById(MePage.ID_MAIN_TAB_DISCOVER);
        }else{
            clickByText("Discover");
        }
    }
    //Discover
    public static void navToDiscover() throws UiObjectNotFoundException {
        clickDiscover();
        Spoon.screenshot("navToDiscover");
    }
    //Discover
    public static void navToDiscover(String page) throws UiObjectNotFoundException {
        clickDiscover();
        Spoon.screenshot("navToDiscover");
    }
    public static void clickLive() throws UiObjectNotFoundException {
        if (id_exists(MePage.ID_MAIN_TAB_LIVE)){
            clickById(MePage.ID_MAIN_TAB_LIVE);
        }else{
            clickByText("Watch");
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
        waitTime(2);
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
            clickByText("Remote");
        }
    }
}
