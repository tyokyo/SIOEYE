package testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.EventCondition;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import bean.BroadcastBean;
import bean.WatcherBean;
import ckt.base.VP2;
import ckt.tools.Spoon2;
import page.App;
import page.Me;

/**
 * Created by elon on 2016/10/13.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class BroadCastsCase extends VP2{

    public WatcherBean getWatcher() throws UiObjectNotFoundException, IOException {
        WatcherBean watcherBean = new WatcherBean();
        UiObject u =  gDevice.findObject(new UiSelector().resourceId(Me.BROADCAST_VIEW_WATCHER_COUNT));
        String watcher = u.getChild(new UiSelector().className("android.widget.TextView").index(1)).getText();
        String comments = u.getChild(new UiSelector().className("android.widget.TextView").index(3)).getText();
        String zan =  u.getChild(new UiSelector().className("android.widget.TextView").index(5)).getText();
        makeToast(zan,3);
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
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME);
    }

    public void testViewVideo(){
        clickByText("Me");
        clickByText("Broadcasts");
        gDevice.wait(Until.gone(By.res(Me.BROADCAST_VIEW)),20000);

        UiObject2 listView = gDevice.findObject(By.res(Me.BROADCAST_VIEW));
        waitTime(5);
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz(HorizontalScrollView.class));
        int size = lisCollect.size();
        Random random = new Random();
        int rd = random.nextInt(size);
        UiObject2 bsobj = lisCollect.get(rd);
        bsobj.click();
        gDevice.wait(Until.gone(By.res(Me.BROADCAST_VIEW_VIDEO_LOADING)),20000);
        //com.sioeye.sioeyeapp.ijkplayer.media.TextureRenderView

    }
    public void testEditTitle(){

    }
    public void testDeleteBroadcastsVideo(){

    }
    public void testPlayVideoViewers()
    {

    }
    @Test
    public void testComments_Length_120() throws UiObjectNotFoundException, IOException {
        clickByText("Me");
        clickByText("Broadcasts");
        gDevice.wait(Until.gone(By.res(Me.BROADCAST_VIEW)),20000);

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
        Assert.assertTrue("comments success",getUiObjectByText(tiptext).exists());

        WatcherBean watcherBean2 = getWatcher();
        String comments2 = watcherBean2.getComments();
        int tip2=Integer.parseInt(comments2);
        Assert.assertEquals(tip1+1,tip2);
        Spoon2.screenshot(gDevice,"add_comments_length_120");

    }
    @Test
    public void testComments_Length_130() throws UiObjectNotFoundException, IOException {
        clickByText("Me");
        clickByText("Broadcasts");
        gDevice.wait(Until.gone(By.res(Me.BROADCAST_VIEW)),20000);
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
        Assert.assertTrue("comments success",getUiObjectByText(tiptext).exists());

        WatcherBean watcherBean2 = getWatcher();
        String comments2 = watcherBean2.getComments();
        int tip2=Integer.parseInt(comments2);
        Assert.assertEquals(tip1+1,tip2);
        Spoon2.screenshot(gDevice,"add_comments_length_130");

    }
    @Test
    public void testComments_Length_20() throws UiObjectNotFoundException, IOException {
        clickByText("Me");
        clickByText("Broadcasts");
        gDevice.wait(Until.gone(By.res(Me.BROADCAST_VIEW)),20000);
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
        String tiptext = getRandomString(20);
        int tip1=Integer.parseInt(comments1);
        clickById(Me.BROADCAST_VIEW_TIPTEXT);
        shellInputText(tiptext);
        //getUiObjectById(Me.BROADCAST_VIEW_TIPTEXT).setText(tiptext);
        gDevice.click(x,y);
        gDevice.pressBack();
        //gDevice.wait(Until.findObject(By.text(tiptext)),60000);
        Assert.assertTrue("comments success",getUiObjectByText(tiptext).exists());

        WatcherBean watcherBean2 = getWatcher();
        String comments2 = watcherBean2.getComments();
        int tip2=Integer.parseInt(comments2);
        Assert.assertEquals(tip1+1,tip2);
        Spoon2.screenshot(gDevice,"add_comments_length_20");
    }

    @Test
    public void testZan() throws UiObjectNotFoundException, IOException {
        clickByText("Me");
        clickByText("Broadcasts");
        gDevice.wait(Until.gone(By.res(Me.BROADCAST_VIEW)),20000);
        getObjectById(Me.BROADCAST_VIEW,0).swipeLeft(2);

        UiObject2 listView = gDevice.findObject(By.res(Me.BROADCAST_VIEW));
        waitTime(5);
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz(HorizontalScrollView.class));
        int size = lisCollect.size();
        Random random = new Random();
        int rd = random.nextInt(size);
        BroadcastBean bs = getBean(rd);
        String zans = bs.getBroadcast_like();
        String expectZans;
        double dzan = (Double.parseDouble(zans)+1000)/1000;
        expectZans = String.format("%.1f", dzan)+"K";

        UiObject2 bsobj = lisCollect.get(rd);
        makeToast("click index:"+rd,(float) 5);
        bsobj.click();
        gDevice.wait(Until.findObject(By.res(Me.BROADCAST_VIEW_TIPTEXT)),20000);
        UiObject zan = getUiObjectById(Me.BROADCAST_VIEW_ZAN);
        int x = zan.getBounds().centerX();
        int y = zan.getBounds().centerY();

        for (int i =0;i<=1000;i++){
            gDevice.click(x,y);
            //makeToast("ZAN+"+i, (float) 0.2);
        }
        WatcherBean watcherBean = getWatcher();
        String addZans = watcherBean.getZan();
        Assert.assertEquals("zan check",expectZans,addZans);

        //listView.swipe(Direction.LEFT, 0.8f, 3000);
        //listView.scroll(Direction.DOWN, 0.8f, 3000);

        /*clickById(Me.BROADCAST_VIEW,1);
        getObjectById(Me.BROAD_TIP_TEXT).setText("test");

        int w = gDevice.getDisplayWidth()-100;
        int h =gDevice.getDisplayHeight()-120;*/
        //gDevice.click(w,h);

        Spoon2.screenshot(gDevice,"zan_1000");
    }

}
