package cn.page;

import android.os.Environment;

import org.junit.Assert;

import java.io.File;
import java.util.Random;
import java.util.logging.Logger;

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
    //email-address-qiang.zhang
    public static  final  String tyokyo_qq_email ="896409120@qq.com";
    //qiuxiaian
    public static  final  String sioEye_id ="991128845";
    public static  final  String qq_mail_address ="991128845@qq.com";
    public static  final  String qq_mail_nick_name ="qiuxia.jian-qq";
    public static  final  String sioeye_id = "qqc123456";
    //permission allow dialog
    public static String PERMISSION_ALLOW="com.android.packageinstaller:id/permission_allow_button";
    //watch search account email->sio eye id
    public static String CORRECT_EMAIL = "123qq@163.com";
    public static String CORRECT_EMAIL_SIOEYE_ID="@qwe123";
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
    private static Logger logger=Logger.getLogger(Constant.class.getName());

    public static String getUserName(){
        String config= Environment.getExternalStorageDirectory()+ File.separator+"config.properties";
        logger.info(config);
        logger.info(userName);
        String userName=Property.getValueByKey(config,"user_name");
        logger.info("userName:"+userName);
        return userName;
    }

    public static String getPassword(){
        String config= Environment.getExternalStorageDirectory()+ File.separator+"config.properties";
        String user_password=Property.getValueByKey(config,"user_password");
        logger.info(config);
        logger.info("user_password:"+user_password);
        return user_password;
    }

    public static String getUserName(String userNameType){
        String nameType=userNameType;
        String config= Environment.getExternalStorageDirectory()+ File.separator+"config.properties";
        logger.info(config);
        logger.info(userName);
        String userName=Property.getValueByKey(config,nameType);
        logger.info("userName:"+userName);
        if (userName.length()< 4){
        Assert.fail("账号名字长度小于4，请检查config.properties文件账号是否配置正确");
        }
        return userName;
    }
    public static String getPassword(String userPasswordType) {
        String passwordType=userPasswordType;
        String config = Environment.getExternalStorageDirectory() + File.separator + "config.properties";
        String userPassword = Property.getValueByKey(config, passwordType);
        logger.info(config);
        logger.info("userPassword:" + userPassword);
        if (userPassword.length()<1||userPassword==null){
            Assert.fail("密码为空，请检查config.properties文件密码是否配置正确");
        }
        return userPassword;
    }
    public static  String randomStringGenerator(){
        int count=(int)(1+Math.random()*(18-1+1));
        char[] alphaArray = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'X', 'Y', 'Z','0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        String randomSequence = "";
        for (int i = 0; i < count; i++) {
            Character c = Character.valueOf(alphaArray[new Random().nextInt(alphaArray.length)]);
            randomSequence = randomSequence + c.toString();
        }
        logger.info("randomString:"+ randomSequence);
        return randomSequence;
    }

}
