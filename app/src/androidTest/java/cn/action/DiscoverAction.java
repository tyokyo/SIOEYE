package cn.action;

import android.graphics.Rect;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.StaleObjectException;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Assert;

import java.util.List;
import java.util.logging.Logger;

import bean.VideoBean;
import ckt.base.VP2;
import cn.page.App;
import cn.page.DiscoverPage;
import cn.page.MePage;
import cn.page.NewPage;
import cn.page.PlayPage;
import usa.page.Device;
import usa.page.Discover;

/**
 * Created by caibing.yin on 2016/11/5.
 */

public class DiscoverAction extends VP2 {
    private static Logger logger = Logger.getLogger(DiscoverAction.class.getName());

    //得到点赞人数
    public static int getZanNumber() {
        //clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
        UiObject2 swip = getObject2ById(DiscoverPage.ID_Swipe_target);
        waitTime(5);
        List<UiObject2> linearLayouts = swip.findObjects(By.clazz(android.widget.LinearLayout.class));
        logger.info(linearLayouts.size() + "");
        int like = 0;
        for (UiObject2 linearLayout : linearLayouts) {
            List<UiObject2> textViews = linearLayout.findObjects(By.depth(1).clazz(android.widget.TextView.class));
            if (textViews.size() == 3) {
                like = cover(textViews.get(1).getText());
                break;
            }
        }
        return like;
    }

    //发现页面-搜索按钮
    public static void navToSearch() {
        clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
        waitTime(3);
        UiObject2 frameLayout = getObject2ById(DiscoverPage.ID_HOT_RECOMMEND).getParent().getParent();
        UiObject2 searchObject = frameLayout.findObject(By.clazz(android.widget.ImageView.class));
        Rect searchRect = searchObject.getVisibleBounds();
        //double click
        clickRect(searchRect);
    }

    //广告
    public static void navToAd() {
        clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
        clickById(DiscoverPage.ID_MAIN_TAB_AD_SPALSH);
    }

    public static void navToNew() {
        clickById(DiscoverPage.ID_NEW_RECOMMEND);
    }

    public static void navToPopular() {
        clickById(DiscoverPage.ID_HOT_RECOMMEND);
    }
    //统计推荐列表的个数
    public static int countRecommendList() throws UiObjectNotFoundException {
        int total = 0;
        if (id_exists(DiscoverPage.ID_RECOMMEND_AVATAR)){
            List<UiObject2> LinearLayouts=gDevice.findObjects(By.res("cn.sioeye.sioeyeapp:id/checked"));
            total=LinearLayouts.size();
            logger.info("countRecommendList-"+total+"");
        }
        return total;
    }
    //index=0-3
    public static String navToRecommendList(int index, int click_time) {
        clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
        waitUntilFind(DiscoverPage.ID_Swipe_target, 5000);
        //刷新列表
        UiObject2 swipe_target = getObject2ById(DiscoverPage.ID_Swipe_target);
        swipe_target.swipe(Direction.DOWN, 0.6f);
        logger.info("刷新列表");
        waitUntilFind(DiscoverPage.ID_MAIN_TAB_RECOMMAND_LIST, 30000);
        //into discover
        List<UiObject2> linearLayout_avatars = getObject2ById(DiscoverPage.ID_MAIN_TAB_RECOMMAND_LIST).findObjects(By.clazz(LinearLayout.class));
        UiObject2 linearLayout = linearLayout_avatars.get(index);
        //get RecommendList
        String trend_name = linearLayout.findObject(By.clazz(TextView.class)).getText();
        logger.info(trend_name);
        //get RecommendList name
        for (int i = 0; i < click_time; i++) {
            linearLayout.click();
        }
        waitUntilFind(DiscoverPage.ID_MAIN_TAB_PROFILE_MINI_HOME, 30);
        Spoon.screenshot("navToRecommendList");
        return trend_name;
    }

    //取得Discover页面中RecommendList中头像对应的的昵称
    public static String getnickname() throws UiObjectNotFoundException {
        clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
        UiObject Recommand_list = getObjectById(DiscoverPage.ID_MAIN_TAB_RECOMMAND_LIST);
        String nickname = Recommand_list.getChild(new UiSelector().index(0)).getText();
        return nickname;
    }

    public static void scrollAdSplash() {
        clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
    }

    public static void scrollRecommendList() throws UiObjectNotFoundException {
        clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
        waitUntilFind(DiscoverPage.ID_MAIN_TAB_RECOMMAND_LIST,5000);
        if (id_exists(DiscoverPage.ID_MAIN_TAB_RECOMMAND_LIST)){
            //向左滑动2步
            getObjectById(DiscoverPage.ID_MAIN_TAB_RECOMMAND_LIST).swipeLeft(2);
            //如果没有推荐列表，滑动会进入new界面，这个时候右滑动，恢复到popular界面
            if (id_exists(NewPage.ID_NEW_VIDEO)) {
                getObjectById(NewPage.ID_NEW_VIDEO).swipeRight(2);
            }
            waitTime(3);
        }
    }

