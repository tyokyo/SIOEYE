package com.sioeye;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by elon on 2016/10/24.
 */
public class FileUtil {
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
