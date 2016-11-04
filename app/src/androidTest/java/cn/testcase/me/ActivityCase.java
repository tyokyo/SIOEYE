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

import action.MeAction;
import action.Nav;
import ckt.base.VP2;
import usa.page.App;
import usa.page.Me;

/**
 * Created by elon on 2016/10/12.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class ActivityCase extends VP2{
    Logger logger = Logger.getLogger(ActivityCase.class.getName());
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }

    public void deleteMyInterests(){
        clickById(Me.ACTIVITY_INTERESTS);
        waitTime(1);
    }

    public void deleteAllMyInterests() throws UiObjectNotFoundException {
        while(getObjectById(Me.ACTIVITY_INTERESTS).exists()){
            clickById(Me.ACTIVITY_INTERESTS);
            waitTime(1);
        }
        /*UiCollection uiCollection = new UiCollection(new UiSelector().className("android.widget.FrameLayout"));
        int count = uiCollection.getChildCount(new UiSelector().resourceId(Me.ACTIVITY_INTERESTS));
        for (int i = 0;i<count;i++)
        {
            clickById(Me.ACTIVITY_INTERESTS);
            waitTime(1);
        }*/
    }
    public Set<String> getAllMyInterests() throws UiObjectNotFoundException {
        Set<String> reSet = new HashSet<>();
        UiCollection uiCollection = new UiCollection(new UiSelector().className("android.widget.FrameLayout"));
        int count = uiCollection.getChildCount(new UiSelector().resourceId(Me.ACTIVITY_INTERESTS));
        for (int i = 0;i<count;i++)
        {
            UiObject vg = uiCollection.getChildByInstance(new UiSelector().resourceId(Me.ACTIVITY_INTERESTS),i);
            reSet.add(vg.getText());
            System.out.println(vg.getText());
        }
        return  reSet;
    }
    public String addInterested(int index) throws UiObjectNotFoundException {
        String addIn = "";
        UiCollection uiCollection = new UiCollection(new UiSelector().className("android.widget.FrameLayout"));
        int count = uiCollection.getChildCount(new UiSelector().resourceId(Me.ACTIVITY_IN_INTERESTED));
        if (index < count)
        {
            UiObject vg = uiCollection.getChildByInstance(new UiSelector().resourceId(Me.ACTIVITY_IN_INTERESTED),index);
            addIn =  vg.getText();
            vg.click();
        }
        return addIn;
    }
    public  Set<String> addAllInterested() throws UiObjectNotFoundException {
        Set<String> reSet = new HashSet<>();
        UiCollection uiCollection = new UiCollection(new UiSelector().className("android.widget.FrameLayout"));
        int count = uiCollection.getChildCount(new UiSelector().resourceId(Me.ACTIVITY_IN_INTERESTED));
        for (int i = 0; i <count ; i++) {
            UiObject vg = uiCollection.getChildByInstance(new UiSelector().resourceId(Me.ACTIVITY_IN_INTERESTED),i);
            reSet.add( vg.getText());
            vg.click();
        }
        return reSet;
    }
    //验证添加功能  what are you interested in ?
    @Test
    public void testAddOneActivity() throws UiObjectNotFoundException {
        clickByText("Me");
        Spoon.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickByText("Activity");
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
        deleteAllMyInterests();
        String added = addInterested(0);
        Set re = getAllMyInterests();
        Asst.assertTrue("Add My Interests ",re.contains(added));
        clickByText("Done");

        //check
        clickByText("Activity");
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
        Set re2 = getAllMyInterests();
        Asst.assertTrue("Add My Interests ",re2.size()==1);
        Asst.assertTrue("Add My Interests ",re2.contains(added));
        Spoon.screenshot(gDevice,"My_Interests_"+added);

    }
    //验证添加功能 what are you interested in ?
    @Test
    public void testAddAllActivity() throws UiObjectNotFoundException {
        clickByText("Me");
        Spoon.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickByText("Activity");
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
        deleteAllMyInterests();
        Set addSets = addAllInterested();
        clickByText("Done");

        //check
        clickByText("Activity");
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
        Set<String> re2 = getAllMyInterests();

        Iterator<String> it;
        it = re2.iterator();
        while (it.hasNext()) {
            String str = it.next();
            Asst.assertTrue(str+" Added to My Interests ",addSets.contains(str));
        }
        Asst.assertTrue(" Added to My Interests ",addSets.size()==re2.size());
        Spoon.screenshot(gDevice,"My_Interests_All");
    }
    //验证取消功能 what are you interested in ?
    @Test
    public void testDelSomeActivity() throws UiObjectNotFoundException {
        clickByText("Me");
        Spoon.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickByText("Activity");
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
        deleteAllMyInterests();
        addInterested(0);
        String add =addInterested(1);

        deleteMyInterests();
        clickByText("Done");

        //check
        clickByText("Activity");
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
        Set re2 = getAllMyInterests();

        Asst.assertTrue(" My Interests "+re2,1==re2.size());
        Asst.assertTrue(" My Interests "+re2,re2.contains(add));
        Spoon.screenshot(gDevice,"My_Interests_All");
    }
    //验证取消所有功能 what are you interested in ?
    @Test
    public void testDelAllActivity() throws UiObjectNotFoundException {
        clickByText("Me");
        Spoon.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickByText("Activity");
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
        deleteAllMyInterests();

        clickByText("Done");

        //check
        clickByText("Activity");
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
        Set re2 = getAllMyInterests();

        Asst.assertTrue(" My Interests is null",0==re2.size());
        Spoon.screenshot(gDevice,"My_Interests_All");

    }
    //My interests
    //添加-String 长度为10
    @Test
    public void test_Add_Input_MyInterests_String_10c() throws IOException, UiObjectNotFoundException {
        Point p = Nav.getSearchLocation();
        MeAction.navToActivities();
        deleteAllMyInterests();
        Set myInterests_before_add = getAllMyInterests();
        String input = getRandomString(10);
        clickById(Me.ACTIVITIES_MY_INTERESTS_ADD);
        clickById(Me.ACTIVITIES_MY_INTERESTS_USER_INPUT);
        shellInputText(input);
        //添加
        gDevice.click(p.x,p.y);
        clickById(Me.ACTIVITIES_MY_INTERESTS_DONE);
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToActivities();

        Set myInterests_after_add = getAllMyInterests();
        UiObject interests_add = getUiObjectByText(input);
        Asst.assertEquals("add success",true,interests_add.exists());
        Spoon.screenshot("add_my_interests",input);
    }
    //My interests
    //添加-String 长度为40  MAX=16
    @Test
    public void test_Add_Input_MyInterests_String_40c() throws IOException, UiObjectNotFoundException {
        Point p = Nav.getSearchLocation();
        MeAction.navToActivities();
        deleteAllMyInterests();
        Set myInterests_before_add = getAllMyInterests();
        String input = getRandomString(40);
        clickById(Me.ACTIVITIES_MY_INTERESTS_ADD);
        clickById(Me.ACTIVITIES_MY_INTERESTS_USER_INPUT);
        shellInputText(input);
        //添加
        gDevice.click(p.x,p.y);
        clickById(Me.ACTIVITIES_MY_INTERESTS_DONE);
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToActivities();

        input=input.substring(0,16);
        Set myInterests_after_add = getAllMyInterests();
        UiObject interests_add = getUiObjectByText(input);
        Asst.assertEquals("add success",true,interests_add.exists());
        Spoon.screenshot("add_my_interests",input);
    }

    //My interests
    //最多可以添加多少个My interests  10个?
    @Test
    public void test_Add_Input_MyInterests_10() throws IOException, UiObjectNotFoundException {
        Point p = Nav.getSearchLocation();
        MeAction.navToActivities();
        deleteAllMyInterests();
        String content="";
        int total_add = 10;
        clickById(Me.ACTIVITIES_MY_INTERESTS_ADD);
        for (int i = 1; i <=total_add ; i++) {
            String input = getRandomString(3);
            clickById(Me.ACTIVITIES_MY_INTERESTS_USER_INPUT);
            shellInputText(input);
            content=content+input+",";
            //添加
            gDevice.click(p.x,p.y);
            logger.info("DEBUG-"+(i));
        }
        clickById(Me.ACTIVITIES_MY_INTERESTS_DONE);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToActivities();
        Set myInterests_after_add = getAllMyInterests();
        Asst.assertEquals("add success",total_add,myInterests_after_add.size());
        Spoon.screenshot("add_my_interests","add interests "+myInterests_after_add.size());
    }
    //添加interests = a,b,c
    @Test
    public void test_ErrorSymbol() throws IOException, UiObjectNotFoundException {
        Point p = Nav.getSearchLocation();
        MeAction.navToActivities();
        deleteAllMyInterests();
        Set myInterests_before_add = getAllMyInterests();
        String input = "a,b,c";
        clickById(Me.ACTIVITIES_MY_INTERESTS_ADD);
        clickById(Me.ACTIVITIES_MY_INTERESTS_USER_INPUT);
        shellInputText(input);
        //添加
        gDevice.click(p.x,p.y);
        clickById(Me.ACTIVITIES_MY_INTERESTS_DONE);
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToActivities();

        Set myInterests_after_add = getAllMyInterests();
        Asst.assertEquals("add one interest",1,myInterests_after_add.size());
        Spoon.screenshot("add_my_interests",input);
    }
    //添加interests特殊字符
    @Test
    public void test_Symbol() throws IOException, UiObjectNotFoundException {
        Point p = Nav.getSearchLocation();
        MeAction.navToActivities();
        deleteAllMyInterests();
        Set myInterests_before_add = getAllMyInterests();
        String input = getRandomSymbol(10);
        clickById(Me.ACTIVITIES_MY_INTERESTS_ADD);
        clickById(Me.ACTIVITIES_MY_INTERESTS_USER_INPUT);
        shellInputText(input);
        //添加
        gDevice.click(p.x,p.y);
        clickById(Me.ACTIVITIES_MY_INTERESTS_DONE);
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToActivities();

        Set myInterests_after_add = getAllMyInterests();
        Asst.assertEquals("add one interest",1,myInterests_after_add.size());
        Spoon.screenshot("add_my_interests",input);
    }
    //添加interests特殊字符
    @Test
    public void test_MyInterests_back() throws IOException, UiObjectNotFoundException {
        Point p = Nav.getSearchLocation();
        MeAction.navToActivities();
        deleteAllMyInterests();
        Set myInterests_before_add = getAllMyInterests();
        String input = getRandomSymbol(10);
        clickById(Me.ACTIVITIES_MY_INTERESTS_ADD);
        clickById(Me.ACTIVITIES_MY_INTERESTS_USER_INPUT);
        shellInputText(input);
        //添加
        gDevice.click(p.x,p.y);
        clickById(Me.ACTIVITIES__BACK);
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        MeAction.navToActivities();

        Set myInterests_after_add = getAllMyInterests();
        Asst.assertEquals("add with forgive saving",0,myInterests_after_add.size());
        Spoon.screenshot("no_interest",input);
    }

    //添加相同的interests
    @Test
    public void test_add_SameInterest() throws IOException, UiObjectNotFoundException {
        Point p = Nav.getSearchLocation();
        MeAction.navToActivities();
        deleteAllMyInterests();

        Set myInterests_before_add = getAllMyInterests();

        String input = getRandomString(10);

        //add a interest
        clickById(Me.ACTIVITIES_MY_INTERESTS_ADD);
        clickById(Me.ACTIVITIES_MY_INTERESTS_USER_INPUT);
        shellInputText(input);
        //添加
        gDevice.click(p.x,p.y);
        Set myInterests_after_add_1 = getAllMyInterests();
        //add another with the same interest name

        clickById(Me.ACTIVITIES_MY_INTERESTS_USER_INPUT);
        shellInputText(input);
        //添加
        gDevice.click(p.x,p.y);

        Set myInterests_after_add_2 = getAllMyInterests();
        Asst.assertEquals("add with the same name",myInterests_after_add_1.size(),myInterests_after_add_2.size());
        Spoon.screenshot("no_interest",input);
    }
}
