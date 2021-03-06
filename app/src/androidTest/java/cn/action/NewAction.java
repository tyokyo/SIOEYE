package cn.action;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.StaleObjectException;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import com.squareup.spoon.Spoon;
import android.support.test.uiautomator.Direction;
import java.util.List;
import java.util.logging.Logger;
import ckt.base.VP2;
import cn.page.DiscoverPage;
import java.util.Random;

import cn.page.NewPage;
import cn.page.PlayPage;

import android.graphics.Rect;
import android.util.Log;


/**
 * Created by yajuan on 2017/7/19.
 */

public class NewAction extends VP2 {
    private static Logger logger = Logger.getLogger(NewAction.class.getName());

    //获取视频封面观看数
    public static int getNewWatchNumber() {
        UiObject2 swip = getObject2ById(NewPage.ID_NEW_VIDEO);
        List<UiObject2> RelativeLayouts = swip.findObjects(By.clazz(android.widget.RelativeLayout.class));
        waitTime(5);
        logger.info(RelativeLayouts.size() + "");
        int watch = 0;
        for (UiObject2 RelativeLayout : RelativeLayouts) {
            List<UiObject2> LinearLayouts = RelativeLayout.findObjects(By.depth(1).clazz(android.widget.LinearLayout.class));
            if (LinearLayouts.size() == 2) {
                UiObject2 LinearLayout=LinearLayouts.get(1);
                List<UiObject2>  textViews=LinearLayout.findObjects(By.clazz(android.widget.TextView.class));
                watch = cover(textViews.get(0).getText());
                break;
            }
        }
        return watch;
    }

    //返回随机视频的index
    public static int getRandomVideoIndex() {
       UiObject2 object= getObject2ById(NewPage.ID_NEW_VIDEO);
        object.swipe(Direction.DOWN,0.5f);
        List<UiObject2> relativeLayouts = gDevice.findObjects(By.res(NewPage.ID_NEW_VIDEO));
        //获取视频列表数目
        int size = relativeLayouts.size();
        //随机选择一个视频
        Random random = new Random();
        int number = random.nextInt(size);
        return number==0?number:number-1;
    }
    public static UiObject2 getRandomVideo(int index) {
        List<UiObject2> relativeLayouts = gDevice.findObjects(By.res(NewPage.ID_NEW_VIDEO));
        //获取视频列表数目
        int size = relativeLayouts.size();
        //随机选择一个视频
        UiObject2 new_video = relativeLayouts.get(index);
        return new_video;

    }
    //获取视频封面位置信息
    public static String getLocation() {
        UiObject2 swipe = getObject2ById(NewPage.ID_NEW_VIDEO);
        List<UiObject2> linearLayouts = swipe.findObjects(By.clazz(android.widget.LinearLayout.class));
        logger.info(linearLayouts.size() + "");
        String address = null;
        for (UiObject2 linearLayout : linearLayouts) {
            List<UiObject2> textViews = linearLayout.findObjects(By.depth(1).clazz(android.widget.TextView.class));
            if (textViews.size() == 3) {
                address =textViews.get(2).getText();
                break;
            }
        }
        return address;
    }
    //得到视频封面点赞人数
    public static int getZanNumber() {
        UiObject2 swip = getObject2ById(NewPage.ID_NEW_VIDEO);
        List<UiObject2> RelativeLayouts = swip.findObjects(By.clazz(android.widget.RelativeLayout.class));
        waitTime(5);
        logger.info(RelativeLayouts.size() + "");
        int like = 0;
        for (UiObject2 RelativeLayout : RelativeLayouts) {
            List<UiObject2> LinearLayouts = RelativeLayout.findObjects(By.depth(1).clazz(android.widget.LinearLayout.class));
            if (LinearLayouts.size() == 2) {
               UiObject2 LinearLayout=LinearLayouts.get(1);
                List<UiObject2>  textViews=LinearLayout.findObjects(By.clazz(android.widget.TextView.class));
                like = cover(textViews.get(1).getText());
                break;
            }
        }
        return like;
    }
    //播放最新视频
    public static void navToPlayNewlistVideo() throws UiObjectNotFoundException {
        clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
        clickById(DiscoverPage.ID_NEW_RECOMMEND);
        int index = NewAction.getRandomVideoIndex();
        UiObject2 video =NewAction.getRandomVideo(index);
        video.click();


    }
    //最新页面-搜索按钮
    public static void navToNewSearch() {
        clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
        //点击最新TAB
        clickById(DiscoverPage.ID_NEW_RECOMMEND);
        waitTime(3);
        UiObject2 frameLayout = getObject2ById(DiscoverPage.ID_NEW_RECOMMEND).getParent().getParent();
        UiObject2 searchObject = frameLayout.findObject(By.clazz(android.widget.ImageView.class));
        Rect searchRect = searchObject.getVisibleBounds();
        //double click
        clickRect(searchRect);
    }
}