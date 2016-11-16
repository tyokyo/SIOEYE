package cn.page;

import android.os.Environment;

import java.io.File;

import ckt.tools.Property;

/**
 * Created by elon on 2016/10/28.
 */
public class Constant {
    //Each-user-need: Mobile number account
    public static  final  String mobile_number ="13547946743";
    public static  final  String mobile_password ="123456789";
    public static  final  String mobile_sio_id ="tyo6743";

    //Each-user-need:  sio-eye account
    public static  final  String userName ="tyokyo@126.com";
    public static  final  String passwd   = "123456789";

    //Each-user-need:  account
    public static  final  String error_userName ="tyokyo@126.com";
    public static  final  String error_passwd   = "0123456789";

    //permission allow dialog
    public static String PERMISSION_ALLOW="com.android.packageinstaller:id/permission_allow_button";
    //email-address-qiang.zhang
    public static  final  String tyokyo_qq_email ="896409120@qq.com";

    //watch search account email->sio eye id
    public static String CORRECT_EMAIL = "123qq@163.com";
    public static String CORRECT_EMAIL_SIOEYE_ID="@qwe123";

    //qiuxiaian
    public static  final  String sioEye_id ="991128845";
    public static  final  String qq_mail_address ="991128845@qq.com";
    public static  final  String qq_mail_nick_name ="qiuxia.jian-qq";
    public static  final  String sioeye_id = "qqc123456";

    public static String getUserName(){
        String config= Environment.getExternalStorageDirectory()+ File.separator+"config.properties";
        String userName=Property.getValueByKey(config,"user_name");
        return userName;
    }
    public static String getPassword(){
        String config= Environment.getExternalStorageDirectory()+ File.separator+"config.properties";
        String userName=Property.getValueByKey(config,"user_password");
        return userName;
    }
    //nick name for search
    public static String CORRECT_NICK_NAME = "tyokyo";
    //nick name for search
    public static String WRONG_NICK_NAME = "QWEJHI";
    //nick name for eye id
    public static String CORRECT_SIO_EYE_ID = "tyo000";
    //nick name for eye id
    public static String WRONG_SIO_EYE_ID = "QWEJHI";

    //search account with wrong email address
    public static String WRONG_EMAIL = "123pp@178.com";
}
