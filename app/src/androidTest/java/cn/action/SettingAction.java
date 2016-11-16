package cn.action;

import ckt.base.VP2;
import cn.page.MePage;

/**
 * Created by elon on 2016/11/16.
 */
/*设置*/
public class SettingAction extends VP2 {
    //账号和安全
    public static void navToAccountAndPrivacy(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.SETTINGS_USER_MAIN);
        clickByText("账号与安全");
    }
    //意见反馈
    public static void navToFeedBack(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.SETTINGS_USER_MAIN);
        clickByText("意见反馈");
    }
    //检查更新
    public static void navToUpdate(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.SETTINGS_USER_MAIN);
        clickByText("检查更新");
    }
    //帮助中心
    public static void navToHelpCenter(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.SETTINGS_USER_MAIN);
        clickByText("帮助中心");
    }
    //关于SioEye
    public static void navToAboutSioEye(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.SETTINGS_USER_MAIN);
        clickByText("关于Sioeye");
    }
    //帮助中心->服务条款
    public static void navToHP_TermService(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.SETTINGS_USER_MAIN);
        clickByText("帮助中心");
        clickById(MePage.HELP_SERVICE);
    }
    //帮助中心->隐私策略
    public static void navToHP_Privacy(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.SETTINGS_USER_MAIN);
        clickByText("帮助中心");
        clickById(MePage.HELP_POLICY);
    }
    //帮助中心->最终用户协议
    public static void navToHP_UserProtocol(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.SETTINGS_USER_MAIN);
        clickByText("帮助中心");
        clickById(MePage.HELP_EMULA);
    }
    //帮助中心->帮助
    public static void navToHP_Help(){
        clickById(MePage.ID_MAIN_TAB_ME);
        clickById(MePage.SETTINGS_USER_MAIN);
        clickByText("帮助中心");
        clickById(MePage.HELP_HELP);
    }
}
