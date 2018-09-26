package matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class StringDigitsMatcher extends BaseMatcher {

    private String rightDigit;
    private String leftDigit;

    private StringDigitsMatcher(String rightDigit) {
        this.rightDigit = rightDigit;
    }

    @Override
    public void describeTo(Description description) {
        if ("".equals(rightDigit)) {
            description.appendText(leftDigit + " should be greater than 0");
        }  else if ("".equals(leftDigit)) {
            description.appendText("0 should be greater than " + rightDigit);
        }
        else {
            description.appendText(leftDigit + " should be greater than " + rightDigit);
        }

    }

    @Override
    public void describeMismatch(Object item, Description description) {
        description.appendText("was lower");
    }

    @Override
    public boolean matches(Object o) {
        leftDigit = (String) o;
        if ("".equals(leftDigit) && "".equals(rightDigit)) {
            return false;
        } else if ("".equals(leftDigit)) {
            return 0 > Integer.parseInt(rightDigit);
        } else if ("".equals(rightDigit)) {
            return Integer.parseInt(leftDigit) > 0;
        } else {
            return Integer.parseInt(leftDigit) > Integer.parseInt(rightDigit);
        }
    }

    public static StringDigitsMatcher shouldBeGreaterThan(String rightDigit) {
        return new StringDigitsMatcher(rightDigit);
    }
}
