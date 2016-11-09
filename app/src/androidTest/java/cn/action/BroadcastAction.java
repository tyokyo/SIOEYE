package cn.action;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.widget.FrameLayout;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import bean.BroadcastBean;
import bean.WatcherBean;
import ckt.base.VP2;
import cn.page.MePage;

/**
 * Created by elon on 2016/11/8.
 */
public class BroadcastAction extends VP2{
    private static Logger logger = Logger.getLogger(BroadcastAction.class.getName());
    //根据title 删除视频
    public static void deleteBroadcast(String title) throws UiObjectNotFoundException {
        scrollAndGetUIObject(title);
        UiObject broadcast=getUiObjectByText(title);
        broadcast.swipeLeft(20);
        clickById(MePage.BROADCAST_DELETE);
        clickById(MePage.BROADCAST_EDIT_DELETE_OK);
        waitUntilFind(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY,10000);
    }
    //直播-直播数目
    public static int getBroadcastsSize(){
        List<UiObject2> lisCollect = gDevice.findObjects(By.res(MePage.BROADCAST_CONTENT));
        int size = lisCollect.size();
        return  size;
    }
    //fans-直播数目
    public static int getFansBroadcastsSize(){
        UiObject2 list = getObject2ById(MePage.USER_FOLLOW_LIST);
        List<UiObject2> lisCollect = list.findObjects(By.clazz(FrameLayout.class));
        int size = lisCollect.size();
        return  size;
    }
    //随机获取一个broadcasts对象的index
    public static int getRandomBroadcastsIndex(){
        UiObject2 listView = gDevice.findObject(By.res(MePage.BROADCAST_VIEW));
        List<UiObject2> lisCollect = gDevice.findObjects(By.res(MePage.BROADCAST_CONTENT));
        int size = lisCollect.size();
        Random random = new Random();
        int rd = random.nextInt(size);
        logger.info("getRandomBroadcastsIndex:"+rd);
        return  rd;
    }
    //随机获取一个broadcasts对象
    public static UiObject2 getRandomBroadcasts(int index){
        List<UiObject2> lisCollect = gDevice.findObjects(By.res(MePage.BROADCAST_VIEW));
        logger.info("getRandomBroadcasts:"+lisCollect.size());
        UiObject2 broadcast = lisCollect.get(index);
        return  broadcast;
    }
    //wait 加载完成 此时点赞图标变为绿色
    public  static void waitBroadcastLoading() throws UiObjectNotFoundException {
        gDevice.wait(Until.findObject(By.res(MePage.BROADCAST_VIEW_TIPTEXT)),20000);
        waitUntilFind(MePage.BROADCAST_VIEW_ZAN,10);
        for (int i = 0; i <20 ; i++) {
            if (getObjectById(MePage.BROADCAST_VIEW_ZAN).isEnabled()==true&&
                    getObjectById(MePage.BROADCAST_VIEW_TIPTEXT).isEnabled()==true){
                break;
            }else{
                waitTime(4);
            }
        }
    }
    public static WatcherBean getWatcher() throws UiObjectNotFoundException, IOException {
        WatcherBean watcherBean = new WatcherBean();
        UiObject u =  gDevice.findObject(new UiSelector().resourceId(MePage.BROADCAST_VIEW_WATCHER_COUNT));
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
    public static BroadcastBean getChinaBean(int index) throws UiObjectNotFoundException {
        UiObject hsv_view =  gDevice.findObject(new UiSelector().resourceId(MePage.BROADCAST_VIEW).index(index));
        String title = hsv_view.getChild(new UiSelector().resourceId(MePage.BROADCAST_TITLE)).getText();
        BroadcastBean bb = new BroadcastBean();
        bb.setBroadcast_title(title);
        return  bb;
    }
    public static BroadcastBean getBean(int index) throws UiObjectNotFoundException {
        UiObject u =  gDevice.findObject(new UiSelector().resourceId(MePage.BROADCAST_VIEW).index(index));
        String title = u.getChild(new UiSelector().resourceId(MePage.BROADCAST_TITLE)).getText();
        String desc = u.getChild(new UiSelector().resourceId(MePage.BROADCAST_DESC)).getText();
        String time = u.getChild(new UiSelector().resourceId(MePage.BROADCAST_TIME)).getText();
        String like = u.getChild(new UiSelector().resourceId(MePage.BROADCAST_LIKE)).getText();
        BroadcastBean bb = new BroadcastBean();
        bb.setBroadcast_desc(desc);
        bb.setBroadcast_like(like);
        bb.setBroadcast_time(time);
        bb.setBroadcast_title(title);
        return  bb;
    }
    public void getPlayTime() throws UiObjectNotFoundException, IOException {
        String startTime="";
        String endTime="";
        //click play screen center
        clickById(MePage.BROADCAST_VIEW_WATCHER_COUNT,0,100);
        for (int i=0;i<10;i++){
            if (getObjectById(MePage.BROADCAST_VIEW_VIDEO_CURRENT_TIME).exists()){
                startTime = getObjectById(MePage.BROADCAST_VIEW_VIDEO_CURRENT_TIME).getText();
                break;
            }else{
                clickById(MePage.BROADCAST_VIEW_WATCHER_COUNT,0,100);
            }
        }
        for (int i=0;i<10;i++){
            if (getObjectById(MePage.BROADCAST_VIEW_VIDEO_END_TIME).exists()){
                endTime = getObjectById(MePage.BROADCAST_VIEW_VIDEO_END_TIME).getText();
                break;
            }else{
                clickById(MePage.BROADCAST_VIEW_WATCHER_COUNT,0,100);
            }
        }
        makeToast(startTime+"|"+endTime,10);

    }
}
