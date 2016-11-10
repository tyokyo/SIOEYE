package ckt.tools;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

public class Property {
	public static Hashtable<String, String> getProperties(String  filePath) throws IOException {
		Properties pps = new Properties();
		pps.load(new FileInputStream(Environment.getExternalStorageDirectory()+"properties/config.properties"));
		Enumeration<?>  enum1 = pps.propertyNames();
		Hashtable<String, String> prop = new Hashtable<String, String>();
		while(enum1.hasMoreElements()) {
			String strKey = (String) enum1.nextElement();
			String strValue = pps.getProperty(strKey);
			prop.put(strKey, strValue);
			System.out.println(strKey + "=" + strValue);
		}
		return prop;
	}
	public static String getValueByKey(String filePath, String key) {
		Properties pps = new Properties();
		File file=new File(filePath);
		if (file.exists()){
			try {
				InputStream in = new BufferedInputStream (new FileInputStream(filePath));
				pps.load(in);
				String value = pps.getProperty(key);
				System.out.println(key + " = " + value);
				return value;
			}catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}else{
			return null;
		}
	}
}
