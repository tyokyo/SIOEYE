package com.sioeye;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class AppInfoReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle=intent.getExtras();
		String packageName=bundle.getString("pn");
		String fileName=bundle.getString("fn");
		
		PackageManager pm=context.getPackageManager();
		
		try {
			PackageInfo manifest=pm.getPackageInfo(packageName, 0);
		    String versionName=manifest.versionName;
		    String apkName=pm.getApplicationLabel(pm.getApplicationInfo(packageName, 0)).toString();
		    writeFile("/mnt/sdcard",fileName,"versionName:"+versionName);
		    writeFile("/mnt/sdcard",fileName,"vapkName:"+apkName);
		    
		    Intent mainIntent=new Intent(Intent.ACTION_MAIN);
		    mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		    mainIntent.setPackage(packageName);
		    List<ResolveInfo> resolveInfos=pm.queryIntentActivities(mainIntent, PackageManager.MATCH_DEFAULT_ONLY);
		    for(ResolveInfo r:resolveInfos){
		    	String activityName=r.activityInfo.name;
		    	if(activityName.contains(packageName)){
		    		 writeFile("/mnt/sdcard",fileName,"activityName:"+activityName);
		    	}
		    	
		    }
		
		
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		

	}
	public void writeFile(String path, String fileName, String text){
		File file=new File(path+ File.separator+fileName);
		try {
		if(!file.exists()){			
				file.createNewFile();			
		}
		
		FileOutputStream out=new FileOutputStream(file,true);
		OutputStreamWriter ow=new OutputStreamWriter(out);
		BufferedWriter bw=new BufferedWriter(ow);
		
		bw.append(text);
		bw.newLine();
		bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
