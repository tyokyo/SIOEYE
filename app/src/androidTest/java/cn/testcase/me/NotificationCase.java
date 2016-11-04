package cn.testcase.me;

import android.graphics.Rect;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import cn.action.MeAction;
import cn.action.Nav;
import ckt.base.VP2;
import usa.page.App;
import usa.page.Me;
import usa.page.Watch;

/**
 * Created by elon on 2016/10/31.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class NotificationCase extends VP2 {
    public String username;
    public int followed;
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }

    @Test
    public void testNotificationAD() throws UiObjectNotFoundException {
        MeAction.navToNotifications();
        waitTime(3);
        int f_users = gDevice.findObjects(By.res(Me.NOTIFICATIONS_USER_FOLLOW)).size();
        if (f_users>=1) {
            Rect rect = getObjectById(Me.NOTIFICATIONS_USER_FOLLOW).getBounds();
            int width = gDevice.getDisplayWidth();
            gDevice.click(width-rect.centerX(),rect.centerY());
            gDevice.wait(Until.findObject(By.res(Watch.WATCH_USER_MINI_NAME)),40000);

            username=getObjectById(Watch.WATCH_USER_MINI_NAME).getText();
            gDevice.pressBack();
            clickById(Me.NOTIFICATIONS_USER_FOLLOW);
            waitTime(2);
            Spoon.screenshot("delete_all_who_followed_me_"+username);
            gDevice.pressBack();
            Nav.navToFollowers();
            followed = gDevice.findObjects(By.res(Me.USER_FOLLOW)).size();

            Asst.assertEquals("delete_followed_user",f_users-1,followed);
            Spoon.screenshot("delete_all_who_followed_me_"+username);
            gDevice.pressBack();




            /*//+Follow
            MeAction.navToNotifications();
            Rect rect1 = getUiObjectByTextContains(username).getBounds();
            gDevice.click(rect1.left-10,rect1.centerY());
            clickById(Watch.WATCH_USER_MINI_FOLLOW);
            gDevice.pressBack();
            int ff_users = gDevice.findObjects(By.res(Me.NOTIFICATIONS_USER_FOLLOW)).size();
            Asst.assertEquals("delete_followed_user",followed+1,ff_users);
            Spoon.screenshot("add_follow",username+"followed_me");*/
        }
    }

}
