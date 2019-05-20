package aservio.domain.platform;

import java.util.regex.Pattern;

public class InputLimitations {

    public static boolean userLogin(String word) {
        return Pattern.matches("[a-zA-Z0-9_.-]{0,24}", word);
    }
}
