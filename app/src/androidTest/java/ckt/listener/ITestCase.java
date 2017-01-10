package ckt.listener;

/**
 * Created by qiang.zhang on 2017/1/8.
 */
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ITestCase extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        //suite.addTestSuite(TestCalcular.class);
        //suite.addTestSuite(TestMyStack.class);

        //suite.addTest(new RepeatedTest(new TestMyStack("testPushNormal1"), 20));
        //调用RepeatedTest，传入要重复运行的类的方法。
        return suite;
    }

}
