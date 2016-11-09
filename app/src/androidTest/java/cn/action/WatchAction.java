package cn.action;

import android.graphics.Point;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;
import com.squareup.spoon.Spoon;
import java.io.IOException;
import ckt.base.VP2;
import cn.page.App;
import cn.page.MePage;
import cn.page.WatchPage;

/**
 * Created by elon on 2016/11/7.
 */
public class WatchAction extends VP2{
    /**
     *搜索指定sioeye id 的用户
     * @param sioEyeId
     */
    public static void searchFollowingUser(String sioEyeId) throws IOException, UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        Point p = MeAction.getSearchLocation();
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);

        clickByText("Watch");
        clickById(WatchPage.WATCH_SEARCH_BTN);
        gDevice.wait(Until.findObject(By.res(WatchPage.WATCH_SEARCH_TRENDING_TITLE)),40000);
        if (!getUiObjectById(WatchPage.WATCH_SEARCH_TRENDING_LIST).exists()){
            clickById(WatchPage.WATCH_SEARCH_FILTER_INPUT);
            waitTime(2);
            gDevice.click(p.x,p.y);
            gDevice.wait(Until.findObject(By.res(WatchPage.WATCH_SEARCH_TRENDING_TITLE)),40000);
        }
        Spoon.screenshot(gDevice,"trending_list");
        clickById(WatchPage.WATCH_SEARCH_FILTER_INPUT);
        shellInputText(sioEyeId);
        gDevice.click(p.x,p.y);
        gDevice.wait(Until.gone(By.res(WatchPage.WATCH_SEARCH_DATA_LOADING)),60000);
        Spoon.screenshot(gDevice,"search_list_"+sioEyeId);
    }
    public static void navToWatchSearch(){
        clickById(MePage.ID_MAIN_TAB_LIVE);
        clickById(WatchPage.WATCH_SEARCH_BTN);
        waitTime(2);
        gDevice.wait(Until.findObject(By.res(WatchPage.WATCH_SEARCH_TRENDING_TITLE)),40000);
    }
}
