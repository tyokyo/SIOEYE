package testcase.me;

import android.graphics.Point;
import android.support.annotation.UiThread;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import action.Nav;
import bean.FollowingBean;
import ckt.base.VP2;
import ckt.base.VP3;
import ckt.tools.Spoon2;
import page.App;
import page.Me;
import page.Watch;

/**
 * Created by elon on 2016/10/18.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class FollowingCase extends VP2{
    private Point p;
    @Before
    public  void setup() throws UiObjectNotFoundException, IOException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME);
        p = Nav.getSearchLocation();
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME);
    }

    //随机选择一个Following 对象
    private FollowingBean randomFollowing() throws IOException, UiObjectNotFoundException {
        FollowingBean followingBean = new FollowingBean();
        UiObject2 listView = gDevice.findObject(By.res(Me.FOLLOWERING_VIEW));
        waitTime(2);
        List<UiObject2> lisCollect = gDevice.findObjects(By.res(Me.FOLLOWERING_AVATAR));
        int size = lisCollect.size();
        Random random = new Random();
        int rd = random.nextInt(size);
        makeToast(rd+"",4);
        UiObject2 linearLayout = lisCollect.get(rd);
        followingBean.setAvatar(linearLayout);
        linearLayout.click();
        String name = getUiObjectById(Me.FOLLOWERING_USERNAME).getText();
        makeToast(name,4);
        followingBean.setName(name);
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME);

        return  followingBean;
    }
    //当前界面所有的Following bean
    private List<FollowingBean> currentFollowing() throws IOException {
        List<FollowingBean> followingBeans =new ArrayList<>();
        UiObject2 listView = gDevice.findObject(By.res(Me.FOLLOWERING_VIEW));
        waitTime(2);
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz(LinearLayout.class));
        for (UiObject2 linearLayout:lisCollect) {
            FollowingBean followingBean = new FollowingBean();
            UiObject2 avatar = linearLayout.findObject(By.clazz(ImageView.class));
            List<UiObject2> textViews = linearLayout.findObjects(By.clazz(TextView.class));
            followingBean.setAvatar(avatar);
            String name = textViews.get(0).getText();
            makeToast(name,4);
            followingBean.setName(name);
            followingBeans.add(followingBean);
        }
        return  followingBeans;
    }
    /**
     *搜索指定sioeye id 的用户
     * @param sioEyeId
     */
    public void findUser(String sioEyeId) throws IOException, UiObjectNotFoundException {
        clickByText("Watch");
        clickById(Watch.WATCH_SEARCH_BTN);
        gDevice.wait(Until.findObject(By.res(Watch.WATCH_SEARCH_TRENDING_TITLE)),40000);
        if (!getUiObjectById(Watch.WATCH_SEARCH_TRENDING_LIST).exists()){
            clickById(Watch.WATCH_SEARCH_FILTER_INPUT);
            waitTime(2);
            gDevice.click(p.x,p.y);
            gDevice.wait(Until.findObject(By.res(Watch.WATCH_SEARCH_TRENDING_TITLE)),40000);
        }
        Spoon2.screenshot(gDevice,"trending_list");
        clickById(Watch.WATCH_SEARCH_FILTER_INPUT);
        shellInputText(sioEyeId);
        gDevice.click(p.x,p.y);
        gDevice.wait(Until.gone(By.res(Watch.WATCH_SEARCH_DATA_LOADING)),60000);
        Spoon2.screenshot(gDevice,"search_list_"+sioEyeId);
    }
    /**
     *添加所有trending用户
     * @param
     */
    public void addAllTrendingUser() throws UiObjectNotFoundException, IOException {
        while (true){
            boolean user_follow = getObjectById(Watch.WATCH_SEARCH_USER_FOLLOW).exists();
            if (user_follow){
                clickById(Watch.WATCH_SEARCH_USER_FOLLOW);
                getObjectById(Watch.WATCH_SEARCH_TRENDING_LIST).swipeUp(2);
                waitTime(5);
                makeToast("add one user",2);
            }else {
                break;
            }
        }
    }
    /**
     *添加所有trending用户
     * @param
     */
    public void deleteFollowingUser(FollowingBean followingBean) throws UiObjectNotFoundException, IOException {
        //Nav.navToFollowing();
        followingBean.getAvatar().click();
        clickById(Me.FOLLOWERING_DELETE);
        gDevice.pressBack();
    }
    //验证-删除一个关注的用户
    @Test
    public void testA1DeleteFollowingUser() throws IOException, UiObjectNotFoundException {
        Nav.navToFollowing();
        //取消关注第一个
        clickById(Me.FOLLOWERING_AVATAR);
        String name = getUiObjectById(Me.FOLLOWERING_USERNAME).getText();
        //取消关注按钮
        clickById(Me.FOLLOWERING_DELETE);
        gDevice.pressBack();
        if (getUiObjectByText(name).exists()){
            Spoon2.screenshot(gDevice,"delete_"+name);
            Assert.fail("delete failed");
        }
    }
    //验证-添加trending关注
    @Test
    public void testA2AddTrendingUserFromAvatar() throws IOException, UiObjectNotFoundException {
        Nav.navToWatchSearch();
        //取消关注第一个
        clickById(Watch.WATCH_USER_AVATAR);
        String name = getUiObjectById(Watch.WATCH_USER_MINI_NAME).getText();
        //关注按钮
        clickById(Watch.WATCH_USER_MINI_FOLLOW);
        gDevice.pressBack();
        getObjectById(Watch.WATCH_SEARCH_TRENDING_LIST).swipeUp(2);
        waitTime(3);
        if (getUiObjectByText(name).exists()){
            Spoon2.screenshot(gDevice,"add_"+name);
            Assert.fail("add failed");
        }
    }
    //验证-添加trending关注
    @Test
    public void testA3AddTrendingUserFromUserFollow() throws IOException, UiObjectNotFoundException {
        Nav.navToWatchSearch();
        //取消关注第一个
        clickById(Watch.WATCH_USER_AVATAR);
        String name = getUiObjectById(Watch.WATCH_USER_MINI_NAME).getText();
        //关注按钮
        clickById(Watch.WATCH_USER_MINI_FOLLOW);
        gDevice.pressBack();
        getObjectById(Watch.WATCH_SEARCH_TRENDING_LIST).swipeUp(2);
        waitTime(3);
        if (getUiObjectByText(name).exists()){
            Spoon2.screenshot(gDevice,"add_"+name);
            Assert.fail("add failed");
        }
    }
    //验证-搜索功能(sioeye id)
    @Test
    public void testFollowingSearchSioEyeID() throws IOException, UiObjectNotFoundException {
        String account_id="ID4Felix";
        findUser(account_id);
        int follows=getObjectsById(Watch.WATCH_SEARCH_USER_FOLLOW).size();
        makeToast("search "+follows,4);
        Assert.assertEquals("search ID4Felix",follows,1);
        clickById(Watch.WATCH_USER_AVATAR);
        boolean isFind=getUiObjectByText("@"+account_id).exists();
        Assert.assertTrue("account",isFind);
    }
    //验证-搜索功能(sioeye id)
    @Test
    public void testFollowingSearchName() throws IOException, UiObjectNotFoundException {
        String name="River";
        findUser(name);
        int follows=getObjectsById(Watch.WATCH_SEARCH_USER_FOLLOW).size();
        makeToast("search "+follows,4);
        //Assert.assertEquals("search name "+name,follows,1);
        clickById(Watch.WATCH_USER_AVATAR);
        String userName=getObjectById(Watch.WATCH_USER_MINI_NAME).getText();
        Assert.assertEquals("account",userName,name);
    }
}


