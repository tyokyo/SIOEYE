package com.sioeye;

import android.app.Activity;
import android.test.AndroidTestRunner;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;
import junit.framework.TestSuite;
import iris4G.testcase.*;
import java.util.logging.Logger;

/**
 * Created by qiang.zhang on 2017/1/8.
 */
public class TestRunner implements Runnable,TestListener {
    Logger logger = Logger.getLogger(TestRunner.class.getName());
    int testCounter;
    int errorCounter;
    int failureCounter;
    Activity parentActivity;
    public TestRunner( Activity parentActivity )
    {
        this.parentActivity = parentActivity;
    }
    public void run()
    {
        testCounter = 0;
        errorCounter = 0;
        failureCounter = 0;
        logger.info("Test started");
        /*整个代码的核心*/
        AndroidTestRunner testRunner = new AndroidTestRunner();
        TestSuite testSuite = new TestSuite();
        //testRunner.setTest(new ExampleSuite() );
        testRunner.addTestListener(this );
        testRunner.setContext( parentActivity );
        testRunner.runTest();
        logger.info("Test ended");
    }
    // TestListener
    public void addError(Test test, Throwable t)
    {
        logger.info("addError-"+test.getClass().getName());
        ++errorCounter;
    }
    public void addFailure(Test test, AssertionFailedError t)
    {
        logger.info("addFailure-"+test.getClass().getName());
        ++failureCounter;
    }
    public void endTest(Test test)
    {
        logger.info("endTest-"+test.getClass().getName());
    }
    public void startTest(Test test)
    {
        logger.info("startTest-"+test.getClass().getName());
        ++testCounter;
    }
}
