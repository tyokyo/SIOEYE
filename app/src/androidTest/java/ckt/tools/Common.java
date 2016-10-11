package ckt.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by elon on 2016/9/12.
 */
public class Common {
    private String TAG = Common.class.getName();
    // 得到一个时间的字符串
    /**
     * @return  time to string
     */
    public static String getTimeToString() {
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        // 设置时间对象的格式
        String timeToString = time.format(date);
        return timeToString;
    }
    /**
     * 获得随机字符
     */
    public String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    // 写到sdcard中
    public static void write(byte[] bs, String destPath){

        FileOutputStream out= null;
        try {
            out = new FileOutputStream(new File(destPath));
            out.write(bs);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * 获取指定文件夹下的所有文件列表
     *
     * @param filePath
     *
     */
    public HashSet<String> FileList(String filePath){
        HashSet<String> tSet1 = new HashSet<String>();
        File f = new File(filePath);
        File[] flists = f.listFiles();
        for (File file : flists) {
            tSet1.add(file.getAbsolutePath());
        }
        Logger.getLogger(TAG).info(tSet1.toString());
        return tSet1;
    }
    public static void writeToFile(String FileName,String strContent, boolean isAppended) {
        try {
            File fe = new File(FileName);
            if (!fe.getParentFile().exists()) {
                String dir = fe.getParentFile().getAbsolutePath();
                new File(dir).mkdirs();
            }
            if (!fe.exists()) {
                fe.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(FileName,isAppended);
            strContent=strContent+"\r\n";
            byte[] initContent = strContent.getBytes("GBK");
            fileOutputStream.write(initContent);

            fileOutputStream.close();
            fileOutputStream.flush();
            //Log.info(FileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
