package testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ckt.base.VP2;
import ckt.tools.Spoon2;
import page.App;
import page.Me;

/**
 * Created by elon on 2016/10/12.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class ActivityCase extends VP2{
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME);
    }
    public void deleteMyInterests(){
        clickById(Me.ACTIVITY_INTERESTS);
        waitTime(1);
    }
    public void deleteAllMyInterests() throws UiObjectNotFoundException {
        UiCollection uiCollection = new UiCollection(new UiSelector().className("android.widget.FrameLayout"));
        int count = uiCollection.getChildCount(new UiSelector().resourceId(Me.ACTIVITY_INTERESTS));
        for (int i = 0;i<count;i++)
        {
            clickById(Me.ACTIVITY_INTERESTS);
            waitTime(1);
        }
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
    @Test
    public void testAddOneActivity() throws UiObjectNotFoundException {
        clickByText("Me");
        Spoon2.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickByText("Activity");
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
        deleteAllMyInterests();
        String added = addInterested(0);
        Set re = getAllMyInterests();
        Assert.assertTrue("Add My Interests ",re.contains(added));
        clickByText("Done");

        //check
        clickByText("Activity");
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
        Set re2 = getAllMyInterests();
        Assert.assertTrue("Add My Interests ",re2.size()==1);
        Assert.assertTrue("Add My Interests ",re2.contains(added));
        Spoon2.screenshot(gDevice,"My_Interests_"+added);

    }
    @Test
    public void testAddAllActivity() throws UiObjectNotFoundException {
        clickByText("Me");
        Spoon2.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickByText("Activity");
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
        deleteAllMyInterests();
        Set addSets = addAllInterested();
        clickByText("Done");

        //check
        clickByText("Activity");
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
        Set re2 = getAllMyInterests();

        Iterator<String> it = re2.iterator();
        while (it.hasNext()) {
            String str = it.next();
            Assert.assertTrue(str+" Added to My Interests ",addSets.contains(str));
        }
        Assert.assertTrue(" Added to My Interests ",addSets.size()==re2.size());
        Spoon2.screenshot(gDevice,"My_Interests_All");
    }
    @Test
    public void testDelSomeActivity() throws UiObjectNotFoundException {
        clickByText("Me");
        Spoon2.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickByText("Activity");
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
        deleteAllMyInterests();
        addInterested(0);
        String add =addInterested(1);

        deleteMyInterests();;
        clickByText("Done");

        //check
        clickByText("Activity");
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
        Set re2 = getAllMyInterests();

        Assert.assertTrue(" My Interests "+re2,1==re2.size());
        Assert.assertTrue(" My Interests "+re2,re2.contains(add));
        Spoon2.screenshot(gDevice,"My_Interests_All");
    }
    @Test
    public void testDelAllActivity() throws UiObjectNotFoundException {
        clickByText("Me");
        Spoon2.screenshot(gDevice,"Me");
        clickById(Me.ID_USER_EDIT);
        clickByText("Activity");
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
        deleteAllMyInterests();

        clickByText("Done");

        //check
        clickByText("Activity");
        gDevice.wait(Until.gone(By.res(Me.IS_LOCATING)),20000);
        Set re2 = getAllMyInterests();

        Assert.assertTrue(" My Interests is null",0==re2.size());
        Spoon2.screenshot(gDevice,"My_Interests_All");

    }

}
