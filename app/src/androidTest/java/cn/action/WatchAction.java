package cn.action;

import android.graphics.Point;
import android.graphics.Rect;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;
import com.squareup.spoon.Spoon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ckt.base.VP;
import ckt.base.VP2;
import cn.page.App;
import cn.page.MePage;
import cn.page.WatchPage;

/**
 * Created by elon on 2016/11/7.
 */
public class WatchAction extends VP2{
    public static Logger logger = Logger.getLogger(WatchAction.class.getName());
    /*
    视频的视频长度对应的-Object
    * */
    public static List<UiObject2> getDurationObjects(){
        List<UiObject2> dus=new ArrayList<>();
        List<UiObject2> frameLayouts= gDevice.findObjects(By.clazz(android.widget.FrameLayout.class));
        for (UiObject2 obj:frameLayouts){
            Rect obj_rect=obj.getVisibleBounds();
            logger.info("obj_rect-"+obj_rect.toString());
            int childCount = obj.getChildCount();
            List<UiObject2> children=obj.getChildren();
            if (children.size()==2){
                Rect c1=children.get(0).getVisibleBounds();
                Rect c2=children.get(1).getVisibleBounds();
                if (obj_rect.equals(c1)&&obj_rect.equals(c2)){
                    logger.info("c1-"+c1.toString());
                    logger.info("c2-"+c2.toString());
                    boolean duration=obj.hasObject(By.clazz(android.widget.TextView.class));
                    if (duration){
                        UiObject2 time=obj.findObject(By.clazz(android.widget.TextView.class));
                        dus.add(time);
                    }
                }
            }
            logger.info(dus.size()+"");
        }
        return  dus;
    }
    /**
     *搜索指定sioeye id 的用户
     * @param sioEyeId
     */
    public static void searchFollowingUser(String sioEyeId) throws IOException, UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        Point p = MeAction.getPointToDoComment();
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
    /**
     *关注->搜索
     */
    public static void navToWatchSearch(){
        clickById(MePage.ID_MAIN_TAB_LIVE);
        clickById(WatchPage.WATCH_SEARCH_BTN);
        waitTime(2);
        gDevice.wait(Until.findObject(By.res(WatchPage.WATCH_SEARCH_TRENDING_TITLE)),40000);
    }
    /**
     *关注->搜索
     */
    public static void navToWatch(){
        clickById(MePage.ID_MAIN_TAB_LIVE);
        waitTime(2);
    }
}
