package io.github.arnabmaji19.libera.desktop.util;

import java.util.regex.Pattern;

public class Validations {

    public static boolean isNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isEmailValid(String email) {
        var emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        var pattern = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pattern.matcher(email).matches();
    }

    public static String validateFormParameters(
            String firstName,
            String lastName,
            String email,
            String password,
            String phone,
            String address
    ) {

        if (
                firstName.isBlank() ||
                        lastName.isBlank() ||
                        email.isBlank() ||
                        password.isBlank() ||
                        phone.isBlank() ||
                        address.isBlank()
        ) return "All Fields are Required!";

        if (!Validations.isEmailValid(email)) return "Email not valid!";

        if (password.length() < 4) return "Password must be at least of 4 characters!";

        if (phone.length() < 10 || phone.length() > 15) return "Phone must be between\n10 to 15 characters!";

        if (address.length() < 4) return "Address must be\nat least 4 characters long!";

        return "";
    }
}
