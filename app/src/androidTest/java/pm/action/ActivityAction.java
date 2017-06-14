package pm.action;

import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.squareup.spoon.Spoon;

import java.util.HashSet;
import java.util.Set;

import ckt.base.VP2;
import pm.page.MePage;

/**
 * Created by elon on 2016/11/8.
 */
public class ActivityAction extends VP2{
    //删除一个interests
    public static void deleteMyInterests(){
        clickById(MePage.ACTIVITY_INTERESTS);
        waitTime(1);
    }
    //删除所有interests
    public static void deleteAllMyInterests() throws UiObjectNotFoundException {
        while(getObjectById(MePage.ACTIVITY_INTERESTS).exists()){
            clickById(MePage.ACTIVITY_INTERESTS);
            waitTime(1);
        }
        Spoon.screenshot("delete_all_My_interests");
    }
    //获取interests
    public static Set<String> getAllMyInterests() throws UiObjectNotFoundException {
        Set<String> reSet = new HashSet<>();
        UiCollection uiCollection = new UiCollection(new UiSelector().className("android.widget.FrameLayout"));
        int count = uiCollection.getChildCount(new UiSelector().resourceId(MePage.ACTIVITY_INTERESTS));
        for (int i = 0;i<count;i++)
        {
            UiObject vg = uiCollection.getChildByInstance(new UiSelector().resourceId(MePage.ACTIVITY_INTERESTS),i);
            reSet.add(vg.getText());
            System.out.println(vg.getText());
        }
        return  reSet;
    }
    //添加一个推荐的爱好
    public static String addInterested(int index) throws UiObjectNotFoundException {
        String addIn = "";
        UiCollection uiCollection = new UiCollection(new UiSelector().className("android.widget.FrameLayout"));
        int count = uiCollection.getChildCount(new UiSelector().resourceId(MePage.ACTIVITY_IN_INTERESTED));
        if (index < count)
        {
            UiObject vg = uiCollection.getChildByInstance(new UiSelector().resourceId(MePage.ACTIVITY_IN_INTERESTED),index);
            addIn =  vg.getText();
            vg.click();
        }
        return addIn;
    }
    //添加推荐列表所有的爱好
    public static Set<String> addAllInterested() throws UiObjectNotFoundException {
        Set<String> reSet = new HashSet<>();
        UiCollection uiCollection = new UiCollection(new UiSelector().className("android.widget.FrameLayout"));
        int count = uiCollection.getChildCount(new UiSelector().resourceId(MePage.ACTIVITY_IN_INTERESTED));
        for (int i = 0; i <count ; i++) {
            UiObject vg = uiCollection.getChildByInstance(new UiSelector().resourceId(MePage.ACTIVITY_IN_INTERESTED),i);
            reSet.add( vg.getText());
            vg.click();
        }
        return reSet;
    }
}
