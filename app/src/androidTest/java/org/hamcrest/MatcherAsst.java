package org.hamcrest;

/**
 * Created by elon on 2016/10/24.
 */

    import com.squareup.spoon.Spoon;

    import org.hamcrest.Matcher;
    import org.hamcrest.StringDescription;

    import ckt.base.VP2;

public class MatcherAsst extends VP2{
    public MatcherAsst() {
    }

    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat("", actual, matcher);
    }

    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        if(!matcher.matches(actual)) {
            StringDescription description = new StringDescription();
            description.appendText(reason).appendText("\nExpected: ").appendDescriptionOf(matcher).appendText("\n     but: ");
            matcher.describeMismatch(actual, description);
            Spoon.screenshot("assertThat",description.toString());
            throw new AssertionError(description.toString());
        }
    }

    public static void assertThat(String reason, boolean assertion) {
        if(!assertion) {
            throw new AssertionError(reason);
        }
    }
}

