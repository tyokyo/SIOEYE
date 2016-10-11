package ckt;

import android.content.ContentResolver;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import java.util.logging.Logger;

/**
 * Created by elon on 2016/9/12.
 */
public class SettingHelper extends  VP{
    public static String TAG = VP.class.getName();
    public static void timeout(){
        ContentResolver resolver = context.getContentResolver();
        Logger.getLogger(TAG).info(Settings.System.getString(resolver, Settings.System.SCREEN_OFF_TIMEOUT));
        //Settings.System.putString(resolver, Settings.System.SCREEN_OFF_TIMEOUT,"60000");

        /*int value = Integer.parseInt((String)"60000");
        try {
            Settings.System.putInt(resolver,  Settings.System.SCREEN_OFF_TIMEOUT, value);
        } catch (NumberFormatException e) {
            Logger.getLogger(TAG).info("could not persist screen timeout setting");
        }*/
        //2147483647
        Logger.getLogger(TAG).info(Settings.System.getInt(resolver, Settings.System.SCREEN_OFF_TIMEOUT, 0)+"");

        //Settings.System.putLong(resolver, Settings.System.SCREEN_BRIGHTNESS,125);

        //boolean on = Settings.System.getInt(resolver,Settings.System.SCREEN_OFF_TIMEOUT,0) == 1 ? true : false;
        //Settings.System.SCREEN_OFF_TIMEOUT
    }
    public static void connectWifi(String sSid,String passWd){
        WifiManager wifiManager =(WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiAutoConnectManager wifiAutoConnectManager = new WifiAutoConnectManager(wifiManager);
        for (int i = 0; i <10 ; i++) {
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected())
            {
                break;
            }else{
                wifiAutoConnectManager.connect(sSid,passWd, WifiAutoConnectManager.WifiCipherType.WIFICIPHER_WPA);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
