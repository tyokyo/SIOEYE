package cn.testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.widget.CheckBox;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.MeAction;
import cn.page.App;
import cn.page.MePage;

/**
 * Created by chendaofa on 2016/10/27.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
/*直播默认标题
*30个汉字或者70个字符
 *  */
public class LiveConfigCase extends VP2{
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageNameInLogin(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }

    //直播标题内容设置-长度-3
    @Test
    @SanityTest
    @PerformanceTest
    public void testTitle3c() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(MePage.LIVE_CONFIG_CONTENT).clearTextField();
        String expect = getRandomString(3);
        shellInputText(expect);
        clickById(MePage.LIVE_CONFIGURATION_DONE_TITLE);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        String active = getObjectById(MePage.LIVE_CONFIG_CONTENT).getText();
        Asst.assertEquals("修改video title",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();

    }
    //直播标题内容设置-长度-35
    @Test
    @SanityTest
    @PerformanceTest
    public void testTitle40c() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(MePage.LIVE_CONFIG_CONTENT).clearTextField();
        String expect = getRandomString(35);
        shellInputText(expect);
        clickById(MePage.LIVE_CONFIGURATION_DONE_TITLE);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        String active = getObjectById(MePage.LIVE_CONFIG_CONTENT).getText();
        Asst.assertEquals("修改video title",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();

    }
    //直播标题内容设置-长度-120
    @Test
    @SanityTest
    @PerformanceTest
    public void testTitle20c() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(MePage.LIVE_CONFIG_CONTENT).clearTextField();
        String expect = getRandomString(20);
        shellInputText(expect);
        clickById(MePage.LIVE_CONFIGURATION_DONE_TITLE);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        String active = getObjectById(MePage.LIVE_CONFIG_CONTENT).getText();
        Asst.assertEquals("修改video title",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();

    }
    //直播标题内容设置-长度->170(最多允许设置35)
    @Test
    @SanityTest
    @PerformanceTest
    public void testTitle50c() throws UiObjectNotFoundException, IOException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        getObjectById(MePage.LIVE_CONFIG_CONTENT).clearTextField();
        String expect = getRandomString(100);
        shellInputText(expect);
        clickById(MePage.LIVE_CONFIGURATION_DONE_TITLE);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_VIDEO_TITLE);
        String active = getObjectById(MePage.LIVE_CONFIG_CONTENT).getText();
        logger.info("length:"+active.length());
        expect=expect.substring(0,70);
        Asst.assertEquals("修改video title",expect,active);
        gDevice.pressBack();
        gDevice.pressBack();

    }
    //privacy_settings to public
    @Test
    @SanityTest
    @PerformanceTest
    public void testSetPublic() throws UiObjectNotFoundException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        MeAction.setToPublic();
        clickById(MePage.LIVE_CONFIGURATION_DONE_PRIVACY);
        waitUntilGone(MePage.LIVE_CONFIGURATION_DONE_PRIVACY,10000);
        waitTime(3);
        Spoon.screenshot("public");
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        waitUntilFind(MePage.WHO_CAN_VIEW_MY_BROADCAST_PUBLIC,10000);
        String permission=MeAction.getPermissionToView();
        Asst.assertEquals("set Privacy Setting:Public","public",permission);
        Spoon.screenshot("Public","Permission_Public");
    }
    //privacy_settings to private
    @Test
    @SanityTest
    @PerformanceTest
    public void testSetPrivate() throws UiObjectNotFoundException {
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        MeAction.setToPrivate();
        clickById(MePage.LIVE_CONFIGURATION_DONE_PRIVACY);
        waitUntilGone(MePage.LIVE_CONFIGURATION_DONE_PRIVACY,10000);
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        waitUntilFind(MePage.WHO_CAN_VIEW_MY_BROADCAST_PRIVATE,10000);
        String permission=MeAction.getPermissionToView();
        Asst.assertEquals("set Privacy Setting:private","private",permission);
        Spoon.screenshot("private","Privacy_Settings_Private");
    }
    @Test
    @SanityTest
    @PerformanceTest
    public void testSetPersonal() throws UiObjectNotFoundException {
        MeAction.navToLiveConfiguration();
        //谁可以看我的直播
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        //设置为部分可见
        MeAction.setToPersonal();
        //如果没有选择用户
        if (!id_exists(MePage.SELECT_PEOPLE_LIST_Horizontal)){
            logger.info("Not select user");
            //选择第一个用户
            getObject2ById(MePage.SELECT_PEOPLE_LIST_Vertical).findObjects(By.res(MePage.SELECT_PEOPLE)).get(0).click();
        }else{
            logger.info("select user");
        }
        clickById(MePage.PRIVACY_PERSONAL_DONE);
        clickById(MePage.PRIVACY_PERSONAL_RIGHT);

        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        //谁可以看我的直播
        clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
        String permission=MeAction.getPermissionToView();
        Asst.assertEquals("set Privacy Setting:Public","personal",permission);
        Spoon.screenshot("personal");
        MeAction.setToPersonal();
        Spoon.screenshot("personal");
    }
    //点击直播封面检查弹出框-陈道发
    @Test
    @SanityTest
    @PerformanceTest
    public void testSetCoverPlot() throws UiObjectNotFoundException {
        MeAction.navToCoverplot();
        String expect_name1="Camera";
        String active_name1=getObjectById(MePage.COVER_PLOT_BTN_DIALOG_PHOTO).getText();
        String expect_name2="Album";
        String active_name2=getObjectById(MePage.COVER_PLOT_BTN_DIALOG_ALBUM).getText();
        String expect_name3="Cancel";
        String active_name3=getObjectById(MePage.COVER_PLOT_BTN_DIALOG_CANCEL).getText();
        Asst.assertEquals("点击的信息是否一致", expect_name1+expect_name2+expect_name3, active_name1+active_name2+active_name3);
        clickById(MePage.COVER_PLOT_BTN_DIALOG_CANCEL);
        Spoon.screenshot("testSetCoverPlot");

    }
    /**
     * 固定拉流地址跳转到会员权益
     * @author chendaofa
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testSelectPullingSource() throws UiObjectNotFoundException {
        MeAction.navToPullingSource();
        String active_name1=getObjectById(MePage.SELECT_PULLINS_TV_MEMBER_TEXT).getText();
        String expect_name1="You have not signed up for Sioeye VIP";
        if (active_name1.equals(expect_name1)){
            clickById(MePage.SELECT_PULLINS_TV_OPEN);
            waitTime(5);
            String active_name2=getObjectById(MePage.VIP_RIGHT_TITLE).getText();
            String expect_name2="VIP Rights";
            Asst.assertEquals("是否跳转到会员权益界面", expect_name2, active_name2);
        }
        Spoon.screenshot("testSetCoverPlot");
    }
    /**
     * 水印开关测试
     *测试开关水印
     * @author chendaofa
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testWaterMarkDelete() throws UiObjectNotFoundException {
        boolean vip = id_exists(MePage.ID_VIP);
        MeAction.navToLiveConfiguration();
        boolean isChecked=getObject2ByClass(CheckBox.class).isChecked();
        logger.info(""+isChecked);
        if(vip==false){
            clickByClass("android.widget.CheckBox",1);
            clickById(MePage.BECOME_VIP);
            if(text_exists("You have not signed up for Sioeye VIP")){
                Asst.assertTrue(true);
                Spoon.screenshot("VIPRights");
            }
        }else if(vip==true){
            if (isChecked==false){
                clickByClass("android.widget.CheckBox",1);
                waitTime(2);
                boolean avtiveisChecked=getObject2ByClass(CheckBox.class).isChecked();
                boolean expect;
                expect=true;
                Asst.assertEquals("打开水印开关",expect,avtiveisChecked);
                Spoon.screenshot("testWaterMarkDelete");
            }if (isChecked==true){
                clickByClass("android.widget.CheckBox",1);
                waitTime(2);
                boolean avtiveisChecked=getObject2ByClass(CheckBox.class).isChecked();
                boolean expect;
                expect=false;
                Asst.assertEquals("关闭水印开关",expect,avtiveisChecked);
                Spoon.screenshot("testWaterMarkDelete");
            }
        }
    }
  /**
   *创建直播间
   * qiang.zhang 2017.12.10
   * */
    @Test
    @SanityTest
    @PerformanceTest
    public void testCreateLiveRoom() throws UiObjectNotFoundException {
        MeAction.navToLiveConfiguration();
        //if live room exist
        if (MeAction.isLiveRoomEffective()){
            MeAction.clickLiveStreamChannel();
            //delete all effective room
            MeAction.deleteAllEffectiveRoom();
        }else{
            //do nothing for no room effective
        }
        //create live room
        MeAction.createLiveRoom();
        Spoon.screenshot("create_live_room");
        Asst.assertEquals("create live room success",true,text_exists("Effective"));
    }
    /**
     *删除所有直播间
     * qiang.zhang 2017.12.10
     * */
    @Test
    @SanityTest
    @PerformanceTest
    public void testDeleteAllLiveRoom() throws UiObjectNotFoundException {
        //navigate to live configuration
        MeAction.navToLiveConfiguration();
        //if live room exist
        if (MeAction.isLiveRoomEffective()){
            //enter live room
            MeAction.clickLiveStreamChannel();
            //delete all live room effective
            MeAction.deleteAllEffectiveRoom();
            waitTime(3);
            Asst.assertEquals("delete all success",false,MeAction.isLiveRoomEffective());
        }
        Spoon.screenshot("delete_all_live_room");
    }
    /**
     *修改直播间属性：公开-私有
     * jqx 2017.12.26
     * */
    @Test
    @SanityTest
    @PerformanceTest
    public void testChangeLiveRoom() throws UiObjectNotFoundException {
        //navigate to live configuration
        MeAction.navToLiveConfiguration();
        //enter live room
        MeAction.clickLiveStreamChannel();
        int live_channel_size = MeAction.getRoomNum();
        if(live_channel_size>=1){
            //得到房间对象-title
            String roomTitle  = MeAction.getRoomTitle(0);
            //获取more按钮
            MeAction.clickMoreRoom(roomTitle);
            waitTime(3);
            String isOpen = MeAction.getOpenOrPrivate().getText();
            Spoon.screenshot("open_private_live_room1");
            MeAction.getOpenOrPrivate().click();
            //再次点击“more”按钮，检查属性是否修改成功
            waitTime(4);
            MeAction.clickMoreRoom(roomTitle);
            Spoon.screenshot("open_private_live_room2");
            String isPrivate = MeAction.getOpenOrPrivate().getText();
            Asst.assertTrue(!isOpen.equals(isPrivate));
        }
        Spoon.screenshot("open_private_live_room3");
    }
    /**
     *搜索直播间
     * jqx 2017.12.26
     * */
    @Test
    @SanityTest
    @PerformanceTest
    public void testSearchLiveRoom() throws UiObjectNotFoundException, IOException {
        //navigate to live configuration
        MeAction.navToLiveConfiguration();
        //enter live room
        MeAction.clickLiveStreamChannel();
        int live_channel_size = MeAction.getRoomNum();
        if(live_channel_size>=1){
            String roomTitle = getObject2ById(MePage.BROADCAST_TITLE).getText();
            //点击搜索按钮
            clickById(MePage.ID_LIVE_ROOM_SEARCH);
            shellInputText(roomTitle);
        }
        waitTime(3);
        //如果搜索有结果，则pass
        Asst.assertTrue(id_exists(MePage.BROADCAST_TITLE));
        Spoon.screenshot("search_live_room");
    }
    /**
     *修改直播房间介绍
     * 1.修改直播房间简介
     * 2.进入该直播房间，检查是否修改成功
     * jqx 2017.12.26
     * */
    @Test
    @SanityTest
    @PerformanceTest
    public void testLiveRoomDescription() throws UiObjectNotFoundException, IOException {
        //navigate to live configuration
        MeAction.navToLiveConfiguration();
        //enter live room
        MeAction.clickLiveStreamChannel();
        int live_channel_size = MeAction.getRoomNum();
        if(live_channel_size>=1){
            //得到房间对象-title
            String roomTitle  = MeAction.getRoomTitle(0);
            //点击Edit按钮
            MeAction.clickEditRoom(roomTitle);
            //点击房间简介
            clickById(MePage.LIVE_ROOM_INFO);
            Spoon.screenshot("live_room_intro_old");
            getObjectById(MePage.LIVE_ROOM_INFO).clearTextField();
            String expect = getRandomString(40);
            shellInputText(expect);
            gDevice.pressBack();
            waitTime(2);
            String descInput = getTex(MePage.LIVE_ROOM_INFO);
            clickByText("OK");//保存
            //点击进入该房间，检查房间介绍修改是否成功
            MeAction.clickEnterRoom(roomTitle);
            String actResult = MeAction.getRoomIntroduction().getText();
            Asst.assertEquals("修改直播间介绍",descInput,actResult);
        }
        Spoon.screenshot("search_live_intro_new");
    }
    /**
     * 直播介绍
     * 输入直播介绍后，保存，再进入，对比结果
     * jqx 2017.12.26
     * */
    @Test
    @SanityTest
    @PerformanceTest
    public void testLiveStreamIntroduction() throws UiObjectNotFoundException, IOException {
        //navigate to live configuration
        MeAction.navToLiveConfiguration();
        //点击live stream introduction
        MeAction.getLiveStreamIntroduction().click();
        //clear text
        getObjectById(MePage.LIVE_CONFIG_CONTENT).clearTextField();
        String expect = getRandomString(35);
        shellInputText(expect);
        clickById(MePage.LIVE_CONFIGURATION_DONE_TITLE);
        //重新打开APP，进入直播设置界面
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        MeAction.getLiveStreamIntroduction().click();
        String active = getObjectById(MePage.LIVE_CONFIG_CONTENT).getText();
        Asst.assertEquals("修改直播介绍",expect,active);
        Spoon.screenshot("change_live_stream_introduction");
        gDevice.pressBack();
    }
    /**
     * 直播介绍（最多可以输入140个字符）
     * 输入140个字符的直播介绍后，保存
     * jqx 2017.12.26
     * */
    @Test
    @SanityTest
    @PerformanceTest
    public void testLiveStreamIntro140c() throws UiObjectNotFoundException, IOException {
        //navigate to live configuration
        MeAction.navToLiveConfiguration();
        //点击live stream introduction
        MeAction.getLiveStreamIntroduction().click();
        //clear text
        getObjectById(MePage.LIVE_CONFIG_CONTENT).clearTextField();
        String expect = getRandomString(140);
        shellInputText(expect);
        clickById(MePage.LIVE_CONFIGURATION_DONE_TITLE);
        //重新打开APP，进入直播设置界面
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        MeAction.getLiveStreamIntroduction().click();
        String active = getObjectById(MePage.LIVE_CONFIG_CONTENT).getText();
        Asst.assertEquals("修改直播介绍为140个字符",expect,active);
        Spoon.screenshot("change_live_stream_intro140c");
        gDevice.pressBack();
    }
    /**
     * 直播介绍（超出140个字符）
     * 1.输入150个字符的直播介绍
     * 2.只能输入140个字符的直播介绍，超出的10个字符不能输入进去
     * jqx 2017.12.26
     * */
    @Test
    @SanityTest
    @PerformanceTest
    public void testLiveStreamIntro150c() throws UiObjectNotFoundException, IOException {
        //navigate to live configuration
        MeAction.navToLiveConfiguration();
        //点击live stream introduction
        MeAction.getLiveStreamIntroduction().click();
        //clear text
        getObjectById(MePage.LIVE_CONFIG_CONTENT).clearTextField();
        String expect = getRandomString(150);
        shellInputText(expect);
        clickById(MePage.LIVE_CONFIGURATION_DONE_TITLE);
        //重新打开APP，进入直播设置界面
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        MeAction.getLiveStreamIntroduction().click();
        String remain = getObjectById(MePage.ID_REMAIN).getText();
        Asst.assertEquals("修改直播介绍为150个字符","0",remain);
        Spoon.screenshot("change_live_stream_intro150c");
        gDevice.pressBack();
    }
    /**
     * 清空直播介绍
     * 1.只能输入直播介绍后，保存
     * 2.清空直播介绍，保存
     * jqx 2017.12.26
     * */
    @Test
    @SanityTest
    @PerformanceTest
    public void testLiveStreamIntroClear() throws UiObjectNotFoundException, IOException {
        //navigate to live configuration
        MeAction.navToLiveConfiguration();
        //点击live stream introduction
        MeAction.getLiveStreamIntroduction().click();
        //clear text
        getObjectById(MePage.LIVE_CONFIG_CONTENT).clearTextField();
        String expect = getRandomString(50);
        shellInputText(expect);
        clickById(MePage.LIVE_CONFIGURATION_DONE_TITLE);
        //重新打开APP，进入直播设置界面
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToLiveConfiguration();
        MeAction.getLiveStreamIntroduction().click();
        //清空直播介绍
        getObjectById(MePage.LIVE_CONFIG_CONTENT).clearTextField();
        clickById(MePage.LIVE_CONFIGURATION_DONE_TITLE);
        waitTime(4);
        //重新进入直播介绍，获取当前内容为空
        MeAction.getLiveStreamIntroduction().click();
        String remain = getObjectById(MePage.LIVE_CONFIG_CONTENT).getText();
        Asst.assertEquals("清空直播介绍","",remain);
        Spoon.screenshot("clear_live_stream_introduction");
        gDevice.pressBack();
    }
    /**非公开时点击同步直播，检查提示
     * creat by zyj 2017.12.27*/
    @Test
    @SanityTest
    @PerformanceTest
    public void testUnPublicPrompt() throws UiObjectNotFoundException {
        String str=MeAction.getPrivacy(); //获取可见性
        if (!str.equals("Public")){
            getObject2ById(MePage.LIVE_CONFIGURATION_SLV_VIDEO).click();
            waitTime(3);
            boolean boo=text_exists("Only public live streams and be simulcast to third party platforms");
            Asst.assertEquals("有提示",true,boo);
            clickById(MePage.ID_TV_OK);
        }
    }
    /**
     * 添加自定义同步直播地址（默认保存20个字符地址）
     * creat by zyj 2017.12.27*/
    @Test
    @SanityTest
    @PerformanceTest
    public void testAddLiveStreamAddress() throws UiObjectNotFoundException, IOException {
        String str=MeAction.getPrivacy(); //获取可见性
        if (!str.equals("Public")) {
            clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
            MeAction.setToPublic();
            clickById(MePage.LIVE_CONFIGURATION_DONE_PRIVACY);
            waitTime(3);
            getObject2ById(MePage.LIVE_CONFIGURATION_SLV_VIDEO).click();
            clickById(MePage.ACTIVITIES_CONTENT);
            waitTime(3);
            if (!text_exists("Address(es) already added")){
                clickById(MePage.ADD_LIVE_STREAN_ADDRESS);
                clickById(MePage.PUT_RTMP_ADDRESS);
                String input_address = getRandomString(20); //输入随机字符地址
                shellInputText(input_address);
                clickById(MePage.SAVE_RTMP_ADDRESS); //点击保存
                Asst.assertTrue("comments success",getUiObjectByTextContains(input_address).exists());
            }
        }else {
            getObject2ById(MePage.LIVE_CONFIGURATION_SLV_VIDEO).click();
            clickById(MePage.ACTIVITIES_CONTENT);
            waitTime(3);
            if (!text_exists("Address(es) already added")) {
                clickById(MePage.ADD_LIVE_STREAN_ADDRESS);
                clickById(MePage.PUT_RTMP_ADDRESS);
                String input_address = getRandomString(20); //输入随机字符地址
                shellInputText(input_address);
                clickById(MePage.SAVE_RTMP_ADDRESS); //点击保存
                Asst.assertTrue("comments success", getUiObjectByTextContains(input_address).exists());
            }
        }
    }
    /**
     * 删除添加的rtmp地址
     *creat by zyj 2017.12.27 */
    @Test
    @SanityTest
    @PerformanceTest
    public void testDeleteRtmpAddress() throws UiObjectNotFoundException, IOException {
        String str=MeAction.getPrivacy(); //获取可见性
        if (!str.equals("Public")) {
            clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
            MeAction.setToPublic();
            clickById(MePage.LIVE_CONFIGURATION_DONE_PRIVACY);
            waitTime(3);
            MeAction.delRtmpAddress();
        }else {
            MeAction.delRtmpAddress();
        }
    }
    /**
     * 会员添加5个rtmp地址
     * *creat by zyj 2017.12.29*/
    @Test
    @SanityTest
    @PerformanceTest
    public void testVipAddRtmpAddress() throws UiObjectNotFoundException, IOException {
        boolean vip = id_exists(MePage.ID_VIP);
        String str=MeAction.getPrivacy(); //获取可见性
            if (vip){
                if (!str.equals("Public")) {
                    clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
                    MeAction.setToPublic();  //设置为公开
                    clickById(MePage.LIVE_CONFIGURATION_DONE_PRIVACY);
                    waitTime(3);
                    MeAction.addRtmpAddress();  //进入添加1个地址
                    while (id_exists(MePage.ADD_LIVE_STREAN_ADDRESS)){
                        //再添加多个地址，直至不能添加
                       MeAction.addManyRtmpAddress();
                    }
                }else{
                    MeAction.addRtmpAddress();
                    while (id_exists(MePage.ADD_LIVE_STREAN_ADDRESS)){
                        MeAction.addManyRtmpAddress();
                    }
                }
                Asst.assertTrue("添加成功",!id_exists(MePage.ADD_LIVE_STREAN_ADDRESS));
            }
    }
    /**非会员添加多个地址（弹出开通会员的提示）
     * *creat by zyj 2017.12.29 */
    @Test
    @SanityTest
    @PerformanceTest
    public void testUnVipAddMoreAddress() throws UiObjectNotFoundException, IOException {
        boolean vip = id_exists(MePage.ID_VIP);
        String str = MeAction.getPrivacy(); //获取可见性
        if (!vip) {
            if (!str.equals("Public")) {
                clickById(MePage.LIVE_CONFIGURATION_PRIVACY_SETTINGS);
                MeAction.setToPublic();  //设置为公开
                clickById(MePage.LIVE_CONFIGURATION_DONE_PRIVACY);
                waitTime(3);
                getObject2ById(MePage.LIVE_CONFIGURATION_SLV_VIDEO).click();
                clickById(MePage.ACTIVITIES_CONTENT);
                waitTime(3);
                if (!text_exists("Address(es) already added")) {
                    MeAction.addManyRtmpAddress();  //进入添加1个地址
                    clickById(MePage.ADD_LIVE_STREAN_ADDRESS);
                } else {
                    clickById(MePage.ADD_LIVE_STREAN_ADDRESS);
                }
            } else {
                getObject2ById(MePage.LIVE_CONFIGURATION_SLV_VIDEO).click();
                clickById(MePage.ACTIVITIES_CONTENT);
                waitTime(3);
                if (!text_exists("Address(es) already added")) {
                    MeAction.addManyRtmpAddress();  //进入添加1个地址
                    clickById(MePage.ADD_LIVE_STREAN_ADDRESS);
                } else {
                    clickById(MePage.ADD_LIVE_STREAN_ADDRESS);
                }
            }
            Asst.assertTrue("添加成功", !text_exists("You can broadcast simultaneously to thrid party platforms after you become Sioeye VIP."));
        }
    }
}
