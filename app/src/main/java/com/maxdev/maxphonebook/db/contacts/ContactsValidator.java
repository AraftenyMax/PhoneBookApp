package com.maxdev.maxphonebook.db.contacts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactsValidator {
    private final static String emailPatternTemplate =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static Pattern emailPattern;

    static {
        emailPattern = Pattern.compile(emailPatternTemplate);
    }

    public static boolean validate(Contact contact) {
        return validateName(contact.getFirstName(), contact.getLastName())
                && validateEmail(contact.getEmail());
    }

    public static boolean validateName(String firstName, String lastName) {
        return firstName != null && !firstName.equals("") && lastName != null && !lastName.equals("");
    }

    public static boolean validateEmail(String email) {
        return emailPattern.matcher(email).matches();
    }
}
