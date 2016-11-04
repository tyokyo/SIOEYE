package usa.action;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.squareup.spoon.Spoon;

import java.util.List;
import java.util.Random;

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
    //随机获取一个broadcasts对象
    public static UiObject2 getRandomBroadcasts(){
        UiObject2 listView = gDevice.findObject(By.res(Me.BROADCAST_VIEW));
        waitTime(5);
        //List<UiObject2> lisCollect = gDevice.findObjects(By.clazz(HorizontalScrollView.class));
        List<UiObject2> lisCollect = gDevice.findObjects(By.res(Me.BROADCAST_CONTENT));
        int size = lisCollect.size();
        Random random = new Random();
        int rd = random.nextInt(size);
        UiObject2 broadcast = lisCollect.get(rd);
        return  broadcast;
    }
    //wait 加载完成 此时点赞图标变为绿色
    public  static void waitBroadcastLoading() throws UiObjectNotFoundException {
        gDevice.wait(Until.findObject(By.res(Me.BROADCAST_VIEW_TIPTEXT)),20000);
        waitUntilFind(Me.BROADCAST_VIEW_ZAN,10);
        for (int i = 0; i <20 ; i++) {
            if (getObjectById(Me.BROADCAST_VIEW_ZAN).isEnabled()==true&&
                    getObjectById(Me.BROADCAST_VIEW_TIPTEXT).isEnabled()==true){
                break;
            }else{
                waitTime(4);
            }
        }
    }
}
