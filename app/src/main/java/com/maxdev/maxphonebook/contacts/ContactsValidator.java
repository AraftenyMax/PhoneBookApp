package com.maxdev.maxphonebook.contacts;

import android.content.res.Resources;

import com.maxdev.maxphonebook.App;
import com.maxdev.maxphonebook.R;
import com.maxdev.maxphonebook.db.contacts.Contact;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ContactsValidator {
    public static final String firstNameField = "first name";
    public static final String lastNameField = "last name";
    public static final String emailField = "email";
    private static final Resources appResources = App.getAppResources();
    private static Map<String, String> errors = new HashMap<>();
    private final static String emailPatternTemplate =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final static char[] nameProhibitedChars = "()\\<>[]{}+-_=\\\\/\\\"'.,:;~!@#$%^&*?№".toCharArray();
    private static Pattern emailPattern;
    public static final int countOfInitialChars = 2;

    static {
        emailPattern = Pattern.compile(emailPatternTemplate);
    }

    public static boolean isValid(Contact contact) {
        if (errors.size() != 0)
            errors.clear();
        boolean isNameValid = validateFullName(contact.getFirstName(), contact.getLastName());
        boolean isEmailValid = validateEmail(contact.getEmail());
        return isNameValid && isEmailValid;
    }

    public static boolean validateFullName(String firstName, String lastName) {
        boolean isValidFirstName = validateString(firstName, firstNameField);
        boolean isValidLastName = validateString(lastName, lastNameField);
        return isValidFirstName && isValidLastName;
    }

    public static boolean validateString(String str, String fieldName) {
        int errorMsgTemplId = -1;
        if (str == null) {
            return false;
        }
        if (str.equals("")) {
            errorMsgTemplId = R.string.emptyStringIsNotAllowed;
        }
        if (containsProhibitedChars(str)) {
            errorMsgTemplId = R.string.containsProhibitedChars;
        }
        if (errorMsgTemplId != -1) {
            String errorMsg = String.format(appResources.getString(errorMsgTemplId), fieldName);
            errors.put(fieldName, errorMsg);
            return false;
        }
        return true;
    }

    public static boolean containsProhibitedChars(String str) {
        for (char c: nameProhibitedChars) {
            if (str.contains(Character.toString(c)))
                return true;
        }
        return false;
    }

    public static boolean validateNameInitials(String chars) {
        return chars != null && !containsProhibitedChars(chars) && chars.length() == countOfInitialChars;
    }

    public static boolean validateEmail(String email) {
        if (email.equals(""))
            return true;
        boolean matchesPattern = emailPattern.matcher(email).matches();
        if (!matchesPattern)
            errors.put(emailField, appResources.getString(R.string.invalidEmail));
        return matchesPattern;
    }

    public static Map<String, String> getErrors() {
        return errors;
    }
}
