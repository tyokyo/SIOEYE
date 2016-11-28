package usa.testcase.discover;

import android.graphics.Point;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.squareup.spoon.Spoon;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import ckt.base.VP2;
import usa.action.AccountAction;
import usa.action.DiscoverAction;
import usa.action.Nav;
import usa.action.WatchAction;
import usa.page.Account;
import usa.page.App;
import usa.page.Discover;
import usa.page.Me;
import usa.page.Watch;

/**
 * Created by jianbin.zhong on 2016/11/7.
 */
@RunWith(AndroidJUnit4.class)
public class SearchUserCase extends VP2 {
    //usa online test nickname:Beanbryant   sioeyeid:bryantBean
    //usa offline test nickname:Beanbryant   sioeyeid:bryantBean
    //此类有用例未完成，且有的用例有问题
    @Before
    public void setUp(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    //点击搜索按钮,跳转到搜索界面
    @Test
    public void test_NavToSearch() throws UiObjectNotFoundException, IOException {
        DiscoverAction.navToSearch();
        waitTime(1);
        String expect = getObjectById(Discover.ID_SEARCH_FILTER_INPUT).getText();
        if (!expect.equals("Search")){
            makeToast("跳转失败",4);
            Spoon.screenshot(expect+"page");
            Assert.fail("跳转失败"+expect);
        }
    }
    //输入框可以输入且输入的与显示的相等
    @Test
    public void test_Input() throws UiObjectNotFoundException, IOException {
        DiscoverAction.navToSearch();
        waitTime(1);
        String expect = getRandomString(5);
        getObjectById(Discover.ID_SEARCH_FILTER_INPUT).setText(expect);
        waitTime(1);
        String input = getObjectById(Discover.ID_SEARCH_FILTER_INPUT).getText();
        if (!expect.equals(input)){
            makeToast("输入失败",4);
            Spoon.screenshot(expect+"not equal"+input);
            Assert.fail("输入与输入框的不相等");
        }
    }
    //准确搜索不存在的数据包括(Username、video、e-mail)
    @Test
    public void test_NoUser() throws UiObjectNotFoundException, IOException {
        DiscoverAction.navToSearch();
        waitTime(1);
      //String search_content = WatchAction.WRONG_NICK_NAME;
        String data = "《》";
        setText(Discover.ID_SEARCH_FILTER_INPUT,data);
        waitTime(2);
        UiObject people = gDevice.findObject(new UiSelector().resourceId(Watch.WATCH_SEARCH_TYPE_CONTACTS));
        UiObject noresult = getObjectById("com.sioeye.sioeyeapp:id/text_data_load_fail");
        if (people.exists() && noresult.exists()){
            makeToast(data+"存在",4);
            Spoon.screenshot("测试失败"+data);
            Assert.fail(data+"存在"+data);
        }
    }
    //准确搜索username，关注，该用户出现在好友列表中
    //search界面的关注
    @Test
    public void test_FollowUser() throws UiObjectNotFoundException, IOException {
        if (!AccountAction.judgelogin()){
            AccountAction.login("1234554321@qq.com","1234554321");
        }
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        DiscoverAction.navToSearch();
        waitTime(1);
        String username = "Beanbryant";
        getObjectById(Discover.ID_SEARCH_FILTER_INPUT).setText(username);
        waitTime(2);
        clickById("com.sioeye.sioeyeapp:id/user_follow");
        gDevice.pressBack();
        clickById(Me.ID_MAIN_TAB_ME);
        clickByText("Following");
        UiObject actual = scrollAndGetUIObject(username);
        if (actual == null){
            makeToast("未找到用户",3);
            Spoon.screenshot("未找到用户"+username);
            Assert.fail("关注用户"+username+"失败");
        }else {
            //关注成功的username取消掉，以便下次测试
            scrollAndGetUIObject(username).click();
            clickById(Me.FOLLOWERING_DELETE);
        }
        AccountAction.logout();
    }
    //取消关注的好友
    @Test
    public void test_CancelFollowerUser() throws UiObjectNotFoundException, IOException {
        //关注用户
        if (!AccountAction.judgelogin()){
            AccountAction.login("1234554321@qq.com","1234554321");
        }
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        DiscoverAction.navToSearch();
        waitTime(1);
        String username = "Beanbryant";
        getObjectById(Discover.ID_SEARCH_FILTER_INPUT).setText(username);
        waitTime(2);
        clickById("com.sioeye.sioeyeapp:id/user_follow");
        gDevice.pressBack();
        clickById(Me.ID_MAIN_TAB_ME);
        clickByText("Following");
        scrollAndGetUIObject(username).click();
        clickById(Me.FOLLOWERING_DELETE);
        gDevice.pressBack();
        if (scrollAndGetUIObject(username)!=null){
            makeToast("取消关注失败",3);
            Spoon.screenshot("取消关注用户"+username);
            Assert.fail("取消关注失败"+username);
        }
        AccountAction.logout();
    }
    //回退到Discover页面
    @Test
    public void test_BackToDiscover() throws UiObjectNotFoundException, IOException {
        DiscoverAction.navToSearch();
        waitTime(1);
        UiObject cancel = gDevice.findObject(new UiSelector().className("android.widget.ImageView"));
        cancel.click();
        UiObject discover = getObjectById(Discover.ID_MAIN_TAB_DISCOVER);
        if (discover == null){
            makeToast("other page",3);
            Spoon.screenshot("page");
            Assert.fail("Cancel and back to discover error");
        }
    }
    //准确搜索存在的用户的Nick Name = Beanbryant
    @Test
    public void test_FullUser() throws UiObjectNotFoundException, IOException {
        DiscoverAction.navToSearch();
        waitTime(3);
        String full_Nickname = "Beanbryant";
        setText(Discover.ID_SEARCH_FILTER_INPUT,full_Nickname);
        waitTime(3);
        getObjectById(Watch.WATCH_SEARCH_RESULT_USER_AVATAR).click();
        getObjectById(Watch.WATCH_USER_MINI_NAME).getText();
        if(!getObjectById(Watch.WATCH_USER_MINI_NAME).getText().equals(full_Nickname)){
            Spoon.screenshot(gDevice,full_Nickname);
            makeToast(full_Nickname,4);
            Assert.fail(full_Nickname+"can't found");
        }
    }
    //搜索匹配Nick Name的用户
    //usa online test nickname:Beanbryant   sioeyeid:bryantBean
    //usa offline test nickname:Beanbryant   sioeyeid:bryantBean
    @Test
    public void test_ContainNickname() throws UiObjectNotFoundException, IOException {
        DiscoverAction.navToSearch();
        waitTime(1);
        String full_Nickname = "Beanbryant";
        String contain_Nickname = "Beanbrya";
        setText(Discover.ID_SEARCH_FILTER_INPUT,contain_Nickname);
        waitTime(3);
        clickById(Watch.WATCH_USER_AVATAR);
        String actul = getObjectById(Watch.WATCH_USER_MINI_NAME).getText();
        if (!full_Nickname.contains(actul)){
            Assert.fail("contain_Nickname test failed");
            Spoon.screenshot(full_Nickname,contain_Nickname);
            makeToast(full_Nickname,3);
        }

    }
    //搜索匹配email账号的用户(由于无法查看邮箱，所以无法验证通用性，只能通过一个测试账号验证)
    @Test
    public void test_ContainEmail() throws UiObjectNotFoundException{
        DiscoverAction.navToSearch();
        waitTime(1);
        //一个已经注册的用户，邮箱为472256238@qq.com。
        //nickname为jian
        String test_email="472256238";
        String expect_nickname="jian";
        UiObject Input =getObjectById(Discover.ID_SEARCH_FILTER_INPUT);
        Input.setText(test_email);
        waitTime(2);
        if (scrollAndGetUIObject(expect_nickname) == null){
            Assert.fail("匹配邮箱测试失败"+test_email);
        }
        Spoon.screenshot(test_email,expect_nickname);
    }
    //准确搜索email(由于无法查看邮箱，所以无法验证通用性，只能通过一个测试账号验证)
    @Test
    public void test_ExactEmail() throws UiObjectNotFoundException{
        DiscoverAction.navToSearch();
        waitTime(1);
        //一个已经注册的用户，邮箱为472256238@qq.com。
        //nickname为jian
        String test_email="472256238@qq.com";
        String expect_nickname="jian";
        setText(Discover.ID_SEARCH_FILTER_INPUT,test_email);
        waitTime(3);
        if (scrollAndGetUIObject(expect_nickname) == null){
            Assert.fail("准确搜索邮箱测试失败"+test_email);
            Spoon.screenshot(test_email,expect_nickname);
        }
    }
    //搜索
    //搜索匹配sioeyeID的用户
    //usa online test nickname:Beanbryant   sioeyeid:bryantBean
    //usa offline test nickname:Beanbryant   sioeyeid:bryantBean
    @Test
    public void test_ContainSioeyeID() throws UiObjectNotFoundException, IOException {
        DiscoverAction.navToSearch();
        waitTime(1);
        String full_SioeyeID = "bryantBean";
        String Contain_SioeyeID="bryantBea";
        setText(Discover.ID_SEARCH_FILTER_INPUT,Contain_SioeyeID);
        waitTime(3);
        clickById(Watch.WATCH_USER_AVATAR);
        if (getUiObjectByText("@"+full_SioeyeID) == null){
            Assert.fail("test_ContainSioeyeID failed");
            makeToast("SioeyeID",3);
            Spoon.screenshot("ContainSioeyeID");
        }

    }
    //准确搜索存在的SioeyeID的用户
    //usa online test acount:Beanbryant   sioeyeid:bryantBean
    //usa offline test acount:Beanbryant   sioeyeid:bryantBean
    @Test
    public void test_ExactSioeyeID() throws UiObjectNotFoundException, IOException {
        DiscoverAction.navToSearch();
        waitTime(1);
        String exact_SioeyeID = "bryantBean";
        setText(Discover.ID_SEARCH_FILTER_INPUT,exact_SioeyeID);
        waitTime(3);
        Spoon.screenshot("user"+exact_SioeyeID);
        clickById(Watch.WATCH_USER_AVATAR);
        if (getUiObjectByText("@"+exact_SioeyeID) == null){
            Assert.fail("search sioeyeid failed");
            Spoon.screenshot("sioeyeid"+exact_SioeyeID);
            makeToast(exact_SioeyeID+"sioeyeid");
        }

    }
    //可以查看搜索出来的user的资料
    //点击查看用户资料有bug
    @Test
    public void test_SeeUserDetail() throws UiObjectNotFoundException, IOException {
        DiscoverAction.navToSearch();
        waitTime(1);
        String expect= "Beanbryant";//these user is exist for test
        setText(Discover.ID_SEARCH_FILTER_INPUT,expect);
        waitTime(3);
        clickById(Watch.WATCH_USER_AVATAR);
        String actual=getObjectById(Watch.WATCH_USER_MINI_NAME).getText();
        if (!actual.contains(expect)){
            Assert.fail("查看用户资料或搜索用户失败");
            Spoon.screenshot("查看用户资料");
            makeToast("用户资料",3);
        }

    }
   //不输入任何东西点击搜索
    @Test
    public void test_NoInput_Search() throws UiObjectNotFoundException, IOException {
        if (!AccountAction.judgelogin()){
            AccountAction.login("1234554321@qq.com","1234554321");
        }
        //获取键盘的search按钮的point
        Point search = Nav.getSearchLocation();
        DiscoverAction.navToSearch();
        waitTime(1);
        clickById(Discover.ID_SEARCH_FILTER_INPUT);
        //通过判断点击search前后，第一个联系人的nickname不同来判断
        //当刷新后的第一个用户是中文nickname的时候，测试用例有失败
        //获取点击前的第一个用户
        String befor_nickname = gDevice.findObject(new UiSelector().className("android.widget.TextView")).getText();
        //点击键盘的搜索
        Spoon.screenshot("befor_nickname"+befor_nickname);
        clickByPoint(search);
        waitTime(2);
        String after_nickname = gDevice.findObject(new UiSelector().className("android.widget.TextView")).getText();
        if (after_nickname.equals(befor_nickname)){
            Assert.fail("刷新失败");
            makeToast("刷新失败",8);
            Spoon.screenshot("after_nickname"+after_nickname);
        }

    }
}
