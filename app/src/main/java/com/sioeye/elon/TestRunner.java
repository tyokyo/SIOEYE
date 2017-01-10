package com.sioeye.elon;

import android.app.Activity;
import android.test.AndroidTestRunner;
import android.util.Log;
import android.widget.TextView;

import com.sioeye.R;

import junit.framework.Test;
import junit.framework.TestListener;

/**
 * Created by qiang.zhang on 2017/1/9.
 */
public class TestRunner implements Runnable,TestListener {
    static final String LOG_TAG = "TestRunner";
    int testCounter;
    int errorCounter;
    int failureCounter;
    TextView statusText;
    TextView testCounterText;
    TextView errorCounterText;
    TextView failureCounterText;
    Activity parentActivity;

    public TestRunner( Activity parentActivity ) {
        this.parentActivity = parentActivity;
    }

    public void run() {
        testCounter = 0;
        errorCounter = 0;
        failureCounter = 0;
        statusText = (TextView)parentActivity.
                findViewById( R.id.status );
        testCounterText = (TextView)parentActivity.
                findViewById( R.id.testCounter );
        errorCounterText = (TextView)parentActivity.
                findViewById( R.id.errorCounter );
        failureCounterText = (TextView)parentActivity.
                findViewById( R.id.failureCounter );
        Log.d( LOG_TAG, "Test started" );
        AndroidTestRunner testRunner = new AndroidTestRunner();
        testRunner.setTest( new MyTestSuite() );
        testRunner.addTestListener( this );
        testRunner.setContext( parentActivity );
        testRunner.runTest();
        Log.d( LOG_TAG, "Test ended" );
    }

// TestListener

    public void addError(Test test, Throwable t) {
        Log.d( LOG_TAG, "addError: "+test.getClass().getName() );
        Log.d( LOG_TAG, t.getMessage(), t );
        ++errorCounter;
        TestDisplay td = new TestDisplay(
                TestDisplay.displayEvent.ERROR,
                test.getClass().getName(),
                testCounter,
                errorCounter,
                failureCounter,
                statusText,
                testCounterText,
                errorCounterText,
                failureCounterText );
        parentActivity.runOnUiThread( td );
    }

    public void endTest(Test test) {
        Log.d( LOG_TAG, "endTest: "+test.getClass().getName() );
        TestDisplay td = new TestDisplay(
                TestDisplay.displayEvent.END_TEST,
                test.getClass().getName(),
                testCounter,
                errorCounter,
                failureCounter,
                statusText,
                testCounterText,
                errorCounterText,
                failureCounterText );
        parentActivity.runOnUiThread( td );
    }

    public void startTest(Test test) {
        Log.d( LOG_TAG, "startTest: "+test.getClass().getName() );
        ++testCounter;
        TestDisplay td = new TestDisplay(
                TestDisplay.displayEvent.START_TEST,
                test.getClass().getName(),
                testCounter,
                errorCounter,
                failureCounter,
                statusText,
                testCounterText,
                errorCounterText,
                failureCounterText );
        parentActivity.runOnUiThread( td );
    }

    @Override
    public void addFailure(Test test, junit.framework.AssertionFailedError t) {
        // TODO Auto-generated method stub

        Log.d( LOG_TAG, "addFailure: "+test.getClass().getName() );
        Log.d( LOG_TAG, t.getMessage(), t );
        ++failureCounter;
        TestDisplay td = new TestDisplay(
                TestDisplay.displayEvent.FAILURE,
                test.getClass().getName(),
                testCounter,
                errorCounter,
                failureCounter,
                statusText,
                testCounterText,
                errorCounterText,
                failureCounterText );
        parentActivity.runOnUiThread( td );
    }
}
