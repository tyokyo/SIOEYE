package com.sioeye.elon;

/**
 * Created by qiang.zhang on 2017/1/9.
 */
import junit.framework.TestSuite;

public class MyTestSuite extends TestSuite {
    public MyTestSuite() {
        addTestSuite(MyTestCase.class );
    }
}