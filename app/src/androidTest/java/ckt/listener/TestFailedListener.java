package ckt.listener;

import com.squareup.spoon.Spoon;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.util.logging.Logger;

/**
 * Created by elon on 2017/1/5.
 * 监控测试的执行
 * 当执行失败的时候自动截图
 */
public class TestFailedListener extends RunListener {
    private static Logger logger=Logger.getLogger(TestFailedListener.class.getName());

    @Override
    public void testRunStarted(Description description) throws Exception {
        super.testRunStarted(description);
        logger.info("testRunStarted-" +description.getMethodName());
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        super.testRunFinished(result);
        logger.info("wasSuccessful-" +result.wasSuccessful());
    }

    @Override
    public void testStarted(Description description) throws Exception {
        super.testStarted(description);
        logger.info("testStarted-" +description.getMethodName());

    }

    @Override
    public void testFinished(Description description) throws Exception {
        super.testFinished(description);
        String className=description.getClassName();
        String methodName=description.getMethodName();
        //Spoon.screenshot("testFinished",className,methodName);
        logger.info("testFinished-" +className+"."+methodName);
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        super.testFailure(failure);
        String className=failure.getDescription().getClassName();
        String methodName=failure.getDescription().getMethodName();
        Spoon.screenshot("failure",className,methodName);
        logger.info("testFailure-" +className+"."+methodName);
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        super.testAssumptionFailure(failure);
    }

    @Override
    public void testIgnored(Description description) throws Exception {
        super.testIgnored(description);
    }
}
