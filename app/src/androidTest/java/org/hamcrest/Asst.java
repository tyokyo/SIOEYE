package org.hamcrest;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.squareup.spoon.Spoon;

import org.junit.ComparisonFailure;
import org.junit.internal.ArrayComparisonFailure;
import org.junit.internal.ExactComparisonCriteria;
import org.junit.internal.InexactComparisonCriteria;

import java.util.logging.Logger;

import ckt.base.VP2;

public class Asst extends VP2{
    //permission allow dialog
    public static String PERMISSION_ALLOW="com.android.packageinstaller:id/permission_allow_button";

    private static Logger logger = Logger.getLogger(Asst.class.getName());
    protected Asst() {
    }
    public static void assertTrue(String message, boolean condition) {
        if(!condition) {
            fail(message);
        }
    }

    public static void assertTrue(boolean condition) {
        assertTrue(null, condition);
    }

    public static void assertFalse(String message, boolean condition) {
        assertTrue(message, !condition);
    }

    public static void assertFalse(boolean condition) {
        assertFalse(null, condition);
    }

    public static void fail(String message){
        //Spoon.screenshot(gDevice,"fail","message is null");
        logger.info("Now-Fail-Message");
        try {
            String ANR="";
            if (getObjectById("android:id/message").exists()){
                ANR=getObjectById("android:id/message").getText();
            }
            if (getObjectByTextContains("Unfortunately").exists()
                    ||getObjectByTextContains("停止运行").exists()) {
                //Exception_Crash
                message="Exception_Crash->"+message;
                message=ANR+"-"+message;

                Spoon.screenshot("Crash",message==null?"message is null":message);
                logger.info("Exception_Crash");
                Spoon.screenshot("Exception_Crash");
                clickByText("OK");

            }else if(getObjectByTextContains("isn't responding").exists()
                    ||getObjectByTextContains("无响应").exists()
                    ){
                //Exception_ANR
                message="Exception_ANR->"+message;
                message=ANR+"-"+message;
                Spoon.screenshot("fail",message==null?"message is null":message);
                logger.info("Exception_ANR");
                Spoon.screenshot("Exception_ANR");
                clickByText("OK");
            }else{
                Spoon.screenshot("fail",message==null?"message is null":message);
            }
            //permission dialog
            UiObject uiObject = gDevice.findObject(new UiSelector().resourceId(PERMISSION_ALLOW));
            if (uiObject.exists()){
                logger.info("click allow-permission setting");
                uiObject.clickAndWaitForNewWindow();
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        if(message == null) {
            throw new AssertionError();
        } else {
            throw new AssertionError(message);
        }
    }

    public static void fail() {
        fail(null);
    }

    public static void assertEquals(String message, Object expected, Object actual){
        if(!equalsRegardingNull(expected, actual)) {
            if(expected instanceof String && actual instanceof String) {
                String cleanMessage = message == null?"":message;
                String ANR="";
                if (getObjectById("android:id/message").exists()){
                    try {
                        ANR=getObjectById("android:id/message").getText();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (getObjectByTextContains("Unfortunately").exists()) {
                        //Exception_Crash
                        message="Exception_Crash->"+message;
                        message=ANR+"-"+message;

                        Spoon.screenshot("fail",message==null?"message is null":message);
                        logger.info("Exception_Crash");
                        Spoon.screenshot("Exception_Crash");
                        clickByText("OK");

                    }else if(getObjectByTextContains("isn't responding").exists()){
                        //Exception_ANR
                        message="Exception_ANR->"+message;
                        message=ANR+"-"+message;
                        Spoon.screenshot("fail",message==null?"message is null":message);
                        logger.info("Exception_ANR");
                        Spoon.screenshot("Exception_ANR");
                        clickByText("OK");
                    }else{
                        Spoon.screenshot("fail",message==null?"message is null":message);
                    }
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }

                throw new ComparisonFailure(cleanMessage, (String)expected, (String)actual);
            } else {
                failNotEquals(message, expected, actual);
            }
        }
    }

    private static boolean equalsRegardingNull(Object expected, Object actual) {
        return expected == null?actual == null:isEquals(expected, actual);
    }

    private static boolean isEquals(Object expected, Object actual) {
        return expected.equals(actual);
    }

    public static void assertEquals(Object expected, Object actual) {
        assertEquals(null, expected, actual);
    }

    public static void assertNotEquals(String message, Object unexpected, Object actual) {
        if(equalsRegardingNull(unexpected, actual)) {
            failEquals(message, actual);
        }

    }

    public static void assertNotEquals(Object unexpected, Object actual) {
        assertNotEquals(null, unexpected, actual);
    }

    private static void failEquals(String message, Object actual) {
        String formatted = "Values should be different. ";
        if(message != null) {
            formatted = message + ". ";
        }

        formatted = formatted + "Actual: " + actual;
        fail(formatted);
    }

    public static void assertNotEquals(String message, long unexpected, long actual) {
        if(unexpected == actual) {
            failEquals(message, Long.valueOf(actual));
        }

    }

    public static void assertNotEquals(long unexpected, long actual) {
        assertNotEquals(null, unexpected, actual);
    }

    public static void assertNotEquals(String message, double unexpected, double actual, double delta) {
        if(!doubleIsDifferent(unexpected, actual, delta)) {
            failEquals(message, Double.valueOf(actual));
        }

    }

    public static void assertNotEquals(double unexpected, double actual, double delta) {
        assertNotEquals(null, unexpected, actual, delta);
    }

    public static void assertNotEquals(float unexpected, float actual, float delta) {
        assertNotEquals(null, unexpected, actual, delta);
    }

    public static void assertArrayEquals(String message, Object[] expecteds, Object[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    public static void assertArrayEquals(Object[] expecteds, Object[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    public static void assertArrayEquals(String message, boolean[] expecteds, boolean[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    public static void assertArrayEquals(boolean[] expecteds, boolean[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    public static void assertArrayEquals(String message, byte[] expecteds, byte[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    public static void assertArrayEquals(byte[] expecteds, byte[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    public static void assertArrayEquals(String message, char[] expecteds, char[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    public static void assertArrayEquals(char[] expecteds, char[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    public static void assertArrayEquals(String message, short[] expecteds, short[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    public static void assertArrayEquals(short[] expecteds, short[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    public static void assertArrayEquals(String message, int[] expecteds, int[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    public static void assertArrayEquals(int[] expecteds, int[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    public static void assertArrayEquals(String message, long[] expecteds, long[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    public static void assertArrayEquals(long[] expecteds, long[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    public static void assertArrayEquals(String message, double[] expecteds, double[] actuals, double delta) throws ArrayComparisonFailure {
        (new InexactComparisonCriteria(delta)).arrayEquals(message, expecteds, actuals);
    }

    public static void assertArrayEquals(double[] expecteds, double[] actuals, double delta) {
        assertArrayEquals(null, expecteds, actuals, delta);
    }

    public static void assertArrayEquals(String message, float[] expecteds, float[] actuals, float delta) throws ArrayComparisonFailure {
        (new InexactComparisonCriteria(delta)).arrayEquals(message, expecteds, actuals);
    }

    public static void assertArrayEquals(float[] expecteds, float[] actuals, float delta) {
        assertArrayEquals(null, expecteds, actuals, delta);
    }

    private static void internalArrayEquals(String message, Object expecteds, Object actuals) throws ArrayComparisonFailure {
        (new ExactComparisonCriteria()).arrayEquals(message, expecteds, actuals);
    }

    public static void assertEquals(String message, double expected, double actual, double delta) {
        if(doubleIsDifferent(expected, actual, delta)) {
            failNotEquals(message, Double.valueOf(expected), Double.valueOf(actual));
        }

    }

    public static void assertEquals(String message, float expected, float actual, float delta) {
        if(floatIsDifferent(expected, actual, delta)) {
            failNotEquals(message, Float.valueOf(expected), Float.valueOf(actual));
        }

    }

    public static void assertNotEquals(String message, float unexpected, float actual, float delta) {
        if(!floatIsDifferent(unexpected, actual, delta)) {
            failEquals(message, Float.valueOf(actual));
        }

    }

    private static boolean doubleIsDifferent(double d1, double d2, double delta) {
        return Double.compare(d1, d2) == 0?false:Math.abs(d1 - d2) > delta;
    }

    private static boolean floatIsDifferent(float f1, float f2, float delta) {
        return Float.compare(f1, f2) == 0?false:Math.abs(f1 - f2) > delta;
    }

    public static void assertEquals(long expected, long actual) {
        assertEquals(null, expected, actual);
    }

    public static void assertEquals(String message, long expected, long actual) {
        if(expected != actual) {
            failNotEquals(message, Long.valueOf(expected), Long.valueOf(actual));
        }

    }

    /** @deprecated */
    @Deprecated
    public static void assertEquals(double expected, double actual) {
        assertEquals(null, expected, actual);
    }

    /** @deprecated */
    @Deprecated
    public static void assertEquals(String message, double expected, double actual) {
        fail("Use assertEquals(expected, actual, delta) to compare floating-point numbers");
    }

    public static void assertEquals(double expected, double actual, double delta) {
        assertEquals(null, expected, actual, delta);
    }

    public static void assertEquals(float expected, float actual, float delta) {
        assertEquals(null, expected, actual, delta);
    }

    public static void assertNotNull(String message, Object object) {
        assertTrue(message, object != null);
    }

    public static void assertNotNull(Object object) {
        assertNotNull(null, object);
    }

    public static void assertNull(String message, Object object) {
        if(object != null) {
            failNotNull(message, object);
        }
    }

    public static void assertNull(Object object) {
        assertNull(null, object);
    }

    private static void failNotNull(String message, Object actual) {
        String formatted = "";
        if(message != null) {
            formatted = message + " ";
        }

        fail(formatted + "expected null, but was:<" + actual + ">");
    }

    public static void assertSame(String message, Object expected, Object actual) {
        if(expected != actual) {
            failNotSame(message, expected, actual);
        }
    }

    public static void assertSame(Object expected, Object actual) {
        assertSame(null, expected, actual);
    }

    public static void assertNotSame(String message, Object unexpected, Object actual) {
        if(unexpected == actual) {
            failSame(message);
        }

    }

    public static void assertNotSame(Object unexpected, Object actual) {
        assertNotSame(null, unexpected, actual);
    }

    private static void failSame(String message) {
        String formatted = "";
        if(message != null) {
            formatted = message + " ";
        }

        fail(formatted + "expected not same");
    }

    private static void failNotSame(String message, Object expected, Object actual) {
        String formatted = "";
        if(message != null) {
            formatted = message + " ";
        }

        fail(formatted + "expected same:<" + expected + "> was not:<" + actual + ">");
    }

    private static void failNotEquals(String message, Object expected, Object actual) {
        fail(format(message, expected, actual));
    }

    static String format(String message, Object expected, Object actual) {
        String formatted = "";
        if(message != null && !message.equals("")) {
            formatted = message + " ";
        }

        String expectedString = String.valueOf(expected);
        String actualString = String.valueOf(actual);
        return expectedString.equals(actualString)?formatted + "expected: " + formatClassAndValue(expected, expectedString) + " but was: " + formatClassAndValue(actual, actualString):formatted + "expected:<" + expectedString + "> but was:<" + actualString + ">";
    }

    private static String formatClassAndValue(Object value, String valueString) {
        String className = value == null?"null":value.getClass().getName();
        return className + "<" + valueString + ">";
    }

    /** @deprecated */
    @Deprecated
    public static void assertEquals(String message, Object[] expecteds, Object[] actuals) {
        assertArrayEquals(message, expecteds, actuals);
    }

    /** @deprecated */
    @Deprecated
    public static void assertEquals(Object[] expecteds, Object[] actuals) {
        assertArrayEquals(expecteds, actuals);
    }

    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat("", actual, matcher);
    }

    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        MatcherAsst.assertThat(reason, actual, matcher);
    }
}
