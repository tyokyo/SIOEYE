package cn.action;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.StaleObjectException;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.widget.RelativeLayout;

import com.squareup.spoon.Spoon;

import android.support.test.uiautomator.Direction;


import java.util.List;
import java.util.logging.Logger;

import bean.VideoBean;
import ckt.base.VP2;

import cn.page.DiscoverPage;
import cn.page.MePage;


import java.util.Random;

import cn.page.NewPage;

import android.graphics.Rect;


/**
 * Created by yajuan on 2017/7/19.
 */

public class NewAction extends VP2 {
    private static Logger logger = Logger.getLogger(NewAction.class.getName());

    //聊天室里获取点赞数
    public static int getNewZanNumber() {

        // UiObject2 swipe = getObject2ById(NewPage.ID_NEW_VIDEO);
        List<UiObject2> relativeLayouts = getObject2ById(NewPage.ID_NEW_VIDEO).findObjects(By.clazz(RelativeLayout.class).depth(1));
        waitTime(5);
        logger.info(relativeLayouts.size() + "");
        relativeLayouts.get(0).click();
        waitUntilFind(MePage.TV_AUCHOR_ID,5000);
        clickById(MePage.TV_AUCHOR_ID);
        waitUntilFind(MePage.VIDEO_CHAT_NUMBER,5000);
        int like = cover(getObject2ById(MePage.VIDEO_CHAT_NUMBER).getText());
        return like;
    }

    //获取视频封面观看数
    public static int getNewWatchNumber() {
        UiObject2 swip = getObject2ById(NewPage.ID_NEW_VIDEO);
        List<UiObject2> linearLayouts = swip.findObjects(By.clazz(android.widget.LinearLayout.class));
        waitTime(5);
        logger.info(linearLayouts.size() + "");
        int watch = 0;
        for (UiObject2 linearLayout : linearLayouts) {
            List<UiObject2> textViews = linearLayout.findObjects(By.depth(1).clazz(android.widget.TextView.class));
            if (textViews.size() == 3||textViews.size() == 2) {
                watch = Integer.parseInt(textViews.get(0).getText());
                break;
            }
        }
        return watch;
    }

    //随机选择视频视频
    public static UiObject2 getRandomVideo() {
        UiObject2 new_list = getObject2ById(NewPage.ID_NEW_VIDEO);
        // List<UiObject2> linearLayouts = new_list.findObjects(By.clazz(android.widget.LinearLayout.class));
        List<UiObject2> linearLayouts = gDevice.findObjects(By.res(NewPage.ID_NEW_VIDEO));
        //获取视频列表数目
        int size = linearLayouts.size();
        //随机选择一个视频
        Random random = new Random();
        int number = random.nextInt(size);
        UiObject2 new_video = linearLayouts.get(number);
        //new_video.click();
        return new_video;
    }

    //观看视频统计点赞数、观看人数、评论数
    public static VideoBean getNumberPlayVideo() throws UiObjectNotFoundException {
        VideoBean videoBean = new VideoBean();
        List<UiObject2> linearLayouts = gDevice.findObjects(By.res(NewPage.ID_NEW_VIDEO));
        logger.info(linearLayouts.size() + "");
        linearLayouts.get(0).click();
        clickById(MePage.TV_AUCHOR_ID);
        try {
            waitUntilFind(MePage.VIDEO_WATCH_NUMBER,5000);
            waitUntilFind(MePage.VIDEO_CHAT_NUMBER,5000);
            waitUntilFind(MePage.VIDEO_LIKE_NUMBER,5000);
            //获取观看数
            int like = Integer.parseInt(getObject2ById(MePage.VIDEO_WATCH_NUMBER).getText());
            //获取点赞数
            int zan = Integer.parseInt(getObject2ById(MePage.VIDEO_CHAT_NUMBER).getText());
            // 获取评论数
            int comment = Integer.parseInt(getObject2ById(MePage.VIDEO_LIKE_NUMBER).getText());
            // videoBean.setAddress(address);
            videoBean.setLike(like);
            videoBean.setZan(zan);
            videoBean.setComment(comment);
            //获取点赞数
            logger.info("getNumberPlayVideo-" + videoBean.toString());
            Spoon.screenshot("About");
            // gDevice.pressBack();

        } catch (StaleObjectException e) {
            e.printStackTrace();
        }

        return videoBean;
    }
    //获取视频封面位置信息
    public static String getLocation() {
        UiObject2 swipe = getObject2ById(NewPage.ID_NEW_VIDEO);
        waitTime(5);
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
        List<UiObject2> linearLayouts = swip.findObjects(By.clazz(android.widget.LinearLayout.class));
        logger.info(linearLayouts.size() + "");
        int like = 0;
        for (UiObject2 linearLayout : linearLayouts) {
            List<UiObject2> textViews = linearLayout.findObjects(By.depth(1).clazz(android.widget.TextView.class));
            if (textViews.size() == 3||textViews.size() == 2) {
                like = cover(textViews.get(1).getText());
                break;
            }
        }
        return like;
    }
    //播放最新视频
    public static int navToPlayNewlistVideo() throws UiObjectNotFoundException {
        clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
        clickById(DiscoverPage.ID_NEW_RECOMMEND);
        int person = 0;
        UiObject2 swipe_target = getObject2ById(NewPage.ID_New_Vediolist);
        swipe_target.swipe(Direction.UP, 0.6f);
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
        if (!id_exists(MePage.BROADCAST_VIEW_ZAN)) {
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