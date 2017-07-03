/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.support.test.uiautomator;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;

import java.util.logging.Logger;

/**
 * Generated in test runs when a {@link UiSelector} selector could not be matched
 * to any UI element displayed.
 * @since API Level 16
 */
public class UiObjectNotFoundException extends Exception {
    Logger logger = Logger.getLogger(UiObjectNotFoundException.class.getName());
    private static final long serialVersionUID = 1L;

    /**
     * @since API Level 16
     **/
    public UiObjectNotFoundException(String msg) {
        super(msg);
        logger.info("UiObjectNotFoundException->"+msg);
        Asst.fail("UiObjectNotFoundException:"+msg);
        Spoon.screenshot("UiObjectNotFoundException",msg);
    }

    /**
     * @since API Level 16
     **/
    public UiObjectNotFoundException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        logger.info("UiObjectNotFoundException->"+detailMessage+throwable.toString());
        Asst.fail("UiObjectNotFoundException:"+detailMessage+throwable.toString());
        Spoon.screenshot("throwable"+System.currentTimeMillis());
    }

    /**
     * @since API Level 16
     **/
    public UiObjectNotFoundException(Throwable throwable) {
        super(throwable);
        logger.info("UiObjectNotFoundException->"+throwable.toString());
        Asst.fail("UiObjectNotFoundException:"+throwable.toString());
        Spoon.screenshot("UiObjectNotFoundException",throwable.toString());
    }

}
