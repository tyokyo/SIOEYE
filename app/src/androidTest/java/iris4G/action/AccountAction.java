package iris4G.action;

import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import ckt.base.VP2;
import cn.page.Constant;
import iris4G.page.Iris4GPage;

public class AccountAction extends VP2 {
    /**
     * Click com.hicam:id/logout_btn按钮
     */
    public static void logout_btn() throws Exception {
        clickById(Iris4GPage.logout_btn);
    }

    /**
     * 输入 com.hicam:id/login_id_input
     */
    public static void login_id_input(String username) throws Exception {
        setText(Iris4GPage.login_id_input, username);
    }

    /**
     * 输入 com.hicam:id/login_password_input
     */
    public static void login_password_input(String password) throws Exception {
        setText(Iris4GPage.login_password_input, password);
    }

    /**
     * Click com.hicam:id/logout_btn按钮
     */
    public static void login_btn_login() throws Exception {
        clickById(Iris4GPage.login_btn_login);
    }
    public static void logOut() throws Exception {
        CameraAction.navConfig(Iris4GPage.nav_menu[0]);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Account");
        clickByText("Account");
        if (id_exists(Iris4GPage.logout_btn)){
            logout_btn();
        }
        gDevice.pressBack();
    }
    public static void loginAccount(String username, String password) throws Exception {
        CameraAction.navConfig(Iris4GPage.nav_menu[0]);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Account");
        clickByText("Account");
        login_id_input(username);
        login_password_input(password);
        login_btn_login();
        boolean login = AccountAction.isLoginSuccess();
        if (login) {
            logger.info("account login success");
        }else {
            logger.info("account login fail");
        }
        gDevice.pressBack();
    }

    public static boolean isLoginSuccess() throws UiObjectNotFoundException {
        boolean isSuccess = false;
        for (int i = 0; i < 20; i++) {
            if (text_exists_match("^Account")) {
                isSuccess = true;
                break;
            } else {
                logger.info("Login wait 1 seconds");
                waitTime(1);
            }
        }
        Spoon.screenshot("Login");
        return isSuccess;
    }
}
