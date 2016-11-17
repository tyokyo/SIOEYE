package cn.action;

import android.graphics.Point;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.widget.TextView;

import com.squareup.spoon.Spoon;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import bean.InfoBean;
import ckt.base.VP;
import ckt.base.VP2;
import cn.page.App;
import cn.page.MePage;

/**
 * Created by elon on 2016/10/27.
 */
public class MeAction extends VP2{
    public static void getAccountPrivacyInfo(InfoBean infoBean){
        //启动被测App
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        SettingAction.navToAccountAndPrivacy();
        String email=getUiObject2ByText("邮箱地址").getParent().findObject(By.res(MePage.GETNICKNAMECONTENT)).getText();
        infoBean.setEmail(email);
        String eyeId=getUiObject2ByText("Sioeye ID").getParent().findObject(By.res(MePage.GETNICKNAMECONTENT)).getText();
        infoBean.setId(eyeId);
    }
    public static void clickBroadcast() throws UiObjectNotFoundException {
        if (id_exists(MePage.ID_ME_BROADCAST)){
            clickById(MePage.ID_ME_BROADCAST);
        }else{
            clickByText("直播");
        }
    }
    public static void clickFollowing() throws UiObjectNotFoundException {
        if (id_exists(MePage.ID_ME_FOLLOWING)){
            clickById(MePage.ID_ME_FOLLOWING);
        }else{
            clickByText("关注");
        }
    }
    public static void clickFollowers() throws UiObjectNotFoundException {
        if (id_exists(MePage.ID_ME_FOLLOWERS)){
            clickById(MePage.ID_ME_FOLLOWERS);
        }else{
            clickByText("粉丝");
        }
    }
    public static void swipeUpDown(String ResourceID,int times) throws UiObjectNotFoundException {
        if (id_exists(ResourceID)){
            for (int i = 0;i <times;i++) {
                getObject2ById(ResourceID).swipe(Direction.UP,0.5f);
                getObject2ById(ResourceID).swipe(Direction.DOWN,0.5f);
            }
        }else {
            logger.info(ResourceID +" can not be found");
        }
    }
    //谁可以看我的直播-获取选取的设置内容
    public static String getPermissionToView() throws UiObjectNotFoundException {
        String permission = "";
        boolean status_public =id_exists(MePage.WHO_CAN_VIEW_MY_BROADCAST_PUBLIC);
        boolean status_private =id_exists(MePage.WHO_CAN_VIEW_MY_BROADCAST_PRIVATE);
        boolean status_personal =id_exists(MePage.WHO_CAN_VIEW_MY_BROADCAST_PARAITION);
        if (status_public==true&&status_private==false&&status_personal==true){
            permission="public";
        }
        if (status_public==false&&status_private==true&&status_personal==true){
            permission="private";
        }
        if (status_public==false&&status_private==false&&status_personal==true){
            permission="personal";
        }
        return  permission;
    }
    //编辑界面所有用户的信息
    public static InfoBean getEditUserInfo() throws UiObjectNotFoundException {
        InfoBean infoBean=new InfoBean();
        infoBean.setNick_name(getNkName());
        infoBean.setSex(getSex());
        //infoBean.setEmail(getEmailAddress());
        infoBean.setLocation(getLocation());
        //infoBean.setId(getSioEyeID());
        infoBean.setAbout_me(getAboutMe());
        return  infoBean;
    }
    //Go to 直播
    public static void navToBroadcasts() throws UiObjectNotFoundException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickBroadcast();
        waitTime(2);
        gDevice.wait(Until.findObject(By.res(MePage.BROADCAST_VIEW)),40000);
        Spoon.screenshot("navToBroadcasts");
    }
    //Go to 关注
    public static void navToFollowing() throws UiObjectNotFoundException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickFollowing();
        gDevice.wait(Until.gone(By.res(MePage.LOADING_FOLLOWERS)),40000);
        Spoon.screenshot("navToFollowing");
    }
    //Go to 粉丝
    public static void navToFans() throws UiObjectNotFoundException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickFollowers();
        gDevice.wait(Until.gone(By.res(MePage.BROADCAST_VIEW_VIDEO_LOADING)),40000);
        Spoon.screenshot("navToFans");
    }

    //Go to 直播配置
    public static void navToLiveConfiguration(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.LIVE_CONFIGURATION);
        Spoon.screenshot("navToLiveConfiguration");
    }
    //Go to 我的二维码
    public static void navToQrCode(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.LIVE_CONFIGURATION);
        Spoon.screenshot("navToQrCode");
    }
    //Go to 消息
    public static void navToNotifications(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.NOTIFICATIONS);
        gDevice.wait(Until.gone(By.res(MePage.IS_LOCATING)),20000);
        Spoon.screenshot("navToNotifications");
    }
    //Go to 设置
    public static void navToSettings(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.SETTINGS_USER_MAIN);
        Spoon.screenshot("navToSettings");
    }
    //Go to 帮助中心
    public static void navToFeedback(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.SETTINGS_USER_MAIN);
        Spoon.screenshot("navToFeedback");
    }
    //编辑 -> 昵称
    public static InfoBean navToNickName() throws UiObjectNotFoundException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.ID_USER_EDIT);
        InfoBean infoBean=getEditUserInfo();
        logger.info(infoBean.toString());
        clickById(MePage.NAV_EDIT_NICKNAME);
        Spoon.screenshot("navToNickName");
        return infoBean;
    }
    //编辑 -> 性别
    public static InfoBean navToSex() throws UiObjectNotFoundException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.ID_USER_EDIT);
        InfoBean infoBean=getEditUserInfo();
        logger.info(infoBean.toString());
        clickById(MePage.NAV_EDIT_SEX);
        Spoon.screenshot("navToSex");
        return infoBean;
    }
    //编辑 -> 邮箱
    public static InfoBean navToEmail() throws UiObjectNotFoundException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.ID_USER_EDIT);
        InfoBean infoBean=getEditUserInfo();
        logger.info(infoBean.toString());
        clickById(MePage.NAV_EDIT_EMAIL);
        Spoon.screenshot("navToEmail");
        return infoBean;
    }
    //编辑 -> 地址
    public static InfoBean navToLocation() throws UiObjectNotFoundException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.ID_USER_EDIT);
        InfoBean infoBean=getEditUserInfo();
        logger.info(infoBean.toString());
        clickById(MePage.NAV_EDIT_LOCATION);
        if (id_exists(VP.PERMISSION_ALLOW)){
            clickById(VP.PERMISSION_ALLOW);
        }
        Spoon.screenshot("navToLocation");
        return infoBean;
    }
    //编辑 -> 爱好
    public static InfoBean navToActivities() throws UiObjectNotFoundException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.ID_USER_EDIT);
        InfoBean infoBean=getEditUserInfo();
        logger.info(infoBean.toString());
        clickById(MePage.NAV_EDIT_ACTIVITY);
        gDevice.wait(Until.gone(By.res(MePage.IS_LOCATING)),20000);
        Spoon.screenshot("navToActivities");
        return infoBean;
    }
    //编辑 -> Sio Eye ID
    public static InfoBean navToSioEyeId() throws UiObjectNotFoundException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.ID_USER_EDIT);
        InfoBean infoBean=getEditUserInfo();
        logger.info(infoBean.toString());
        clickById(MePage.NAV_EDIT_SIOEYE_ID);
        Spoon.screenshot("navToSioEyeId");
        return infoBean;
    }
    //编辑 -> 个性签名
    public static InfoBean navToAboutMe() throws UiObjectNotFoundException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.ID_USER_EDIT);
        InfoBean infoBean=getEditUserInfo();
        logger.info(infoBean.toString());
        clickById(MePage.NAV_EDIT_ABOUT_ME);
        Spoon.screenshot("navToAboutMe");
        return infoBean;
    }
    //Go to 编辑
    public static InfoBean navToUserEdit() throws UiObjectNotFoundException {
        clickById(MePage.ID_MAIN_TAB_ME);
        Spoon.screenshot(gDevice,"Me");
        clickById(MePage.ID_USER_EDIT);
        InfoBean infoBean=getEditUserInfo();
        logger.info(infoBean.toString());
        return infoBean;
    }
    /**
     *获取点赞图标的X Y,作为键盘输入的确定按钮
     * @param
     */
    public static Point getPointToDoComment() throws UiObjectNotFoundException, IOException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        Point point = new Point();
        navToBroadcasts();
        int index=BroadcastAction.getRandomBroadcastsIndex();
        BroadcastAction.getRandomBroadcasts(index).click();
        BroadcastAction.waitBroadcastLoading();
        UiObject zan = getUiObjectById(MePage.BROADCAST_VIEW_ZAN);
        int x = zan.getBounds().centerX();
        int y = zan.getBounds().centerY();
        point.set(x,y);
        makeToast(point.x+"|"+point.y,3);
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        return  point;
    }
    //获取nickname
    public static String getNkName() throws UiObjectNotFoundException {
        UiObject u =  gDevice.findObject(new UiSelector().resourceId(MePage.NAV_EDIT_NICKNAME));
        String me = u.getChild(new UiSelector().resourceId(MePage.ABOUT_ME_CONTENT_TEXT)).getText();
        return  me;
    }
    //获取性别
    public static String getSex() throws UiObjectNotFoundException {
        UiObject u =  gDevice.findObject(new UiSelector().resourceId(MePage.NAV_EDIT_SEX));
        String me = u.getChild(new UiSelector().resourceId(MePage.ABOUT_ME_CONTENT_TEXT)).getText();
        return  me;
    }
    //邮件
    public static String getEmailAddress() throws UiObjectNotFoundException {
        UiObject u =  gDevice.findObject(new UiSelector().resourceId(MePage.NAV_EDIT_EMAIL));
        String em = u.getChild(new UiSelector().resourceId(MePage.ABOUT_ME_CONTENT_TEXT)).getText();
        return  em;
    }
    //地址
    public static String getLocation() throws UiObjectNotFoundException {
        UiObject u =  gDevice.findObject(new UiSelector().resourceId(MePage.NAV_EDIT_LOCATION));
        String em = u.getChild(new UiSelector().resourceId(MePage.ABOUT_ME_CONTENT_TEXT)).getText();
        return  em;
    }
    //爱好
    public static String getActivities() throws UiObjectNotFoundException {
        UiObject u =  gDevice.findObject(new UiSelector().resourceId(MePage.NAV_EDIT_ACTIVITY));
        String em = u.getChild(new UiSelector().resourceId(MePage.ABOUT_ME_CONTENT_TEXT)).getText();
        return  em;
    }
    //SIO EYE ID
    public static String getSioEyeID() throws UiObjectNotFoundException {
        UiObject u =  gDevice.findObject(new UiSelector().resourceId(MePage.NAV_EDIT_SIOEYE_ID));
        String em = u.getChild(new UiSelector().resourceId(MePage.ABOUT_ME_CONTENT_TEXT)).getText();
        return  em;
    }
    //个性签名内容
    public static String getAboutMe() throws UiObjectNotFoundException {
        UiObject u =  gDevice.findObject(new UiSelector().resourceId(MePage.NAV_EDIT_ABOUT_ME));
        String me = u.getChild(new UiSelector().resourceId(MePage.ABOUT_ME_CONTENT_TEXT)).getText();
        return  me;
    }

    /*
    获取子对象内容MePage.ABOUT_ME_CONTENT_TEXT
    父对象id-MePage.ABOUT_ME_ID
    public static final String NAV_EDIT_NICKNAME="cn.sioeye.sioeyeapp:id/nickname";
    public static final String NAV_EDIT_SEX="cn.sioeye.sioeyeapp:id/sex";
    public static final String NAV_EDIT_EMAIL="cn.sioeye.sioeyeapp:id/email";
    public static final String NAV_EDIT_LOCATION="cn.sioeye.sioeyeapp:id/location";
    public static final String NAV_EDIT_ACTIVITY="cn.sioeye.sioeyeapp:id/interest";
    public static final String NAV_EDIT_SIOEYE_ID="cn.sioeye.sioeyeapp:id/sioeye_id";
    public static final String NAV_EDIT_ABOUT_ME="cn.sioeye.sioeyeapp:id/about";
    * */
    public static String getEditContent(String resourceID) throws UiObjectNotFoundException {
        UiObject u =  gDevice.findObject(new UiSelector().resourceId(resourceID));
        String me = u.getChild(new UiSelector().resourceId(MePage.ABOUT_ME_CONTENT_TEXT)).getText();
        return  me;
    }
    //谁可以看我的直播-设置为 公开public
    public static void setToPublic() throws UiObjectNotFoundException {
        clickByText("公开");
    }
    //谁可以看我的直播-设置为 秘密private
    public static void setToPrivate() throws UiObjectNotFoundException {
        clickByText("私密");
    }
    //谁可以看我的直播-设置为 秘密private
    public static void setToPersonal() throws UiObjectNotFoundException {
        clickByText("部分可见");
    }

}
