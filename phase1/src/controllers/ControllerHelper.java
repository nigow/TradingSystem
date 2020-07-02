package controllers;

import java.util.regex.Pattern;

public class ControllerHelper {

    public boolean isNum(String input) {

        return Pattern.matches("^[0-9]+$", input);

    }

    public boolean isDate(String input) {

        // citation: https://stackoverflow.com/questions/22061723/regex-date-validation-for-yyyy-mm-dd
        return Pattern.matches("^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", input);

    }

    public boolean isTime(String input) {

        // citation: https://stackoverflow.com/questions/7536755/regular-expression-for-matching-hhmm-time-format
        return Pattern.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", input);

    }

    public boolean isBool(String input) {

        return input.equals("y") || input.equals("n");

    }

}
