package elon;

/**
 * Created by qiang.zhang on 2017/1/9.
 */
import android.test.AndroidTestCase;
import android.util.Log;

public class MyTestCase extends AndroidTestCase {
    protected int i1;
    protected int i2;
    static final String LOG_TAG = "MathTest";

    public void setUp() {
        i1 = 2;
        i2 = 3;
    }
    public void testAdd() {
        Log.d( LOG_TAG, "testAdd" );
        assertTrue( LOG_TAG+"1", ( ( i1 + i2 ) == 5 ) );
    }

    public void testAndroidTestCaseSetupProperly() {
        super.testAndroidTestCaseSetupProperly();
        Log.d( LOG_TAG, "testAndroidTestCaseSetupProperly" );
    }
}