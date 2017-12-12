package cn.testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.logging.Logger;

import ckt.annotation.PerformanceTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.BroadcastAction;
import cn.action.CollectionAction;
import cn.action.MainAction;
import cn.action.MeAction;
import cn.action.PlayAction;
import cn.page.App;
import cn.page.DiscoverPage;
import cn.page.MePage;
import cn.page.PlayPage;


/**
 * Created by zyj on 2017/9/29.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class CollectionCase extends VP2{
    Logger logger = Logger.getLogger(BroadCastsCase.class.getName());
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        //确保App 处于登录状态
        AccountAction.inLogin();
        CollectionAction.collectDiscoverVideo();
    }
    /**
     *1. 播放收藏视频*/
    @Test
    @PerformanceTest
    public void  testPlayCollection() throws UiObjectNotFoundException {
        MeAction.navToCollection();
        int size= CollectionAction.getCollectionSize();
        if (size>=1){
            int index = CollectionAction.getRandomCollectionIndex();
            UiObject2 object=CollectionAction.getRandomCollectionElement(index);
            object.click();
            BroadcastAction.waitBroadcastLoading();
            gDevice.wait(Until.gone(By.res(PlayPage.BROADCAST_VIEW_VIDEO_LOADING)),60000);
            Asst.assertTrue("time out 60 seconds.",!getObjectById(PlayPage.BROADCAST_VIEW_VIDEO_LOADING).exists());
            Spoon.screenshot("play_video");
        }
        else{
            String str="Your collection is empty, go save some videos";
            Asst.assertEquals("没有收藏",str,getTex(MePage.COLLECION_EMPTY));
        }
    }
    /**
     *2. 列表中取消收藏*/
    @Test
    @PerformanceTest
    public  void testCancelCollection() throws UiObjectNotFoundException {
        MeAction.navToCollection();
        int size= CollectionAction.getCollectionSize();
        if (size>=1){
            int index = CollectionAction.getRandomCollectionIndex();
            UiObject2 object=CollectionAction.getRandomCollectionElement(index);
            UiObject2 view= BroadcastAction.getVideoAction(object);
            view.getChildren().get(2).click(); //点击取消收藏
            Spoon.screenshot("Cancel");
            clickById(MePage.BROADCAST_EDIT_DELETE_OK);  //确认取消收藏
        }
    }
    /**3.分享收藏视频
     * */
    @Test
    @PerformanceTest
    public void testShareCollection() throws UiObjectNotFoundException {
        MeAction.navToCollection();
        int size= CollectionAction.getCollectionSize();
        if (size>=1){
            int index = CollectionAction.getRandomCollectionIndex();
            UiObject2 object=CollectionAction.getRandomCollectionElement(index);
            UiObject2 view=BroadcastAction.getVideoAction(object);
            view.getChildren().get(1).click(); //点击分享
            waitUntilFind(MePage.BROADCAST_SHARE,3000);
            Asst.assertTrue(id_exists(MePage.BROADCAST_SHARE));
        }
    }
    /**
     * 4.播放界面点击取消收藏*/
    @Test
    @PerformanceTest
    public void testCancelCollectionPlay() throws UiObjectNotFoundException {
        MeAction.navToCollection();
        int size= CollectionAction.getCollectionSize();
        if (size>=1){
            int index = CollectionAction.getRandomCollectionIndex();
            UiObject2 object=CollectionAction.getRandomCollectionElement(index);
            object.click();
            BroadcastAction.waitBroadcastLoading();
            gDevice.wait(Until.gone(By.res(PlayPage.BROADCAST_VIEW_VIDEO_LOADING)),60000);
            Asst.assertTrue("time out 60 seconds.",!getObjectById(PlayPage.BROADCAST_VIEW_VIDEO_LOADING).exists());
            Spoon.screenshot("play_video");
            CollectionAction.getClickCollection();  //观看视频点击取消收藏
            Spoon.screenshot("collect");
        }
    }
    /**
     *5.收藏发现界面视频
     */
    @Test
    @PerformanceTest
    public void testCollectDiscover() throws UiObjectNotFoundException {
        MainAction.navToDiscover();
        UiObject2 swipe_target = getObject2ById(DiscoverPage.ID_SWIPE_TARGET);
        List<UiObject2> linearLayouts = swipe_target.findObjects(By.clazz(android.widget.RelativeLayout.class));
        int size = linearLayouts.size();
        linearLayouts.get(size - 1).click();
        BroadcastAction.waitBroadcastLoading();
        gDevice.wait(Until.gone(By.res(PlayPage.BROADCAST_VIEW_VIDEO_LOADING)), 60000);
        CollectionAction.getClickCollection();
        Spoon.screenshot("testViewVideo");
        gDevice.pressBack();
    }
    /**
     *6. 举报收藏视频*/
    @Test
    @PerformanceTest
    public void testReportCollection() throws UiObjectNotFoundException, InterruptedException {
        MeAction.navToCollection();
        int size= CollectionAction.getCollectionSize();
        if (size>=1){
            int index = CollectionAction.getRandomCollectionIndex();
            UiObject2 object=CollectionAction.getRandomCollectionElement(index);
            UiObject2 view=BroadcastAction.getVideoAction(object);
            view.getChildren().get(3).click(); //点击举报
            waitTime(3);
            if (id_exists(PlayPage.REPORT_LIST)) {
                PlayAction.reportVideo();
            }
        }
    }
}