    //滑动RecommendList查找
    public static boolean scrollRedToFind(String text) {
        UiScrollable listScrollable = new UiScrollable(new UiSelector().
                resourceId(DiscoverPage.ID_MAIN_TAB_RECOMMAND_LIST).scrollable(true));
        listScrollable.setAsHorizontalList();
        boolean status = false;
        try {
            if (listScrollable.scrollTextIntoView(text)) {
                status = true;
            }
        } catch (UiObjectNotFoundException e) {
            status = false;
        }
        return status;
    }

    public static void deleteFollowing(String userName) throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
        MeAction.navToFollowing();
        scrollAndGetUIObject(userName);
        clickByText(userName);
        waitUntilFind(DiscoverPage.ID_PROFILE_DELETE_FOLLOW, 10000);
        clickById(DiscoverPage.ID_PROFILE_DELETE_FOLLOW);
        waitTime(3);
        gDevice.pressBack();
        logger.info("deleteFollowing-" + userName);
    }

    public static void checkAddFriendsInMyFollowing(String target_nick_name) throws UiObjectNotFoundException {
        MeAction.navToFollowing();
        waitUntilFind(MePage.FOLLOWERING_VIEW, 6000);
        UiObject expectObj = scrollAndGetUIObject(target_nick_name);
        if (expectObj != null) {
            if (!expectObj.exists()) {
                Spoon.screenshot("swip_to_find", target_nick_name + "Failed");
                Assert.fail("AddFriendsRecommand" + target_nick_name + "Failed");
            }
        } else {
            Assert.fail("出现异常,nick name获取为空-未找到" + target_nick_name);
        }
    }

    public static String checkMiniProfileNumFollowerAddOneAfterFollow() throws UiObjectNotFoundException {
        //该目标用户的Follower的数量，+1表示点击关注后该用户的Follower实际数量
        int expect_NumFollower = Integer.parseInt(getTex(DiscoverPage.ID_MAIN_TAB_PROFILE_MINI_NUM_FOLLOWER));
        //关注操作
        clickById(DiscoverPage.ID_MAIN_TAB_PROFILE_MINI_FOLLOW);

        waitTime(3);
        //关注后该目标用户的Follower的数量，
        int active_NumFollower = Integer.parseInt(getTex(DiscoverPage.ID_MAIN_TAB_PROFILE_MINI_NUM_FOLLOWER));
        //id for user
        String sioEye_id = getObject2ById(DiscoverPage.ID_PROFILE_MINI_DEVICES).findObject(By.clazz(android.widget.TextView.class)).getText();
        Spoon.screenshot("testAddFriendsRecommend0", "该用户followers没有加1");
        //断言该用户followers有没有+1
        Asst.assertEquals("添加推荐用户为好友后，该用户followers没有加1", expect_NumFollower + 1, active_NumFollower);
        //关闭弹出框
        //clickByClass("android.widget.ImageView", 2);
        gDevice.pressBack();
        return sioEye_id;
    }

    //得到观看人数
    public static int getPersonNumber() {
        //clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
        UiObject2 swipe_target = getObject2ById(DiscoverPage.ID_Swipe_target);
        waitTime(5);
        List<UiObject2> linearLayouts = swipe_target.findObjects(By.clazz(android.widget.LinearLayout.class));
        logger.info(linearLayouts.size() + "");
        int person = 0;
        boolean find = false;
        List<UiObject2> textViews;
        for (UiObject2 linearLayout : linearLayouts) {
            textViews = linearLayout.findObjects(By.depth(1).clazz(android.widget.TextView.class));
            if (textViews.size() == 3) {
                person = Integer.parseInt(textViews.get(0).getText());
                find = true;
                break;
            }
        }
        if (find == false) {
            linearLayouts = swipe_target.findObjects(By.clazz(android.widget.LinearLayout.class));
            for (UiObject2 linearLayout : linearLayouts) {
                textViews = linearLayout.findObjects(By.depth(1).clazz(android.widget.TextView.class));
                if (textViews.size() == 2) {
                    if (linearLayout.getChildCount() == 2 && linearLayout.getParent().getChildCount() == 2) {
                        person = Integer.parseInt(textViews.get(0).getText());
                        find = true;
                        break;
                    }
                }
            }
        }
        return person;
    }

    //获得位置信息
    public static String getLocationInfo() {
        List<UiObject2> textViews = getObject2ById(DiscoverPage.ID_SWIPE_TARGET).findObjects(By.clazz(TextView.class));
        return textViews.get(11).getText();
    }

    //滑动视频列表
    public static void scrollVideoList() {
        UiObject2 swipe_target = getObject2ById(DiscoverPage.ID_Swipe_target);
        swipe_target.swipe(Direction.UP, 0.6f);
        waitTime(5);
    }

    /*回放界面上第一个包含 点赞数 回放数的视频
     返回对象：VideoBean，watch zan 数量 address 信息(如果存在地址信息及返回)
    */
    public static VideoBean playBackVideo(boolean play) throws UiObjectNotFoundException {
        VideoBean videoBean = new VideoBean();
        boolean isFind = false;
        UiObject2 swipe_target = getObject2ById(DiscoverPage.ID_Swipe_target);
        List<UiObject2> linearLayouts = swipe_target.findObjects(By.clazz(android.widget.LinearLayout.class));
        logger.info(linearLayouts.size() + "");
        List<UiObject2> textViews;
        for (UiObject2 linearLayout : linearLayouts) {
            try {
                textViews = linearLayout.findObjects(By.depth(1).clazz(android.widget.TextView.class));
                if (textViews.size() == 3) {
                    isFind = true;
                    logger.info("playBackVideo-size=3");
                    String WatchStr = textViews.get(0).getText();
                    int Watch = cover(WatchStr);

                    int zan = 0;
                    String zanStr = textViews.get(1).getText().trim();
                    zan = cover(zanStr);

                    String address = textViews.get(2).getText();
                    videoBean.setAddress(address);
                    videoBean.setWatch(Watch);
                    videoBean.setZan(zan);
                    //获取点赞数
                    logger.info("playBackVideo-" + videoBean.toString());
                    Spoon.screenshot("zan", "" + zan);
                    //点击视频进行播放
                    if (play) {
                        textViews.get(0).getParent().getParent().getParent().click();
                        waitTime(3);
                        //等待视频加载完成
                        BroadcastAction.waitBroadcastLoading();
                    }
                    break;
                }
            } catch (StaleObjectException e) {
                e.printStackTrace();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (!isFind) {
            for (UiObject2 linearLayout : linearLayouts) {
                textViews = linearLayout.findObjects(By.depth(1).clazz(android.widget.TextView.class));
                if (textViews.size() == 2) {
                    if (linearLayout.getChildCount() == 2 && linearLayout.getParent().getChildCount() == 2) {
                        int Watch = cover(textViews.get(0).getText());
                        int zan = cover(textViews.get(1).getText());
                        videoBean.setAddress("");
                        videoBean.setWatch(Watch);
                        videoBean.setZan(zan);
                        //获取点赞数
                        logger.info("playBackVideo-" + videoBean.toString());
                        Spoon.screenshot("zan", "" + zan);
                        //点击视频进行播放
                        if (play) {
                            textViews.get(0).getParent().getParent().getParent().click();
                            waitTime(3);
                            //等待视频加载完成
                            BroadcastAction.waitBroadcastLoading();
                        }
                        break;
                    }
                }
            }
        }
        return videoBean;
    }

    //点击Discover界面的视频观看
    public static int navToPlayVideo() throws UiObjectNotFoundException {
        clickById(DiscoverPage.ID_MAIN_TAB_DISCOVER);
        int person = 0;
        UiObject2 swipe_target = getObject2ById(DiscoverPage.ID_Swipe_target);
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
                    zanBeforeNumber = DiscoverAction.getZanNumber();
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
                        zanBeforeNumber = DiscoverAction.getZanNumber();
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

    /**
     * 点击广告界面的返回键
     */
    public static void clickADBack() {
        DiscoverAction.navToAd();
        UiObject2 Frame = getObject2ByClass(android.widget.FrameLayout.class);
        waitTime(2);
        List<UiObject2> RelativeLayouts = Frame.findObjects(By.clazz(android.widget.RelativeLayout.class));
        List<UiObject2> ImageViews;
        for (UiObject2 RelativeLayout : RelativeLayouts) {
            ImageViews = RelativeLayout.findObjects(By.clazz(android.widget.ImageView.class).depth(1));
            if (ImageViews.size() == 1) {
                ImageViews.get(0).click();
                break;
            }
        }
    }
    //点击弹出框中的更多选项，拉黑
    public static void addBlackList() throws UiObjectNotFoundException {
       List<UiObject2> object= getObject2ById(DiscoverPage.ID_DISCOVER_PROFILE).getChildren();
        object.get(1).click();
        waitTime(3);
        if (text_exists("Add to blacklist")){
            clickByText("Add to blacklist");  //点击拉黑
            gDevice.pressBack();
        }
    }
}

