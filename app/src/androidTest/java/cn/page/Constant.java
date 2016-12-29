package cn.page;

import android.os.Environment;

import junit.framework.Assert;

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
        Assert.fail("userName.length<4,Please check the Config.properties configuration");
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
            Assert.fail("Password is null,Please check the Config.properties configuration");
        }
        return userPassword;
    }
    /*
    String randomStringGenerator(int length)产生随机字符串，int length定义了该字符串最大可能长度
     */
    public static  String randomStringGenerator(int length){
        int count=(int)(1+Math.random()*(length-1+1));
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
    public static String randomPhoneNumber() {
        String phoneNumber="";
        char[]alphaNumber={'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        String[]alphaTelTitle={"130","131","132","133","134","135","136","137","138","139", "150",
                "151","152","153","155","156","157","158","159","180","181","183","186","187","188","189","170"};
        String phoneTitle= alphaTelTitle[new Random().nextInt(alphaTelTitle.length)];
        phoneNumber=phoneNumber+phoneTitle;
        for(int i=0;i<8;i++){
            Character ranNumber=Character.valueOf(alphaNumber[new Random().nextInt(alphaNumber.length)]);
            phoneNumber=phoneNumber+ranNumber.toString();
        }
        System.out.println(phoneNumber);
        return phoneNumber;
    }
    public static String randomEmail(int length) {
        int count=(int)(5+Math.random()*(length-5));
        int count1=(int)(2+Math.random()*(count-4));
        int count2=(int)(2+Math.random()*(count-count1-2));
        String randomEmail="";
        char[]alphaChar={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o',
                'p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G',
                'H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y',
                'Z','0','1','2','3','4','5','6','7','8','9'};
        for(int i=1;i<count1;i++){
            Character ranChar=Character.valueOf(alphaChar[new Random().nextInt(alphaChar.length)]);
            randomEmail=randomEmail+ranChar.toString();}
        randomEmail=randomEmail+"@";
        for(int a=count1;a<count2+count1-1;a++){
            Character ranChar=Character.valueOf(alphaChar[new Random().nextInt(alphaChar.length)]);
            randomEmail=randomEmail+ranChar.toString();}
        randomEmail=randomEmail+".";
        for(int i=count2+count1;i<=count-1;i++){
            Character ranChar=Character.valueOf(alphaChar[new Random().nextInt(alphaChar.length)]);
            randomEmail=randomEmail+ranChar.toString();}
        System.out.println(randomEmail);
        return randomEmail;
    }

    /*
     *   产生lengh位随机的错误手机号码，其中lengh不能等于11
     */

    public static String randomErrPhoneNumber(int lengh) {
        String phoneNumber="";
        char[]alphaNumber={'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        String[]alphaTelTitle={"130","131","132","133","134","135","136","137","138","139", "150",
                "151","152","153","155","156","157","158","159","180","181","183","186","187","188","189","170"};
        String phoneTitle= alphaTelTitle[new Random().nextInt(alphaTelTitle.length)];
        phoneNumber=phoneNumber+phoneTitle;
        for(int i=0;i<lengh-3;i++){
            Character ranNumber=Character.valueOf(alphaNumber[new Random().nextInt(alphaNumber.length)]);
            phoneNumber=phoneNumber+ranNumber.toString();
        }
        System.out.println(phoneNumber);
        return phoneNumber;
    }

}
