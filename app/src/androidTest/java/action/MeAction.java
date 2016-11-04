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
        clickByText("Me");
        clickById(Me.LIVE_CONFIGURATION);
        waitTime(2);
    }
    public static void navToNotifications(){
        clickByText("Me");
        clickById(Me.NOTIFICATIONS);
        gDevice.wait(Until.gone(By.res(Me.LOADING_FOLLOWERS)),40000);
    }
    public static void navToActivities(){
        clickByText("Me");
        Spoon.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickByText("Activity");
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
    }
    public static void navToUserEdit(){
        clickById(Me.ID_MAIN_TAB_ME);
        Spoon.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
    }
}
