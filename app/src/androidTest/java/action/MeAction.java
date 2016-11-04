package action;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Until;

import com.squareup.spoon.Spoon;

import ckt.base.VP2;
import page.Me;

/**
 * Created by elon on 2016/10/27.
 */
public class MeAction extends VP2{
    public static void navToLiveConfiguration(){
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.LIVE_CONFIGURATION);
        waitTime(2);
    }
    public static void navToNotifications(){
        clickById(Me.ID_MAIN_TAB_ME);
        clickById(Me.NOTIFICATIONS);
        gDevice.wait(Until.gone(By.res(Me.LOADING_FOLLOWERS)),40000);
    }
    public static void navToActivities(){
        clickById(Me.ID_MAIN_TAB_ME);
        Spoon.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickById(Me.NAV_EDIT_ACTIVITY);
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
    }
    public static void navToUserEdit(){
        clickById(Me.ID_MAIN_TAB_ME);
        Spoon.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
    }
    public static void navToNickName(){
        clickById(Me.ID_MAIN_TAB_ME);
        Spoon.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickById(Me.NAV_EDIT_NICKNAME);
    }
    public static void navToBroadcasts(){
        clickById(Me.ID_MAIN_TAB_ME);
        Spoon.screenshot(gDevice,"Me");
        clickById(Me.ID_ME_BROADCAST);
        gDevice.wait(Until.gone(By.res(Me.BROADCAST_VIEW)),20000);
    }
}
