package io.github.arnabmaji19.libera.desktop.util;

public class Validations {

    public static boolean isNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
