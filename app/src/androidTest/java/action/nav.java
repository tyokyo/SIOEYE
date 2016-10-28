package action;

import android.graphics.Point;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import ckt.base.VP2;
import page.App;
import page.Me;
import page.Watch;

/**
 * Created by elon on 2016/10/20.
 */
public class Nav extends VP2{
    public static void navToFollowing(){
        clickByText("Me");
        clickById(Me.ID_ME_FOLLOWING);
        waitTime(2);
        gDevice.wait(Until.gone(By.res(Me.LOADING_FOLLOWERS)),40000);
    }
    public static void navToBrodCasts(){
        clickByText("Me");
        clickById(Me.ID_ME_BROADCAST);
        waitTime(2);
        gDevice.wait(Until.gone(By.res(Me.BROADCAST_VIEW)),40000);
    }

    public static void navToFollowers(){
        clickByText("Me");
        clickById(Me.ID_ME_FOLLOWERS);
        waitTime(2);
        gDevice.wait(Until.gone(By.res(Me.BROADCAST_VIEW_VIDEO_LOADING)),40000);
    }
    public static void navToWatchSearch(){
        clickByText("Watch");
        clickById(Watch.WATCH_SEARCH_BTN);
        waitTime(2);
        gDevice.wait(Until.findObject(By.res(Watch.WATCH_SEARCH_TRENDING_TITLE)),40000);
    }
    /**
     *获取点赞图标的X Y,作为键盘输入的确定按钮
     * @param
     */
    public static Point getSearchLocation() throws UiObjectNotFoundException, IOException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        Point p = new Point();
        clickByText("Me");
        clickByText("Broadcasts");
        gDevice.wait(Until.gone(By.res(Me.BROADCAST_VIEW)),20000);
        gDevice.wait(Until.findObject(By.res(Me.BROADCASTS_LIST)),20000);
        gDevice.wait(Until.findObject(By.res(Me.BROADCAST_CONTENT)),20000);
        waitTime(5);
        List<UiObject2> lisCollect = gDevice.findObjects(By.res(Me.BROADCAST_CONTENT));
        int size = lisCollect.size();
        Random random = new Random();
        int rd = random.nextInt(size);
        UiObject2 obj = lisCollect.get(rd);
        obj.click();
        gDevice.wait(Until.findObject(By.res(Me.BROADCAST_VIEW_TIPTEXT)),20000);
        UiObject zan = getUiObjectById(Me.BROADCAST_VIEW_ZAN);
        int x = zan.getBounds().centerX();
        int y = zan.getBounds().centerY();
        p.set(x,y);
        makeToast(p.x+"|"+p.y,3);
        return  p;
    }

}
