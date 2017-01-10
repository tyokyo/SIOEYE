package ckt.listener;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;

/**
 * Created by elon on 2017/1/6.
 */
public class AppFailedListener implements TestListener {
    @Override
    public void addError(Test test, Throwable throwable) {

    }

    @Override
    public void addFailure(Test test, AssertionFailedError assertionFailedError) {

    }

    @Override
    public void endTest(Test test) {

    }

    @Override
    public void startTest(Test test) {

    }
}
