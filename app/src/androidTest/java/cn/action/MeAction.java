package cn.action;

import android.graphics.Point;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import com.squareup.spoon.Spoon;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import bean.InfoBean;
import bean.MeBean;
import ckt.base.VP;
import ckt.base.VP2;
import cn.page.App;
import cn.page.MePage;
import cn.page.PlayPage;

/**
 * Created by elon on 2016/10/27.
 */
public class MeAction extends VP2{
    private static Logger logger = Logger.getLogger(MeAction.class.getName());
    public static void getAccountPrivacyInfo(InfoBean infoBean) throws UiObjectNotFoundException {
        //启动被测App
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        SettingAction.navToAccountAndPrivacy();
        String email=getUiObject2ByText("Email").getParent().getParent().findObject(By.res(MePage.GETNICKNAMECONTENT)).getText();
        infoBean.setEmail(email);
        String eyeId=getUiObject2ByText("Sioeye ID").getParent().getParent().findObject(By.res(MePage.GETNICKNAMECONTENT)).getText();
        infoBean.setId(eyeId);
    }
    public static MeBean getMeBean(){
        MeBean bean=new MeBean();
        String nickName= getObject2ById(MePage.WATCH_USER_MINI_NAME).getText();
        int videos= cover(getObject2ById(MePage.HOME_BROADCASTS).getText());
        int following= cover(getObject2ById(MePage.HOME_FOLLOWING).getText());
        int follower= cover(getObject2ById(MePage.HOME_FOLLOWERS).getText());
        int like=cover(getObject2ById(MePage.HOME_LIKE).getText());
        bean.setNickName(nickName);
        bean.setLike(like);
        bean.setVideos(videos);
        bean.setFollower(follower);
        bean.setFollowing(following);
        logger.info(bean.toString());
        return bean;
    }
    public static void clickBroadcast() throws UiObjectNotFoundException {
        if (id_exists(MePage.ID_ME_BROADCAST)){
            clickById(MePage.ID_ME_BROADCAST);
        }else{
            clickByText("Videos");
        }
    }
    public static void clickFollowing() throws UiObjectNotFoundException {
        if (id_exists(MePage.ID_ME_FOLLOWING)){
            clickById(MePage.ID_ME_FOLLOWING);
        }else{
            clickByText("Following");
        }
    }
    public static void clickFollowers() throws UiObjectNotFoundException {
        if (id_exists(MePage.ID_ME_FOLLOWERS)){
            clickById(MePage.ID_ME_FOLLOWERS);
        }else{
            clickByText("Followers");
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
        if(status_private==true&&status_personal==true) {
            permission = "private";
        }
        if (status_public==false&&status_private==false&&status_personal==true){
            permission="personal";
        }
        logger.info(permission);
        return  permission;
    }
    //编辑界面所有用户的信息
    public static InfoBean getEditUserInfo() throws UiObjectNotFoundException {
        InfoBean infoBean=new InfoBean();
        infoBean.setNick_name(getNkName());
        infoBean.setSex(getSex());
        infoBean.setLocation(getLocation());
        infoBean.setAbout_me(getAboutMe());
        return  infoBean;
    }
    //Go to 直播
    public static void navToBroadcasts() throws UiObjectNotFoundException {
        clickById(MePage.ID_MAIN_TAB_ME);
        clickBroadcast();
        waitTime(2);
        gDevice.wait(Until.findObject(By.res(MePage.BROADCASTS_LIST)),40000);
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
        gDevice.wait(Until.gone(By.res(PlayPage.BROADCAST_VIEW_VIDEO_LOADING)),40000);
        Spoon.screenshot("navToFans");
    }
    //Me-> 直播配置
    public static void navToLiveConfiguration(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickByText("Live Configuration");
        Spoon.screenshot("navToLiveConfiguration");
        waitTime(3);
    }
    //me->固定拉流地址
    public static void navToPullingSource(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickByText("Live Configuration");
        clickByText("Select pulling source");
        Spoon.screenshot("navToPullingSource");
    }
    //me->直播封面
    public static void navToCoverplot(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickByText("Live Configuration");
        clickByText("Cover plot");
        Spoon.screenshot("navToCoverplot");
    }
    //Me->会员权益
    public static void navToVipRights(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickByText("VIP Rights");
        Spoon.screenshot("navToVipRights");
    }
    //Me-> 我的二维码
    public static void navToQrCode() throws UiObjectNotFoundException {
        MainAction.clickMe();
        clickByText("QR Code");
        Spoon.screenshot("navToQrCode");
    }
    //Me-> 消息
    public static void navToNotifications() throws UiObjectNotFoundException {
        MainAction.clickMe();
        clickByText("Notifications");
        gDevice.wait(Until.gone(By.res(MePage.IS_LOCATING)),20000);
        Spoon.screenshot("navToNotifications");
    }
    //Me-> 设置
    public static void navToSettings() throws UiObjectNotFoundException {
        MainAction.clickMe();
        //2017/11/30修改，小屏手机找不到Settings，需要滑动实现--jqx
        if(!text_exists_contain("Settings")){
            UiObject2 swipe_target = getObject2ById(MePage.SCROLL_ME_VIEW);
            swipe_target.swipe(Direction.UP, 0.6f);
        }
        clickByText("Settings");
        Spoon.screenshot("navToSettings");
    }
    //Me->相机
    public static void navToCamera() throws UiObjectNotFoundException {
        MainAction.clickMe();
        clickByText("Camera");
        Spoon.screenshot("Camera");
    }
    //Me->收藏
    public static void navToCollection() throws UiObjectNotFoundException {
        MainAction.clickMe();
        clickByText("Collection");
        Spoon.screenshot("Collection");
    }
    //Go to 帮助
    public static void navToHelp() throws UiObjectNotFoundException {
        MainAction.clickMe();
        clickByText("Help");
        Spoon.screenshot("Help");
    }
    //编辑 -> 昵称
    public static InfoBean navToNickName() throws UiObjectNotFoundException {
        MainAction.clickMe();
        clickById(MePage.ID_USER_EDIT);
        InfoBean infoBean=getEditUserInfo();
        logger.info(infoBean.toString());
        clickById(MePage.NAV_EDIT_NICKNAME);
        Spoon.screenshot("navToNickName");
        return infoBean;
    }
    //编辑 -> 性别
    public static InfoBean navToSex() throws UiObjectNotFoundException {
        MainAction.clickMe();
        clickById(MePage.ID_USER_EDIT);
        InfoBean infoBean=getEditUserInfo();
        logger.info(infoBean.toString());
        clickById(MePage.NAV_EDIT_SEX);
        Spoon.screenshot("navToSex");
        return infoBean;
    }
    //编辑 -> 邮箱
    public static InfoBean navToEmail() throws UiObjectNotFoundException {
        MainAction.clickMe();
        clickById(MePage.ID_USER_EDIT);
        InfoBean infoBean=getEditUserInfo();
        logger.info(infoBean.toString());
        clickById(MePage.NAV_EDIT_EMAIL);
        Spoon.screenshot("navToEmail");
        return infoBean;
    }
    //编辑 -> 地址
    public static InfoBean navToLocation() throws UiObjectNotFoundException {
        MainAction.clickMe();
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
        MainAction.clickMe();
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
        MainAction.clickMe();
        clickById(MePage.ID_USER_EDIT);
        InfoBean infoBean=getEditUserInfo();
        logger.info(infoBean.toString());
        clickById(MePage.NAV_EDIT_SIOEYE_ID);
        Spoon.screenshot("navToSioEyeId");
        return infoBean;
    }
    //编辑 -> 个性签名
    public static InfoBean navToAboutMe() throws UiObjectNotFoundException {
        MainAction.clickMe();
        clickById(MePage.ID_USER_EDIT);
        InfoBean infoBean=getEditUserInfo();
        logger.info(infoBean.toString());
        clickById(MePage.NAV_EDIT_ABOUT_ME);
        Spoon.screenshot("navToAboutMe");
        return infoBean;
    }
    //Go to 编辑
    public static InfoBean navToUserEdit() throws UiObjectNotFoundException {
        MainAction.clickMe();
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
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        Point point = new Point();
        navToBroadcasts();
        int index=BroadcastAction.getRandomBroadcastsIndex();
        BroadcastAction.clickRandomBroadcastsVideo(index);
        BroadcastAction.waitBroadcastLoading();
        UiObject zan = getUiObjectById(PlayPage.BROADCAST_VIEW_ZAN);
        int x = zan.getBounds().centerX();
        int y = zan.getBounds().centerY();
        point.set(x,y);
        makeToast(point.x+"|"+point.y,3);
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
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
    public static String getEditContent(String resourceID) throws UiObjectNotFoundException {
        UiObject u =  gDevice.findObject(new UiSelector().resourceId(resourceID));
        String me = u.getChild(new UiSelector().resourceId(MePage.ABOUT_ME_CONTENT_TEXT)).getText();
        return  me;
    }
    //谁可以看我的直播-设置为 公开public
    public static void setToPublic() throws UiObjectNotFoundException {
        clickByText("Public");
    }
    //谁可以看我的直播-设置为 秘密private
    public static void setToPrivate() throws UiObjectNotFoundException {
        clickByText("Private");
    }
    //谁可以看我的直播-设置为 秘密private
    public static void setToPersonal() throws UiObjectNotFoundException {
        clickByText("Private");
        clickByText("Visible to someone");
        waitUntilFind(MePage.SELECT_PEOPLE,10000);
    }
    //评论区域-滑动更新,显示最新消息
    public static void displayNewMessages() throws UiObjectNotFoundException {
        if (id_exists(PlayPage.NEW_MESSAGES_DISPLAY)){
            getObject2ById(PlayPage.NEW_MESSAGES_DISPLAY).click();
            waitTime(2);
        }
    }
    //确认创建直播间
    public static void createLiveRoom() throws UiObjectNotFoundException {
        clickLiveStreamChannel();
        waitUntilFindText("Establish live stream channel",10000);
        if(text_exists("Establish live stream channel")){
            clickEstablishLiveStreamChannel();
            waitUntilFind(MePage.LIVE_ROOM_INFO,5000);
        }else {

        }
        clickByText("OK");
        //pop up dialog(create successful)
        clickById(MePage.ID_TV_OK);
        waitTime(3);
    }
    //delete live room Effective
    public static void deleteAllEffectiveRoom() throws UiObjectNotFoundException {
        waitUntilFind(MePage.LIVE_ROOM_LIST,5000);
        if (id_exists(MePage.LIVE_ROOM_LIST)){
            UiObject2 list=getUiObject2ById(MePage.LIVE_ROOM_LIST);
            List<UiObject2> rooms=list.findObjects(By.depth(1).clazz(android.widget.LinearLayout.class));
            for (UiObject2 room: rooms) {
                if (room.hasObject(By.text("Effective"))){
                    room.findObject(By.text("More")).click();
                    waitUntilFind(MePage.LIVE_ROOM_MORE_OPTION_LIST,10000);
                    clickTerminateLiveRoom();
                    waitTime(3);
                }
            }
        }
        logger.info("all live room has been deleted success");
        gDevice.pressBack();
        waitTime(3);
    }
    //Effective=true stop=false
    public static void liveRoomStatus() throws UiObjectNotFoundException {
        boolean status=false;
        if (text_exists("Effective")){
            status=true;
        }
        if (text_exists("Stop")){
            status=false;
        }
    }
    //Terminate Live room
    public static void clickTerminateLiveRoom(){
        getUiObject2ByText("Terminate").click();
    }
    //Delete Live room
    public static void clickDeleteLiveRoom(){
        getUiObject2ByText("Delete").click();
    }
    //click Live stream channel
    public static void clickLiveStreamChannel(){
        getLiveStreamChannel().click();
        waitUntilFindText("Establish live stream channel",5000);
    }
    //nav to Live stream channel
    public static void clickEstablishLiveStreamChannel(){
        getEstablishLiveStreamChannel().click();
        waitUntilFind(MePage.LIVE_ROOM_NAME,5000);
    }
    public static UiObject2 getLiveStreamChannel(){
        return  getUiObject2ByText("Live stream channel");
    }
    public static UiObject2 getEstablishLiveStreamChannel(){
        return  getUiObject2ByText("Establish live stream channel");
    }
    //judge if live room is has been created
    public static boolean isLiveRoomEffective() throws UiObjectNotFoundException {
        waitUntilFind(MePage.LIVE_ROOM_DATE_RECT,10000);
        boolean isCreated=false;
        UiObject2 pObj=getLiveStreamChannel().getParent().getParent();
        UiObject2 live_room_date= pObj.findObject(By.res(MePage.LIVE_ROOM_DATE_RECT));
        if (live_room_date!=null){
            String date =live_room_date.getText();
            if (date!=null){
                if (date.trim().length()>=1){
                    isCreated=true;
                }
            }
        }
        return isCreated;
    }
}
