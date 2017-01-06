package ckt.listener;

import android.test.AndroidTestRunner;

import org.junit.runner.JUnitCore;

/**
 * Created by elon on 2017/1/5.
 */
public class MainExcuteRunner {
    public void main(String args[]) {
        JUnitCore core = new JUnitCore();
        core.addListener(new TestFailedListener());
        core.run(cn.testcase.me.LocationCase.class);
        /*整个代码的核心*/
        AndroidTestRunner testRunner = new AndroidTestRunner();
        testRunner.setTestClassName("MoreagreeCase","testMethodXXX");
        testRunner.addTestListener(new AppFailedListener());
        testRunner.setContext( null );
        testRunner.runTest();
    }
}
