package com.maxdev.maxphonebook.db.contacts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.room.util.StringUtil;

public class ContactsValidator {
    private final static String emailPatternTemplate =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final static char[] nameProhibitedChars = "()<>[]{}+-_=\\/\"'.,:;~!@#$%^&*?№".toCharArray();
    private static Pattern emailPattern;
    public static final int countOfInitialChars = 2;

    static {
        emailPattern = Pattern.compile(emailPatternTemplate);
    }

    public static boolean isValid(Contact contact) {
        return validateNamePart(contact.getFirstName() + " " + contact.getLastName())
                && validateEmail(contact.getEmail());
    }

    public static boolean validateNamePart(String part) {
        return part != null && !part.equals("") && !containsProhibitedChars(part);
    }

    public static boolean containsProhibitedChars(String str) {
        for (char c: nameProhibitedChars) {
            if (str.contains(Character.toString(c)))
                return true;
        }
        return false;
    }

    public static boolean validateNameInitials(String chars) {
        return validateNamePart(chars) && chars.length() == countOfInitialChars;
    }

    public static boolean validateEmail(String email) {
        if (email.equals(""))
            return true;
        return emailPattern.matcher(email).matches();
    }
}
