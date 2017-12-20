package cn.action;

import android.graphics.Rect;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.StaleObjectException;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import ckt.base.VP2;
import cn.page.DiscoverPage;
import cn.page.NewPage;
import cn.page.PlayPage;


/**
 * Created by yajuan on 2017/7/19.
 */

public class NewAction extends VP2 {
    private static Logger logger = Logger.getLogger(NewAction.class.getName());

    //获取视频封面观看数
    public static int getNewWatchNumber() {
        waitUntilFind(NewPage.ID_NEW_VIDEO,10000);
        UiObject2 swipe = getObject2ById(NewPage.ID_NEW_VIDEO);
        List<UiObject2> linearLayouts = swipe.findObjects(By.clazz(android.widget.LinearLayout.class));
        waitTime(5);
        logger.info(linearLayouts.size() + "");
        int watch = 0;
        for (UiObject2 linearLayout : linearLayouts) {
            List<UiObject2> textViews = linearLayout.findObjects(By.depth(1).clazz(android.widget.TextView.class));
            waitTime(3);
            if (textViews.size() == 3||textViews.size() == 2) {
                try {
                    watch =cover(textViews.get(0).getText());
                    break;
                }catch (NumberFormatException e){
                    logger.info("-NumberFormatException");
                }
            }

        }
        logger.info("getNewWatchNumber-"+watch);
        return watch;
    }

    //返回随机视频的index
    public static int getRandomVideoIndex() {
        getObject2ById(NewPage.ID_NEW_VIDEO).swipe(Direction.DOWN,0.5f);
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
        waitUntilFind(NewPage.ID_NEW_VIDEO,10000);
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
        waitUntilFind(NewPage.ID_NEW_VIDEO,10000);
        UiObject2 swipe = getObject2ById(NewPage.ID_NEW_VIDEO);
        List<UiObject2> linearLayouts = swipe.findObjects(By.clazz(android.widget.LinearLayout.class));
        waitTime(5);
        logger.info(linearLayouts.size() + "");
        int like = 0;
        for (UiObject2 linearLayout : linearLayouts) {
            List<UiObject2> textViews = linearLayout.findObjects(By.depth(1).clazz(android.widget.TextView.class));
            if (textViews.size() == 3||textViews.size() == 2) {
                try {
                    like = cover(textViews.get(1).getText());
                    break;
                }catch (NumberFormatException e){
                    logger.info("-NumberFormatException");
                }
            }
        }
        logger.info("getZanNumber-"+like);
        return like;
    }
    //播放最新视频
    public static int navToPlayNewlistVideo() throws UiObjectNotFoundException {
        clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
        clickById(DiscoverPage.ID_NEW_RECOMMEND);
        int person = 0;
        UiObject2 swipe_target = getObject2ById(NewPage.ID_NEW_VIDEO);
        swipe_target.swipe(Direction.DOWN, 0.2f);
        waitTime(5);
        List<UiObject2> linearLayouts = swipe_target.findObjects(By.clazz(android.widget.LinearLayout.class));
        logger.info(linearLayouts.size() + "");
        int zanBeforeNumber = 0;
        List<UiObject2> textViews;
        for (UiObject2 linearLayout : linearLayouts) {
            try {
                textViews = linearLayout.findObjects(By.depth(1).clazz(android.widget.TextView.class));
                if (textViews.size() == 3) {
                    person = Integer.parseInt(textViews.get(0).getText());
                    //获取点赞数
                    zanBeforeNumber = NewAction.getZanNumber();
                    logger.info("赞前人数是" + zanBeforeNumber + "人");
                    Spoon.screenshot("before_zan", "" + zanBeforeNumber);
                    //点击视频进行播放
                    textViews.get(0).getParent().getParent().getParent().click();
                    waitTime(3);
                    //等待视频加载完成
                    BroadcastAction.waitBroadcastLoading();
                    break;
                }
            } catch (StaleObjectException e) {
                e.printStackTrace();
            }
        }
        if (!id_exists(PlayPage.BROADCAST_VIEW_ZAN)) {
            linearLayouts = swipe_target.findObjects(By.clazz(android.widget.LinearLayout.class));
            for (UiObject2 linearLayout : linearLayouts) {
                textViews = linearLayout.findObjects(By.depth(1).clazz(android.widget.TextView.class));
                if (textViews.size() == 2) {
                    if (linearLayout.getChildCount() == 2 && linearLayout.getParent().getChildCount() == 2) {
                        person = Integer.parseInt(textViews.get(0).getText());
                        //获取点赞数
                        zanBeforeNumber = NewAction.getZanNumber();
                        logger.info("赞前人数是" + zanBeforeNumber + "人");
                        Spoon.screenshot("before_zan", "" + zanBeforeNumber);
                        //点击视频进行播放
                        textViews.get(0).getParent().getParent().getParent().click();
                        waitTime(3);
                        break;
                    }
                }
            }
        }
        return zanBeforeNumber;


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