package cn.page;

import android.os.Environment;

import java.io.File;

import ckt.tools.Property;

/**
 * Created by elon on 2016/10/28.
 */
public class Constant {
    //sio-eye account
    public static  final  String userName ="tyokyo@126.com";
    public static  final  String passwd   = "123456789";

    //sio-eye error account
    public static  final  String error_userName ="tyokyo@126.com";
    public static  final  String error_passwd   = "0123456789";

    //permission allow dialog
    public static String PERMISSION_ALLOW="com.android.packageinstaller:id/permission_allow_button";

    //qiuxiaian
    public static  final  String sioEye_id ="991128845";
    public static  final  String qq_mail_address ="991128845@qq.com";
    public static  final  String qq_mail_nick_name ="qiuxia.jian-qq";
    public static  final  String sioeye_id = "qqc123456";

    public static String getUserName(){
        String config= Environment.getExternalStorageDirectory()+ File.separator+"config.1properties";
        String userName=Property.getValueByKey(config,"user_name");
        return userName;
    }
    public static String getPassword(){
        String config= Environment.getExternalStorageDirectory()+ File.separator+"config.1properties";
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
    //nick name for email
    public static String CORRECT_EMAIL = "123qq@163.com";
    //nick name for email
    public static String WRONG_EMAIL = "123pp@178.com";
}
