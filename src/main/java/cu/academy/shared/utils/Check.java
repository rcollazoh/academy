package cu.academy.shared.utils;

import cu.academy.shared.exceptions.ArgumentException;

public class Check {
    public static int parseIntOrThrow(String str, String errorMsg) throws ArgumentException {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            throw new ArgumentException(errorMsg);
        }
    }

    public static <T> T argumentNotNull(T value, String errorMsg) throws ArgumentException {
        if (value == null)
            throw new ArgumentException(errorMsg);
        return value;
    }
}
