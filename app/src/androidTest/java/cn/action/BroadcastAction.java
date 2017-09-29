package cn.action;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.widget.FrameLayout;

import com.squareup.spoon.Spoon;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import bean.BroadcastBean;
import bean.WatcherBean;
import ckt.base.VP2;
import cn.page.MePage;
import cn.page.PlayPage;

/**
 * Created by elon on 2016/11/8.
 */
public class BroadcastAction extends VP2{
    private static Logger logger = Logger.getLogger(BroadcastAction.class.getName());
    public static void navEdit(UiObject2 broadcast){
        //more
        broadcast.findObject(By.text("More")).click();
        //编辑视频标题
        clickByText("Edit the title");
        waitUntilFind(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY,10000);
    }
    // 删除视频
    public static void deleteBroadcast(UiObject2 object) throws UiObjectNotFoundException {
        UiObject2 view=BroadcastAction.getVideoAction(object);
        view.getChildren().get(3).click();
        waitUntilFind(MePage.BROADCAST_VIDEO_MORE_OPTION_LIST,3000);
        clickByText("Delete");
        Spoon.screenshot("Delete");
        clickById(MePage.BROADCAST_EDIT_DELETE_OK);
       // waitUntilFind(MePage.BROADCAST_VIEW_VIDEO_TITLE_MODIFY,10000);
    }
    //直播-直播数目
    public static int getBroadcastsSize(){
        waitHasObject(MePage.BROADCAST_CONTENT,50000);
        List<UiObject2> lisCollect = gDevice.findObjects(By.res(MePage.BROADCAST_VIDEO_THUMB));
        int size = lisCollect.size();
        logger.info("getBroadcastsSize:"+size);
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
        waitHasObject(MePage.BROADCASTS_LIST,10000);
        UiObject2 view = gDevice.findObject(By.res(MePage.BROADCASTS_LIST));
        List<UiObject2> broadcasts = view.findObjects(By.res(MePage.BROADCAST_CONTENT));
        int size = broadcasts.size();
        logger.info("getRandomBroadcastsIndex-size:"+size);
        Random random = new Random();
        int rd = random.nextInt(size);
        logger.info("size-"+size+"random:"+rd);
        return  rd==0?rd:rd-1;
    }
    //随机获取一个普通直播broadcasts对象的index
    public static int getRandomBroadcastsWithNoRoomIndex(){
        waitHasObject(MePage.BROADCASTS_LIST,10000);
        int index = 0;
        boolean exit = true;
        int search = 1;

        while (exit){
            logger.info("================================");
            UiObject2 view = gDevice.findObject(By.res(MePage.BROADCASTS_LIST));
            List<UiObject2> broadcasts = view.findObjects(By.clazz(android.widget.LinearLayout.class).depth(1));
            logger.info("getRandomBroadcastsWithNoRoomIndex size is -"+broadcasts.size());
            for (int j = 1; j <broadcasts.size() ; j++) {
                UiObject2 bsObject = broadcasts.get(j);
                List<UiObject2> lines= bsObject.findObjects(By.clazz(android.widget.LinearLayout.class).depth(1));
                int size = lines.size();
                UiObject2 nav =  lines.get(size-1);
                List<UiObject2> texts = nav.findObjects(By.clazz(android.widget.TextView.class));
                String cover = texts.get(2).getText();
                if ("Cover".equals(cover)){
                    exit=false;
                    logger.info("find Cover");
                    index=j;
                    break;
                }
            }
            if (exit){
                view.swipe(Direction.UP,0.85f,2000);
                waitTime(3);
            }
            search=search+1;
            if (exit){
                if (search==10){
                    exit=false;
                }
            }
            logger.info("================================");
        }
        logger.info("getRandomBroadcastsWithNoRoomIndex-"+index);
        return  index;
    }
    //随机获取一个broadcasts对象
    public static UiObject2 getRandomBroadcastsElement(int index){
        waitHasObject(MePage.BROADCASTS_LIST,10000);
        UiObject2 view = gDevice.findObject(By.res(MePage.BROADCASTS_LIST));
        List<UiObject2> broadcasts = view.findObjects(By.clazz(android.widget.LinearLayout.class).depth(1));
        logger.info("getRandomBroadcastsElement:"+broadcasts.size());
        UiObject2 broadcast = broadcasts.get(index);
        return  broadcast;
    }
    //随机获取一个broadcasts对象并点击
    public static void clickRandomBroadcastsVideo(int index){
        waitHasObject(MePage.BROADCASTS_LIST,10000);
        UiObject2 view = gDevice.findObject(By.res(MePage.BROADCASTS_LIST));
        List<UiObject2> broadcasts = view.findObjects(By.res(MePage.BROADCAST_CONTENT));
        logger.info("getRandomBroadcasts:"+broadcasts.size());
        UiObject2 broadcast = broadcasts.get(index);
        broadcast.click();
    }
    //wait 加载完成 此时点赞图标变为绿色
    public  static void waitBroadcastLoading() throws UiObjectNotFoundException {
        waitUntilFind(PlayPage.BROADCAST_VIEW_TIPTEXT, 20000);
        waitUntilFind(PlayPage.BROADCAST_VIEW_ZAN, 10000);
        for (int i = 0; i < 20; i++) {
            if (getObjectById(PlayPage.BROADCAST_VIEW_ZAN).isEnabled() == true &&
                    getObjectById(PlayPage.BROADCAST_VIEW_TIPTEXT).isEnabled() == true &&
                    getUiObjectByText("Add a comment").exists()) {
                logger.info("聊天室连接成功-说点什么吧");
                break;
            } else {
                waitTime(4);
            }
        }
    }
    //视频回放页面的播放数-点赞数-评论数
    public static WatcherBean getWatcher() throws UiObjectNotFoundException, IOException {
        FollowersAction.clickToAnchor();
        WatcherBean watcherBean = new WatcherBean();
        UiObject u =  gDevice.findObject(new UiSelector().resourceId(PlayPage.BROADCAST_VIEW_WATCHER_COUNT));
        String watcher = getObject2ById(PlayPage.VIDEO_WATCH_NUMBER).getText();
        String comments = getObject2ById(PlayPage.VIDEO_LIKE_NUMBER).getText();
        String zan = getObject2ById(PlayPage.VIDEO_CHAT_NUMBER).getText();
        makeToast(zan,3);
        watcherBean.setComments(comments);
        watcherBean.setWatch(watcher);
        watcherBean.setZan(zan);
        FollowersAction.clickToChat();
        logger.info(watcherBean.toString());
        return  watcherBean;
    }
    public static BroadcastBean getBroadcastBean(int index) throws UiObjectNotFoundException {
        UiObject2 view = gDevice.findObject(By.res(MePage.BROADCASTS_LIST));
        List<UiObject2> broadcasts = view.findObjects(By.clazz(android.widget.LinearLayout.class).depth(1));
        logger.info("getRandomBroadcastsElement:"+broadcasts.size());
        UiObject2 broadcast = broadcasts.get(index);
        UiObject2 texts = broadcast.findObject(By.res(MePage.BROADCAST_CONTENT));
        List<UiObject2> objects = texts.findObject(By.clazz(android.widget.LinearLayout.class).depth(1)).
                findObjects(By.clazz(android.widget.TextView.class));
        BroadcastBean bb = new BroadcastBean();
        bb.setBroadcast_title(objects.get(0).getText());
        bb.setBroadcast_time(objects.get(1).getText());
        bb.setBroadcast_like(objects.get(2).getText());
        return  bb;
    }
    public void getPlayTime() throws UiObjectNotFoundException, IOException {
        String startTime="";
        String endTime="";
        //click play screen center
        clickById(PlayPage.BROADCAST_VIEW_WATCHER_COUNT,0,100);
        for (int i=0;i<10;i++){
            if (getObjectById(PlayPage.BROADCAST_VIEW_VIDEO_CURRENT_TIME).exists()){
                startTime = getObjectById(PlayPage.BROADCAST_VIEW_VIDEO_CURRENT_TIME).getText();
                break;
            }else{
                clickById(PlayPage.BROADCAST_VIEW_WATCHER_COUNT,0,100);
            }
        }
        for (int i=0;i<10;i++){
            if (getObjectById(PlayPage.BROADCAST_VIEW_VIDEO_END_TIME).exists()){
                endTime = getObjectById(PlayPage.BROADCAST_VIEW_VIDEO_END_TIME).getText();
                break;
            }else{
                clickById(PlayPage.BROADCAST_VIEW_WATCHER_COUNT,0,100);
            }
        }
        makeToast(startTime+"|"+endTime,10);

    }
    //获取直播列表中视频的可操作按钮
    public static UiObject2 getVideoAction(UiObject2 object){
       UiObject2 view= object.getChildren().get(2);
        return view;
    }
    //随机获取一个直播间broadcasts对象的index
    public static int getRandomBroadcastsRoomIndex(){
        waitHasObject(MePage.BROADCASTS_LIST,10000);
        int index = 0;
        boolean exit = true;
        int search = 1;

        while (exit){
            logger.info("================================");
            UiObject2 view = gDevice.findObject(By.res(MePage.BROADCASTS_LIST));
            List<UiObject2> broadcasts = view.findObjects(By.clazz(android.widget.LinearLayout.class).depth(1));
            logger.info("getRandomBroadcastsWithNoRoomIndex size is -"+broadcasts.size());
            for (int j = 1; j <broadcasts.size() ; j++) {
                UiObject2 bsObject = broadcasts.get(j);
                List<UiObject2> lines= bsObject.findObjects(By.clazz(android.widget.LinearLayout.class).depth(1));
                int size = lines.size();
                UiObject2 nav =  lines.get(size-1);
                List<UiObject2> texts = nav.findObjects(By.clazz(android.widget.TextView.class));
                String str = texts.get(2).getText();
                if ("Edit".equals(str)){
                    exit=false;
                    logger.info("find Edit");
                    index=j;
                    break;
                }
            }
            if (exit){
                view.swipe(Direction.UP,0.85f,2000);
                waitTime(3);
            }
            search=search+1;
            if (exit){
                if (search==10){
                    exit=false;
                }
            }
            logger.info("================================");
        }
        logger.info("getRandomBroadcastsWithNoRoomIndex-"+index);
        return  index;
    }
}
