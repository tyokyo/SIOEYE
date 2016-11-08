package cn.action;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Until;

import ckt.base.VP2;
import cn.page.WatchPage;

/**
 * Created by elon on 2016/11/7.
 */
public class WatchAction extends VP2{

    public static void navToWatchSearch(){
        clickByText("Watch");
        clickById(WatchPage.WATCH_SEARCH_BTN);
        waitTime(2);
        gDevice.wait(Until.findObject(By.res(WatchPage.WATCH_SEARCH_TRENDING_TITLE)),40000);
    }
}
