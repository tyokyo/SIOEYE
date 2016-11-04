package usa.testcase.me;

import android.graphics.Rect;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.widget.HorizontalScrollView;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import action.MeAction;
import bean.BroadcastBean;
import bean.WatcherBean;
import ckt.base.VP2;
import usa.page.App;
import usa.page.Me;

/**
 * Created by elon on 2016/10/13.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class BroadCastsCase extends VP2{
    Logger logger = Logger.getLogger(BroadCastsCase.class.getName());
    public WatcherBean getWatcher() throws UiObjectNotFoundException, IOException {
        WatcherBean watcherBean = new WatcherBean();
        UiObject u =  gDevice.findObject(new UiSelector().resourceId(Me.BROADCAST_VIEW_WATCHER_COUNT));
        String watcher = u.getChild(new UiSelector().className("android.widget.TextView").index(1)).getText();
        String comments = u.getChild(new UiSelector().className("android.widget.TextView").index(3)).getText();
        String zan =  u.getChild(new UiSelector().className("android.widget.TextView").index(5)).getText();
        makeToast(zan,3);
        logger.info("active zan is"+zan);
        watcherBean.setComments(comments);
        watcherBean.setWatch(watcher);
        watcherBean.setZan(zan);
        return  watcherBean;
    }
    public BroadcastBean getBean(int index) throws UiObjectNotFoundException {
        UiObject u =  gDevice.findObject(new UiSelector().resourceId(Me.BROADCAST_VIEW).index(index));
        String title = u.getChild(new UiSelector().resourceId(Me.BROADCAST_TITLE)).getText();
        String desc = u.getChild(new UiSelector().resourceId(Me.BROADCAST_DESC)).getText();
        String time = u.getChild(new UiSelector().resourceId(Me.BROADCAST_TIME)).getText();
        String like = u.getChild(new UiSelector().resourceId(Me.BROADCAST_LIKE)).getText();
        BroadcastBean bb = new BroadcastBean();
        bb.setBroadcast_desc(desc);
        bb.setBroadcast_like(like);
        bb.setBroadcast_time(time);
        bb.setBroadcast_title(title);
        return  bb;
    }
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    public void getPlayTime() throws UiObjectNotFoundException, IOException {
        String startTime="";
        String endTime="";
        //click play screen center
        clickById(Me.BROADCAST_VIEW_WATCHER_COUNT,0,100);
        for (int i=0;i<10;i++){
            if (getObjectById(Me.BROADCAST_VIEW_VIDEO_CURRENT_TIME).exists()){
                startTime = getObjectById(Me.BROADCAST_VIEW_VIDEO_CURRENT_TIME).getText();
                break;
            }else{
                clickById(Me.BROADCAST_VIEW_WATCHER_COUNT,0,100);
            }
        }
        for (int i=0;i<10;i++){
            if (getObjectById(Me.BROADCAST_VIEW_VIDEO_END_TIME).exists()){
                endTime = getObjectById(Me.BROADCAST_VIEW_VIDEO_END_TIME).getText();
                break;
            }else{
                clickById(Me.BROADCAST_VIEW_WATCHER_COUNT,0,100);
            }
        }
        makeToast(startTime+"|"+endTime,10);

    }
    //播放视频60秒
    @Test
    public void testViewVideo() throws UiObjectNotFoundException, IOException {
        MeAction.navToBroadcasts();

        UiObject2 listView = gDevice.findObject(By.res(Me.BROADCAST_VIEW));
        waitTime(5);
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz(HorizontalScrollView.class));
        int size = lisCollect.size();
        Random random = new Random();
        int rd = random.nextInt(size);
        UiObject2 bsobj = lisCollect.get(rd);
        bsobj.click();
        waitTime(3);
        gDevice.wait(Until.gone(By.res(Me.BROADCAST_VIEW_VIDEO_LOADING)),60000);
        Asst.assertTrue("time out 60 seconds.",!getObjectById(Me.BROADCAST_VIEW_VIDEO_LOADING).exists());
        //click play screen center
        clickById(Me.BROADCAST_VIEW_WATCHER_COUNT,0,100);

    }
    //1:编辑title 2:放弃保存
    @Test
    public void testEditTitleCancel() throws UiObjectNotFoundException, IOException {
        MeAction.navToBroadcasts();
        UiObject2 listView = gDevice.findObject(By.res(Me.BROADCAST_VIEW));
        waitTime(5);
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz(HorizontalScrollView.class));
        int size = lisCollect.size();
        Random random = new Random();
        int rd = random.nextInt(size);
        BroadcastBean bbe = getBean(rd);

        UiObject2 obj = lisCollect.get(rd);
        obj.swipe(Direction.LEFT,0.9f);
        clickByText("Edit Title");
        gDevice.wait(Until.findObject(By.res(Me.BROADCAST_VIEW_VIDEO_TITLE_MODIFY)),20000);
        getUiObjectById(Me.BROADCAST_VIEW_VIDEO_TITLE_MODIFY).clearTextField();
        String expect_title=getRandomString(3);
        getUiObjectById(Me.BROADCAST_VIEW_VIDEO_TITLE_MODIFY).setText(expect_title);
        clickByText("Cancel");
        obj.swipe(Direction.RIGHT,0.9f);

        BroadcastBean bs = getBean(rd);
        String active_title = bs.getBroadcast_title();
        Asst.assertEquals("modify title cancel",bbe.getBroadcast_title(),active_title);
        makeToast(expect_title,3);
        Spoon.screenshot(gDevice,"modify_title_cancel");
    }
    //title 输入字符长度3
    @Test
    public void testEditTitle3() throws UiObjectNotFoundException, IOException {
        MeAction.navToBroadcasts();
        UiObject2 listView = gDevice.findObject(By.res(Me.BROADCAST_VIEW));
        waitTime(5);
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz(HorizontalScrollView.class));
        int size = lisCollect.size();
        Random random = new Random();
        int rd = random.nextInt(size);
        BroadcastBean bbe = getBean(rd);

        UiObject2 obj = lisCollect.get(rd);
        obj.swipe(Direction.LEFT,0.9f);
        clickByText("Edit Title");
        gDevice.wait(Until.findObject(By.res(Me.BROADCAST_VIEW_VIDEO_TITLE_MODIFY)),20000);
        getUiObjectById(Me.BROADCAST_VIEW_VIDEO_TITLE_MODIFY).clearTextField();
        String expect_title=getRandomString(3);
        getUiObjectById(Me.BROADCAST_VIEW_VIDEO_TITLE_MODIFY).setText(expect_title);
        clickByText("OK");

        BroadcastBean bs = getBean(rd);
        String active_title = bs.getBroadcast_title();
        Asst.assertEquals("modify title",expect_title,active_title);
        makeToast(expect_title,3);
        Spoon.screenshot(gDevice,"modify_title_3");
    }
    //title 输入字符长度70
    @Test
    public void testEditTitle70() throws UiObjectNotFoundException, IOException {
        MeAction.navToBroadcasts();
        UiObject2 listView = gDevice.findObject(By.res(Me.BROADCAST_VIEW));
        waitTime(5);
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz(HorizontalScrollView.class));
        int size = lisCollect.size();
        Random random = new Random();
        int rd = random.nextInt(size);
        BroadcastBean bbe = getBean(rd);

        UiObject2 obj = lisCollect.get(rd);
        obj.swipe(Direction.LEFT,0.9f);
        clickByText("Edit Title");
        gDevice.wait(Until.findObject(By.res(Me.BROADCAST_VIEW_VIDEO_TITLE_MODIFY)),20000);
        getUiObjectById(Me.BROADCAST_VIEW_VIDEO_TITLE_MODIFY).clearTextField();
        String expect_title=getRandomString(70);
        getUiObjectById(Me.BROADCAST_VIEW_VIDEO_TITLE_MODIFY).setText(expect_title);
        clickByText("OK");

        BroadcastBean bs = getBean(rd);
        String active_title = bs.getBroadcast_title();
        Asst.assertEquals("modify title",expect_title,active_title);
        makeToast(expect_title,3);
        Spoon.screenshot(gDevice,"modify_title_max");
    }
    //title 输入字符长度>70
    @Test
    public void testEditTitleMoreThan70() throws UiObjectNotFoundException, IOException {
        MeAction.navToBroadcasts();
        UiObject2 listView = gDevice.findObject(By.res(Me.BROADCAST_VIEW));
        waitTime(5);
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz(HorizontalScrollView.class));
        int size = lisCollect.size();
        Random random = new Random();
        int rd = random.nextInt(size);
        BroadcastBean bbe = getBean(rd);

        UiObject2 obj = lisCollect.get(rd);
        obj.swipe(Direction.LEFT,0.9f);
        clickByText("Edit Title");
        gDevice.wait(Until.findObject(By.res(Me.BROADCAST_VIEW_VIDEO_TITLE_MODIFY)),20000);
        getUiObjectById(Me.BROADCAST_VIEW_VIDEO_TITLE_MODIFY).clearTextField();
        String expect_title=getRandomString(80);
        shellInputText(expect_title);
        //getUiObjectById(Me.BROADCAST_VIEW_VIDEO_TITLE_MODIFY).setText(expect_title);
        clickByText("OK");

        BroadcastBean bs = getBean(rd);
        String active_title = bs.getBroadcast_title();
        Asst.assertEquals("modify title",expect_title.substring(0,70),active_title);
        makeToast(active_title,3);
        Spoon.screenshot(gDevice,"modify_title_more_than_max");
    }
    @Test
    public void testDeleteBroadcastsVideo() throws UiObjectNotFoundException, IOException {
        MeAction.navToBroadcasts();
        UiObject2 listView = gDevice.findObject(By.res(Me.BROADCAST_VIEW));
        waitTime(5);
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz(HorizontalScrollView.class));
        int size = lisCollect.size();
        Random random = new Random();
        int rd = random.nextInt(size);
        BroadcastBean bbe = getBean(rd);

        UiObject2 obj = lisCollect.get(rd);
        obj.swipe(Direction.LEFT,0.9f);

        clickByText("Edit Title");
        gDevice.wait(Until.findObject(By.res(Me.BROADCAST_VIEW_VIDEO_TITLE_MODIFY)),20000);
        getUiObjectById(Me.BROADCAST_VIEW_VIDEO_TITLE_MODIFY).clearTextField();
        String expect_title=getRandomString(10);
        shellInputText(expect_title);
        clickByText("OK");
        waitTime(3);


        List<UiObject2> lisCollect_update = gDevice.findObjects(By.clazz(HorizontalScrollView.class));
        UiObject2 obj_update = lisCollect_update.get(rd);
        obj_update.swipe(Direction.LEFT,0.9f);
        clickByText("Delete");
        gDevice.wait(Until.findObject(By.res(Me.BROADCAST_VIEW_VIDEO_DELETE)),20000);
        clickById(Me.BROADCAST_VIEW_VIDEO_DELETE);
        getUiObjectById(Me.BROADCASTS_LIST).swipeDown(3);
        waitTime(3);
        gDevice.wait(Until.gone(By.res(Me.BROADCAST_VIEW_STATUS_IMAGE)),60000);
        Asst.assertTrue("delete",!getUiObjectByText(bbe.getBroadcast_title()).exists());
    }
    @Test
    public void testPlayVideoViewers() throws UiObjectNotFoundException {
        MeAction.navToBroadcasts();
        getUiObjectById(Me.BROADCASTS_LIST).swipeDown(3);

    }
    @Test
    public void testComments_Length_120() throws UiObjectNotFoundException, IOException {
        MeAction.navToBroadcasts();

        UiObject2 listView = gDevice.findObject(By.res(Me.BROADCAST_VIEW));
        waitTime(5);
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz(HorizontalScrollView.class));
        int size = lisCollect.size();
        Random random = new Random();
        int rd = random.nextInt(size);
        UiObject2 bsobj = lisCollect.get(rd);
        bsobj.click();
        gDevice.wait(Until.findObject(By.res(Me.BROADCAST_VIEW_TIPTEXT)),20000);
        UiObject zan = getUiObjectById(Me.BROADCAST_VIEW_ZAN);
        int x = zan.getBounds().centerX();
        int y = zan.getBounds().centerY();

        WatcherBean watcherBean1 = getWatcher();
        String comments1 = watcherBean1.getComments();
        String tiptext = getRandomString(120);
        int tip1=Integer.parseInt(comments1);
        clickById(Me.BROADCAST_VIEW_TIPTEXT);
        shellInputText(tiptext);
        //getUiObjectById(Me.BROADCAST_VIEW_TIPTEXT).setText(tiptext);
        gDevice.click(x,y);
        gDevice.pressBack();
        //gDevice.wait(Until.findObject(By.text(tiptext)),60000);
        Asst.assertTrue("comments success",getUiObjectByText(tiptext).exists());

        WatcherBean watcherBean2 = getWatcher();
        String comments2 = watcherBean2.getComments();
        int tip2=Integer.parseInt(comments2);
        Asst.assertEquals(tip1+1,tip2);
        Spoon.screenshot(gDevice,"add_comments_length_120");

    }
    @Test
    public void testComments_Length_130() throws UiObjectNotFoundException, IOException {
        MeAction.navToBroadcasts();
        getObjectById(Me.BROADCAST_VIEW,0).swipeLeft(2);

        UiObject2 listView = gDevice.findObject(By.res(Me.BROADCAST_VIEW));
        waitTime(5);
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz(HorizontalScrollView.class));
        int size = lisCollect.size();
        Random random = new Random();
        int rd = random.nextInt(size);
        UiObject2 bsobj = lisCollect.get(rd);
        bsobj.click();
        gDevice.wait(Until.findObject(By.res(Me.BROADCAST_VIEW_TIPTEXT)),20000);
        UiObject zan = getUiObjectById(Me.BROADCAST_VIEW_ZAN);
        int x = zan.getBounds().centerX();
        int y = zan.getBounds().centerY();

        WatcherBean watcherBean1 = getWatcher();
        String comments1 = watcherBean1.getComments();
        String tiptext = getRandomString(130);
        int tip1=Integer.parseInt(comments1);
        clickById(Me.BROADCAST_VIEW_TIPTEXT);
        shellInputText(tiptext);
        //getUiObjectById(Me.BROADCAST_VIEW_TIPTEXT).setText(tiptext);
        gDevice.click(x,y);
        gDevice.pressBack();
        //gDevice.wait(Until.findObject(By.text(tiptext)),60000);
        tiptext = tiptext.substring(0,120);
        Asst.assertTrue("comments success",getUiObjectByText(tiptext).exists());

        WatcherBean watcherBean2 = getWatcher();
        String comments2 = watcherBean2.getComments();
        int tip2=Integer.parseInt(comments2);
        Asst.assertEquals(tip1+1,tip2);
        Spoon.screenshot(gDevice,"add_comments_length_130");

    }
    @Test
    public void testComments_Length_20() throws UiObjectNotFoundException, IOException {
        //进入broadcasts
        MeAction.navToBroadcasts();
        //随机获取一个直播视频
        MeAction.getRandomBroadcasts().click();
        //等待加载完成-点赞图标变为绿色  可以进行点赞动作
        MeAction.waitBroadcastLoading();

        //获取点赞图标的rect
        UiObject zanRect = getUiObjectById(Me.BROADCAST_VIEW_ZAN);
        Rect rect =zanRect.getBounds();
        //当前的评论数
        WatcherBean watcherBean1 = getWatcher();
        String comments_before = watcherBean1.getComments();
        String input_comments = getRandomString(20);
        int comments_count_before=Integer.parseInt(comments_before);
        //输入评论内容
        clickById(Me.BROADCAST_VIEW_TIPTEXT);
        shellInputText(input_comments);
        //点击评论
        clickRect(rect);
        gDevice.pressBack();
        Asst.assertTrue("comments success",getUiObjectByText(input_comments).exists());

        //验证评论数+1
        WatcherBean watcherBean_after = getWatcher();
        String after_comments = watcherBean_after.getComments();
        int comments_count=Integer.parseInt(after_comments);
        Asst.assertEquals(comments_count_before+1,comments_count);

        Spoon.screenshot(gDevice,"add_comments_length_20");
    }

    //验证点赞超过1000的显示是否正确
    @Test
    public void testZanK() throws UiObjectNotFoundException, IOException {
        MeAction.navToBroadcasts();
        getObjectById(Me.BROADCAST_VIEW,0).swipeLeft(2);

        UiObject2 listView = gDevice.findObject(By.res(Me.BROADCAST_VIEW));
        waitTime(5);
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz(HorizontalScrollView.class));
        int size = lisCollect.size();
        Random random = new Random();
        int rd = random.nextInt(size);
        BroadcastBean bs = getBean(rd);
        String zans = bs.getBroadcast_like();
        logger.info("current like is"+zans);
        String expectZans;
        double dzan = (Double.parseDouble(zans)+1000)/1000;
        expectZans = String.format("%.1f", dzan)+"K";
        logger.info("expectZans is"+expectZans);
        UiObject2 bsobj = lisCollect.get(rd);
        makeToast("click index:"+rd,(float) 5);
        bsobj.click();
        gDevice.wait(Until.findObject(By.res(Me.BROADCAST_VIEW_TIPTEXT)),20000);
        UiObject zan = getUiObjectById(Me.BROADCAST_VIEW_ZAN);
        int x = zan.getBounds().centerX();
        int y = zan.getBounds().centerY();

        for (int i =0;i<10;i++){
            //gDevice.click(x,y);
            clickById(Me.BROADCAST_VIEW_ZAN);
            //getUiObjectById(Me.BROADCAST_VIEW_ZAN);
        }
        WatcherBean watcherBean = getWatcher();
        String addZans = watcherBean.getZan();
        Asst.assertEquals("zan 1000+ check",expectZans,addZans);

        //listView.swipe(Direction.LEFT, 0.8f, 3000);
        //listView.scroll(Direction.DOWN, 0.8f, 3000);


        /*clickById(Me.BROADCAST_VIEW,1);
        getObjectById(Me.BROAD_TIP_TEXT).setText("test");

        int w = gDevice.getDisplayWidth()-100;
        int h =gDevice.getDisplayHeight()-120;*/
        //gDevice.click(w,h);

        Spoon.screenshot(gDevice,"zan_1000");
    }

}
