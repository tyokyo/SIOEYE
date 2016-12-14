package cn.testcase.watch;

import android.graphics.Rect;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import com.squareup.spoon.Spoon;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import cn.page.App;
import usa.testcase.me.HelpCase;



/**
 * Created by admin on 2016/10/28.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class MoreagreeCase extends VP2 {
    Logger logger = Logger.getLogger(HelpCase.class.getName());
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
    }
    @Test
    public void testD1() throws UiObjectNotFoundException {
//        clickByText("text");
//        getObjectById("id");
//        clickById("id");
//        Spoon.screenshot("picture_name", "这里的文字会绘制文字到图片上");
//        Spoon.save(context, "your file name");
        waitTime(3);
        UiObject gx = new UiObject(new UiSelector().className("android.widget.TextView").text("请更新到最新版本"));
        if(gx.exists() == true)
        {
            clickByText("取消");
        }
        else
        waitTime(5);
        clickById("cn.sioeye.sioeyeapp:id/main_tab_live");

        Spoon.screenshot("moreagree","进入关注界面");
        UiObject sObject = new UiObject(new UiSelector().className("android.widget.RelativeLayout"));
        sObject.swipeDown(10);

        waitTime(10);
        logger.info("-----------------------------------------");
//        UiObject ss = new UiObject(new UiSelector().className("android.widget.RelativeLayout").index(0));
//        UiObject sw = ss.getChild(new UiSelector().className("android.widget.LinearLayout").index(0));
//        UiObject sx = sw.getChild(new UiSelector().className("android.widget.LinearLayout").index(0));
//        UiObject sd = sx.getChild(new UiSelector().className("android.widget.LinearLayout").index(1));
//        UiObject sa = sd.getChild(new UiSelector().className("android.widget.TextView").index(1));
//        String sl = sa.getText();
//        logger.info("-------"+sl);



        UiObject hf = new UiObject(new UiSelector().className("android.widget.RelativeLayout").index(1));
        Rect r =hf.getBounds();
        int x0 = r.left;
        int y0 = r.top;
        int x1 = r.right;
        int y1 = r.bottom;
        logger.info(x0+"*"+x1+"*"+y0+"*"+y1+"*");
        //clickByPonit((x1-x0)/2,(y1-y0)/2);
        clickByPonit((y1-y0)/2,(x1-x0)/2);
        waitTime(5);
        //随机区域点击


//        int i;
//        for (i=0;i<3;i++)
//        {
//            clickById("cn.sioeye.sioeyeapp:id/watch_player_portrait_like");
//            waitTime(1);
//        }
//
//        clickByText("说点什么吧");
//        int j;
//        for (j=0;j<3;j++)
//        {
//            clickById("cn.sioeye.sioeyeapp:id/float_like");
//        }
//        waitTime(3);
        clickByText("主播");
        waitTime(3);
        UiObject xg = new UiObject(new UiSelector().resourceId("cn.sioeye.sioeyeapp:id/tv_chat_number"));
        String gh = xg.getText();
        logger.info("====="+gh);




        //clickById("android.view.View");
        //clickById("com.sioeye.sioeyeapp:id/watch_player_portrait_like");
//
//
//
//

    }
    }
