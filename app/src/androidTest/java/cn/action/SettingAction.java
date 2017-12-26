package cn.action;

import android.support.test.uiautomator.StaleObjectException;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import ckt.base.VP2;
import cn.page.MePage;

/**
 * Created by elon on 2016/11/16.
 */
/*设置*/
public class SettingAction extends VP2 {
    //账号和安全
    public static void navToAccountAndPrivacy() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("Account and Security");
        Spoon.screenshot("AccountAndPrivacy");
    }
    //意见反馈
    public static void navToFeedBack() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("Feedback");
        Spoon.screenshot("FeedBack");
    }
    //检查更新
    public static void navToUpdate() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("Check for updates");
        Spoon.screenshot("checkUpdate");
    }
    //用户协议
    public static void navToAgreement() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("User Agreement");
        Spoon.screenshot("Agreement");
    }
    //黑名单
    public static void navToBlackList() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("Blacklist");
        Spoon.screenshot("Blacklist");
    }
    //推送通知
    public static void navToMessageNotification() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("Message notification");
        Spoon.screenshot("HelpCenter");
    }
    //关于SioEye
    public static void navToAboutSioEye() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("About Sioeye");
        Spoon.screenshot("AboutSioeye");
    }
    //用户协议->服务条款
    public static void navToHP_TermService() throws UiObjectNotFoundException {
        navToAgreement();
        clickById(MePage.HELP_SERVICE);
        Spoon.screenshot("HELP_SERVICE");
    }
    //用户协议->隐私策略
    public static void navToHP_Privacy() throws UiObjectNotFoundException {
        navToAgreement();
        clickById(MePage.HELP_POLICY);
        Spoon.screenshot("HELP_POLICY");
    }
    //用户协议->最终用户协议
    public static void navToHP_UserProtocol() throws UiObjectNotFoundException {
        navToAgreement();
        clickById(MePage.HELP_EMULA);
        Spoon.screenshot("HELP_EMULA");
    }
    //用户协议->用户内容管理条例
    public static void navToHP_UserManagement() throws UiObjectNotFoundException {
        navToAgreement();
        clickById(MePage.HELP_MANAGEMENT);
        Spoon.screenshot("HELP_MANAGEMENT");
    }

    //帮助->常见问题
    public static void navToHP_Help() throws UiObjectNotFoundException {
        clickById(MePage.HELP_HELP);
        Spoon.screenshot("HELP_HELP");
    }
    //帮助->使用指南
    public static void navToUserGuide() throws UiObjectNotFoundException {
        clickById(MePage.USER_GUIDE);
        Spoon.screenshot("USER_GUIDE");
    }
    //从黑名单移除
    public static String removeFromBlackList(){
        UiObject2 object=getObject2ById(MePage.ID_BLACKLIST).getChildren().get(1).getChildren().get(0);
        UiObject2 object1=object.getChildren().get(2);
        String expect_name=object.getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getText();
        object1.click();
        return expect_name;
    }


}
