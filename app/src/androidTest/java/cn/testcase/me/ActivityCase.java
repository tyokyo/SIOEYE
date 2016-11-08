package cn.testcase.me;

import android.graphics.Point;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.ActivityAction;
import cn.action.MeAction;
import cn.action.WatchAction;
import cn.page.App;
import cn.page.MePage;

/**
 * Created by elon on 2016/10/12.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class ActivityCase extends VP2{
    Logger logger = Logger.getLogger(ActivityCase.class.getName());
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        AccountAction.inLogin();
    }
    //验证添加功能  what are you interested in ?
    @Test
    public void testAddOneActivity() throws UiObjectNotFoundException {
        //进入爱好界面
        MeAction.navToActivities();
        //删除当前已经添加的爱好
        ActivityAction.deleteAllMyInterests();
        //添加第一个推荐的爱好
        String added = ActivityAction.addInterested(0);
        //获取我的爱好列表
        Set re = ActivityAction.getAllMyInterests();
        //验证
        Asst.assertTrue("Add My Interests ",re.contains(added));
        //确认
        clickById(MePage.ACTIVITIES_MY_INTERESTS_DONE);
        //check
        clickById(MePage.NAV_EDIT_ACTIVITY);
        waitUntilFind(MePage.IS_LOCATING,3000);
        waitUntilGone(MePage.IS_LOCATING,30000);
        //获取我的爱好列表
        Set re2 = ActivityAction.getAllMyInterests();
        //验证
        Asst.assertTrue("Add My Interests ",re2.size()==1);
        Asst.assertTrue("Add My Interests ",re2.contains(added));
        Spoon.screenshot("add_my_Interests",added);

    }
    //验证添加功能 what are you interested in ? 添加所有推荐的爱好
    @Test
    public void testAddAllActivity() throws UiObjectNotFoundException {
        //进入爱好界面
        MeAction.navToActivities();
        //删除当前已经添加的爱好
        ActivityAction.deleteAllMyInterests();
        //添加推荐列表所有的爱好
        Set addSets = ActivityAction.addAllInterested();
        //确认
        clickById(MePage.ACTIVITIES_MY_INTERESTS_DONE);
        //check
        clickById(MePage.NAV_EDIT_ACTIVITY);
        waitUntilFind(MePage.IS_LOCATING,3000);
        waitUntilGone(MePage.IS_LOCATING,30000);
        //获取当前已经添加的爱好
        Set<String> re2 = ActivityAction.getAllMyInterests();
        //验证
        Iterator<String> it;
        it = re2.iterator();
        while (it.hasNext()) {
            String str = it.next();
            Asst.assertTrue(str+" Added to My Interests ",addSets.contains(str));
        }
        Asst.assertEquals(" Added to My Interests ",addSets.size(),re2.size());
        Spoon.screenshot("My_Interests_All");
    }
    //验证取消功能 what are you interested in ?
    @Test
    public void testDelSomeActivity() throws UiObjectNotFoundException {
        //进入爱好界面
        MeAction.navToActivities();
        //删除所有
        ActivityAction.deleteAllMyInterests();
        //添加第一个
        ActivityAction.addInterested(0);
        //添加第2个
        String add =ActivityAction.addInterested(1);
        //删除第一个
        ActivityAction.deleteMyInterests();
        //确认
        clickById(MePage.ACTIVITIES_MY_INTERESTS_DONE);
        //check
        clickById(MePage.NAV_EDIT_ACTIVITY);
        waitTime(2);
        waitUntilGone(MePage.IS_LOCATING,30);
        //获取爱好内容列表
        Set activeSet = ActivityAction.getAllMyInterests();
        //验证
        Asst.assertEquals("My Interests "+activeSet,1,activeSet.size());
        Asst.assertTrue(" My Interests "+activeSet,activeSet.contains(add));
        Spoon.screenshot(gDevice,"My_Interests_All");
    }
    //验证取消所有功能 what are you interested in ?
    @Test
    public void testDelAllActivity() throws UiObjectNotFoundException {
        //进入爱好界面
        MeAction.navToActivities();
        //输出所有爱好列表
        ActivityAction.deleteAllMyInterests();
        //确认
        clickById(MePage.ACTIVITIES_MY_INTERESTS_DONE);
        //check
        clickById(MePage.NAV_EDIT_ACTIVITY);
        waitUntilFind(MePage.IS_LOCATING,3000);
        waitUntilGone(MePage.IS_LOCATING,30000);
        //后去已经添加的爱好列表
        Set activeSet = ActivityAction.getAllMyInterests();
        //check
        Asst.assertEquals(" My Interests is null",0,activeSet.size());
        Spoon.screenshot(gDevice,"My_Interests_All");

    }
    //My interests
    //添加-String 长度为10
    @Test
    public void test_Add_Input_MyInterests_String_10c() throws IOException, UiObjectNotFoundException {
        //点赞图标的Point
        Point point = MeAction.getSearchLocation();
        //go to activities
        MeAction.navToActivities();
        //删除所有的interests
        ActivityAction.deleteAllMyInterests();
        Set myInterests_before_add = ActivityAction.getAllMyInterests();
        //生成随机字符
        String input = getRandomString(10);
        //添加我的爱好
        clickById(MePage.ACTIVITIES_MY_INTERESTS_ADD);
        clickById(MePage.ACTIVITIES_MY_INTERESTS_USER_INPUT);
        shellInputText(input);
        //添加
        clickByPoint(point);
        //提交
        clickById(MePage.ACTIVITIES_MY_INTERESTS_DONE);
        //重新open app 验证是否添加成功
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        //go to 爱好
        MeAction.navToActivities();
        Set myInterests_after_add = ActivityAction.getAllMyInterests();
        UiObject interests_add = getUiObjectByText(input);
        Asst.assertEquals("add success",true,interests_add.exists());
        Spoon.screenshot("add_my_interests",input);
    }
    //My interests
    //添加-String 长度为40  MAX=16
    @Test
    public void test_Add_Input_MyInterests_String_40c() throws IOException, UiObjectNotFoundException {
        //点赞图标的Point
        Point point = MeAction.getSearchLocation();
        //go to activities
        MeAction.navToActivities();
        //delete all interest
        ActivityAction.deleteAllMyInterests();
        Set myInterests_before_add = ActivityAction.getAllMyInterests();
        //随机的爱好-长度40
        String input = getRandomString(40);
        clickById(MePage.ACTIVITIES_MY_INTERESTS_ADD);
        clickById(MePage.ACTIVITIES_MY_INTERESTS_USER_INPUT);
        shellInputText(input);
        //添加
        clickByPoint(point);
        clickById(MePage.ACTIVITIES_MY_INTERESTS_DONE);
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        MeAction.navToActivities();
        //最多只能接受16个字符的长度的输入
        input=input.substring(0,16);
        Set myInterests_after_add = ActivityAction.getAllMyInterests();
        UiObject interests_add = getUiObjectByText(input);
        Asst.assertEquals("add success",true,interests_add.exists());
        Spoon.screenshot("add_my_interests",input);
    }

    //My interests
    //最多可以添加多少个My interests  10个?
    @Test
    public void test_Add_Input_MyInterests_10() throws IOException, UiObjectNotFoundException {
        //点赞图标的Point
        Point point = MeAction.getSearchLocation();
        MeAction.navToActivities();
        //delete all
        ActivityAction.deleteAllMyInterests();
        String content="";
        //添加10个 爱好
        int total_add = 10;
        clickById(MePage.ACTIVITIES_MY_INTERESTS_ADD);
        for (int i = 1; i <=total_add ; i++) {
            String input = getRandomString(3);
            clickById(MePage.ACTIVITIES_MY_INTERESTS_USER_INPUT);
            shellInputText(input);
            content=content+input+",";
            //添加
            clickByPoint(point);
            logger.info("DEBUG-"+(i));
        }
        //确认
        clickById(MePage.ACTIVITIES_MY_INTERESTS_DONE);
        //验证增加的爱好
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        MeAction.navToActivities();
        Set myInterests_after_add = ActivityAction.getAllMyInterests();
        Asst.assertEquals("add success",total_add,myInterests_after_add.size());
        Spoon.screenshot("add_my_interests","add interests "+myInterests_after_add.size());
    }
    //添加interests = a,b,c
    @Test
    public void test_ErrorSymbol() throws IOException, UiObjectNotFoundException {
        //点赞图标的Point
        Point p = MeAction.getSearchLocation();
        //go to activities
        MeAction.navToActivities();
        ActivityAction.deleteAllMyInterests();
        Set myInterests_before_add = ActivityAction.getAllMyInterests();
        String input = "a,b,c";
        clickById(MePage.ACTIVITIES_MY_INTERESTS_ADD);
        clickById(MePage.ACTIVITIES_MY_INTERESTS_USER_INPUT);
        shellInputText(input);
        //添加
        gDevice.click(p.x,p.y);
        clickById(MePage.ACTIVITIES_MY_INTERESTS_DONE);
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        MeAction.navToActivities();
        //获取爱好内容
        Set myInterests_after_add =ActivityAction.getAllMyInterests();
        Asst.assertEquals("add one interest",1,myInterests_after_add.size());
        Spoon.screenshot("add_my_interests",input);
    }
    //添加interests特殊字符
    @Test
    public void test_Symbol() throws IOException, UiObjectNotFoundException {
        //点赞图标的Point
        Point point = MeAction.getSearchLocation();
        MeAction.navToActivities();
        ActivityAction.deleteAllMyInterests();
        Set myInterests_before_add = ActivityAction.getAllMyInterests();
        //随机字符
        String input = getRandomSymbol(10);
        clickById(MePage.ACTIVITIES_MY_INTERESTS_ADD);
        clickById(MePage.ACTIVITIES_MY_INTERESTS_USER_INPUT);
        shellInputText(input);
        //添加
        clickByPoint(point);
        clickById(MePage.ACTIVITIES_MY_INTERESTS_DONE);
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        MeAction.navToActivities();
        //验证点
        Set myInterests_after_add = ActivityAction.getAllMyInterests();
        Asst.assertEquals("add one interest",1,myInterests_after_add.size());
        Spoon.screenshot("add_my_interests",input);
    }
    //添加interests特殊字符
    @Test
    public void test_MyInterests_back() throws IOException, UiObjectNotFoundException {
        //点赞图标的Point
        Point p = MeAction.getSearchLocation();
        MeAction.navToActivities();
        ActivityAction.deleteAllMyInterests();
        clickById(MePage.ACTIVITIES_MY_INTERESTS_DONE);
        //重新进入activities
        clickById(MePage.NAV_EDIT_ACTIVITY);
        Set myInterests_before_add = ActivityAction.getAllMyInterests();
        String input = getRandomSymbol(10);
        clickById(MePage.ACTIVITIES_MY_INTERESTS_ADD);
        clickById(MePage.ACTIVITIES_MY_INTERESTS_USER_INPUT);
        shellInputText(input);
        //添加
        clickByPoint(p);
        clickById(MePage.ACTIVITIES__BACK);
        //check
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        MeAction.navToActivities();
        Set myInterests_after_add = ActivityAction.getAllMyInterests();
        Asst.assertEquals("add with forgive saving",0,myInterests_after_add.size());
        Spoon.screenshot("no_interest",input);
    }

    //添加相同的interests
    @Test
    public void test_add_SameInterest() throws IOException, UiObjectNotFoundException {
        //点赞图标的Point
        Point point = MeAction.getSearchLocation();
        MeAction.navToActivities();
        //delete all interests
        ActivityAction.deleteAllMyInterests();
        Set myInterests_before_add = ActivityAction.getAllMyInterests();
        //随机输入
        String input = getRandomString(10);
        //add a interest
        clickById(MePage.ACTIVITIES_MY_INTERESTS_ADD);
        clickById(MePage.ACTIVITIES_MY_INTERESTS_USER_INPUT);
        shellInputText(input);
        //添加
        clickByPoint(point);
        //get interetsts content
        Set myInterests_after_add_1 = ActivityAction.getAllMyInterests();
        //add another with the same interest name
        clickById(MePage.ACTIVITIES_MY_INTERESTS_USER_INPUT);
        shellInputText(input);
        //添加
        clickByPoint(point);
        //check
        Set myInterests_after_add_2 = ActivityAction.getAllMyInterests();
        Asst.assertEquals("add with the same name",myInterests_after_add_1.size(),myInterests_after_add_2.size());
        Spoon.screenshot("no_interest",input);
    }
}
