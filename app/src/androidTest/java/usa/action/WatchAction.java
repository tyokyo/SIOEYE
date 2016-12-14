package usa.action;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import ckt.base.VP2;

/**
 * Created by admin on 2016/11/5.
 */
public class WatchAction extends VP2 {
    //nick name for search
    public static String CORRECT_NICK_NAME = "Hifly US";
    //nick name for search
    public static String WRONG_NICK_NAME = "QWEJHI";
    //nick name for eye id
    public static String CORRECT_SIO_EYE_ID = "hif000";
    //nick name for eye id
    public static String WRONG_SIO_EYE_ID = "QWEJHI";
    //nick name for email
    public static String CORRECT_EMAIL = "hifly001@163.com";
    //nick name for email
    public static String WRONG_EMAIL = "hifly001@178.com";
    //设置图标点赞数量
    public  static  int zan_number1 = 10;
    //设置键盘点赞数量
    public  static  int zan_number2 = 10;
    //设置区域点赞数量
    public  static  int zan_number3 = 10;
    //获取播放界面赞的数量
    public  static  int zan() throws UiObjectNotFoundException {
        UiObject na1 = new UiObject(new UiSelector().resourceId("com.sioeye.sioeyeapp:id/watch_player_portrait_watcher_count"));
        UiObject na = na1.getChild(new UiSelector().className("android.widget.TextView").index(5));
        String naText = na.getText();
        int ab = Integer.parseInt(naText);
        return ab;
    }
    public  static void tovedio() throws UiObjectNotFoundException{
        //随机进入watch列表中的一个视频
        UiObject sObject = new UiObject(new UiSelector().className("android.widget.RelativeLayout"));
        sObject.swipeDown(10);
        sObject.swipeUp((int) (Math.random() * 100));
        waitTime(3);
        //视图中的第二个视频，点击进入
        UiObject df =gDevice.findObject(new UiSelector().className("android.widget.RelativeLayout").index(1));
        UiObject hf = df.getChild(new UiSelector().className("android.widget.RelativeLayout").index(1));
        UiObject xf = hf.getChild(new UiSelector().className("android.widget.RelativeLayout").index(1));
        xf.click();
        waitTime(5);
    }


}
