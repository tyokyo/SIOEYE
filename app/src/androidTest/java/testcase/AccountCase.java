package testcase;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Environment;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sioeye.MainActivity;
import com.squareup.spoon.Spoon;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.File;
import java.util.logging.Logger;
import ckt.VP2;
import page.App;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class AccountCase extends VP2 {
	@Before
	public  void setup(){
		killAppByPackage(App.SIOEYE_PACKAGE_NAME);
	}

	@Rule
	public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
			MainActivity.class);
	@Test
	public void testLogin() throws Exception{
		//logStart();
		initDevice();
		openAppByActivity(App.LAUNCH_SIOEYE);
		Thread.sleep(10000);
		Spoon.screenshot(mActivityRule.getActivity(),"open_app");

		//logStop();
		//Spoon.save(instrument.getContext(), logAbsPath);
		Logger.getLogger("LOGGER").info("spoon save log file "+logAbsPath);

	}

}
