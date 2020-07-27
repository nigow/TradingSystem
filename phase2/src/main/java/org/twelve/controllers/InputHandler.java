package org.twelve.controllers;

import java.time.Month;
import java.time.Year;
import java.util.regex.Pattern;

/**
 * Handler that contains helper methods for verifying input.
 */
public class InputHandler {

    private final String TRUE = "y";
    private final String FALSE = "n";

    /**
     * Returns the TRUE string.
     *
     * @return the TRUE string
     */
    public String getTrue() {
        return TRUE;
    }

    /**
     * Returns the FALSE string.
     *
     * @return the FALSE string
     */
    public String getFalse() {
        return FALSE;
    }

    /**
     * Check if a string is a non-negative integer.
     *
     * @param input Input string
     * @return Whether given string is a non-negative integer
     */
    public boolean isNum(String input) {

        return Pattern.matches("^[0-9]+$", input);

    }

    /**
     * Check if a string is a valid date in the form of yyyy-mm-dd.
     *
     * @param input Input string
     * @return Whether given string is a valid date in the form of yyyy-mm-dd
     */
    public boolean isDate(String input) {

        // citation: https://stackoverflow.com/questions/22061723/regex-date-validation-for-yyyy-mm-dd
        if (Pattern.matches("^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", input)) {

            int year = Integer.parseInt(input.split("-")[0]);
            int month = Integer.parseInt(input.split("-")[1]);
            int day = Integer.parseInt(input.split("-")[2]);

            return day <= Month.of(month).length(Year.isLeap(year));

        }
        return false;

    }

    /**
     * Check if a string is a time in the form of hh:mm.
     *
     * @param input Input string
     * @return Whether given string is a time in the form of hh:mm
     */
    public boolean isTime(String input) {

        // citation: https://stackoverflow.com/questions/7536755/regular-expression-for-matching-hhmm-time-format
        return Pattern.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", input);

    }

    /**
     * Check if a string is a boolean/decision in the form of "y" or "n".
     *
     * @param input Input string
     * @return Whether given string is a boolean/decision in the form of "y" or "n"
     */
    public boolean isBool(String input) {

        return isTrue(input) || isFalse(input);

    }

    /**
     * Check if a string is a boolean/decision in the form of "y".
     *
     * @param input Input string.
     * @return Whether given string is a boolean/decision in the form of "y".
     */
    public boolean isTrue(String input) {
        return input.equals(TRUE);
    }

    /**
     * Check if a string is a boolean/decision in the form of "n".
     *
     * @param input Input string.
     * @return Whether given string is a boolean/decision in the form of "n".
     */
    public boolean isFalse(String input) {
        return input.equals(FALSE);
    }

    /**
     * Check if a string is the program standard exit string (-1).
     *
     * @param input Input string
     * @return Whether given string is the program standard exit string (-1)
     */
    public boolean isExitStr(String input) {

        return input.equals("-1");

    }

    /**
     * Check if a string is valid for storing in a CSV file (does not include commas).
     *
     * @param input Input string
     * @return Whether the given string is valid for storing in a CSV file
     */
    public boolean isValidCSVStr(String input) {
        return !input.isEmpty() && !input.contains(",");
    }

    /**
     * Determines if an account can be created with the given username and password.
     *
     * @param username Username of the new account
     * @param password Password of the new account
     * @return Whether an account can be created
     */
    public boolean isValidUserPass(String username, String password) {
        return isValidCSVStr(username) && isValidCSVStr(password) &&
                username.matches("^[a-zA-Z0-9_]*$") && password.matches("^[ -~]*$");
    }
}