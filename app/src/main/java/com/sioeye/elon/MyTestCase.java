package com.sioeye.elon;

/**
 * Created by qiang.zhang on 2017/1/9.
 */
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)

public class MyTestCase extends AndroidTestCase {
    protected int i1;
    protected int i2;
    static final String LOG_TAG = "MathTest";
    @Test
    public void setUp() {
        i1 = 2;
        i2 = 3;
    }
    @Test
    public void testAdd() {
        Log.d( LOG_TAG, "testAdd" );
        assertTrue( LOG_TAG+"1", ( ( i1 + i2 ) == 5 ) );
    }
    @Test
    public void testAndroidTestCaseSetupProperly() {
        super.testAndroidTestCaseSetupProperly();
        Log.d( LOG_TAG, "testAndroidTestCaseSetupProperly" );
    }
    /*@Test
    public void test(){
        Instrumentation instrument = InstrumentationRegistry.getInstrumentation();
        Context context = instrument.getContext();
        UiDevice gDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        gDevice.pressHome();
        gDevice.findObject(By.textContains("Google")).click();
    }*/
}