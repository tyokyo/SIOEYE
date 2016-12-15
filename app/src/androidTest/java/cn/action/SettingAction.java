package cn.action;

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
        clickByText("账号与安全");
        Spoon.screenshot("AccountAndPrivacy");
    }
    //意见反馈
    public static void navToFeedBack() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("意见反馈");
        Spoon.screenshot("FeedBack");
    }
    //检查更新
    public static void navToUpdate() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("检查更新");
        Spoon.screenshot("checkUpdate");
    }
    //帮助中心
    public static void navToHelpCenter() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("帮助中心");
        Spoon.screenshot("HelpCenter");
    }
    //设置
    public static void navToSetting() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        Spoon.screenshot("HelpCenter");
    }
    //关于SioEye
    public static void navToAboutSioEye() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("关于Sioeye");
        Spoon.screenshot("AboutSioeye");
    }
    //帮助中心->服务条款
    public static void navToHP_TermService() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("帮助中心");
        clickById(MePage.HELP_SERVICE);
        Spoon.screenshot("HELP_SERVICE");
    }
    //帮助中心->隐私策略
    public static void navToHP_Privacy() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("帮助中心");
        clickById(MePage.HELP_POLICY);
        Spoon.screenshot("HELP_POLICY");
    }
    //帮助中心->最终用户协议
    public static void navToHP_UserProtocol() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("帮助中心");
        clickById(MePage.HELP_EMULA);
        Spoon.screenshot("HELP_EMULA");
    }
    //帮助中心->帮助
    public static void navToHP_Help() throws UiObjectNotFoundException {
        MeAction.navToSettings();
        clickByText("帮助中心");
        clickById(MePage.HELP_HELP);
        Spoon.screenshot("HELP_HELP");
    }

}
