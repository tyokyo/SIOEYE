package cn.action;

import android.graphics.Point;
import android.os.SystemClock;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.hamcrest.Asst;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import ckt.base.VP2;
import cn.page.DiscoverPage;
import cn.page.MePage;

/**
 * Created by jqx on 2017/8/18.
 */
public class NotificationAction extends VP2 {
    private static Logger logger = Logger.getLogger(MeAction.class.getName());
    //获取评论数
    public static int replyCommentSize(){
        waitHasObject(MePage.BROADCAST_CONTENT,50000);
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz("android.widget.LinearLayout"));
        int size = lisCollect.size();
        logger.info("getCommentsSize:"+size);
        return  size;
    }
    //随机获取一个comments对象的index
    public static int getRandomCommentsIndex(){
        List<UiObject2> lisCollect = gDevice.findObjects(By.res(MePage.NOTIFICATION_WATCH_VIDEO));
        int size = lisCollect.size();
        logger.info("getRandomCommentsIndex-size:"+size);
        Random random = new Random();
        int rd = random.nextInt(size);
        logger.info("size-"+size+"random:"+rd);
        return  rd==0?rd:rd-1;
    }
    //随机获取一个Comments对象
    public static UiObject2 getRandomComments(int index){
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz("android.widget.LinearLayout"));
        logger.info("getRandomBroadcasts:"+lisCollect.size());
        UiObject2 comment = lisCollect.get(index);
        return  comment;
    }
    //获取评论视频的父元素
    public static UiObject2 getCommentsParent(){
        UiObject2 commentObject = getObject2ById(MePage.NOTIFICATION_WATCH_VIDEO).getParent().getParent();
        return commentObject;
    }
    //获取输入的视频评论
    public static boolean getVideoComment(String comments) throws UiObjectNotFoundException {
        clickById(MePage.NOTIFICATION_WATCH_VIDEO);
        waitTime(3);
        MeAction.displayNewMessages();
        if(getObjectByTextContains(comments)!=null){
            return true;
        }else{
            return false;
        }
    }
    //获取Sioeye团队消息
    public static boolean loadSioeyeMessage() throws UiObjectNotFoundException{
        String errorStr = "network connect time out, please retry.";
        String errorStr2 = "Web page not available";
        String errorStr3 = "No Internet";
        boolean loadmessage = false;
        clickByText("Sioeye Team");
        clickByClass("android.widget.LinearLayout");
        waitUntilGone("BROADCAST_VIEW_STATUS_IMAGE",10000);
        if(text_exists(errorStr)||text_exists(errorStr2)||text_exists(errorStr3)){
            loadmessage=false;
        }else{
            loadmessage=true;
        }
        return loadmessage;
    }
    //获取Message种类
    public static boolean getMessage() throws UiObjectNotFoundException{
        String atAnchor = "mentioned you in the comments.";
        String commentAnchor = "commented on your video.";
        boolean isExit = false;
        UiObject2 swipe_target = getObject2ById(MePage.NOTIFICATION_WATCH_VIDEO);
        while(text_exists(atAnchor)){
            if(text_exists(atAnchor)){
                swipe_target.swipe(Direction.UP, 0.2f);
                if(text_exists(commentAnchor)){

                }
                isExit = true;
                break;
            }else {
                swipe_target.swipe(Direction.UP, 0.2f);
            }
        }
        return isExit;
    }
}
