package org.twelve.controllers;

import java.time.Month;
import java.time.Year;
import java.util.regex.Pattern;

/**
 * Handler that contains helper methods for verifying input.
 */
public class InputHandler {

    /**
     * Determines if username contains only acceptable characters.
     *
     * @param username Username of an account.
     * @return Whether given username contains only acceptable characters.
     */
    public boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9_]+$");
    }

    /**
     * Determines if location contains only acceptable characters.
     *
     * @param location String of a location.
     * @return Whether given location contains only acceptable characters.
     */
    public boolean isValidLocation(String location) {

        return location.matches("^[a-zA-Z]+( [a-zA-Z]+)*$");

    }
}
