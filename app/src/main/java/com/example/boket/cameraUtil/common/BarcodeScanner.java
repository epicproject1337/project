package com.example.boket.cameraUtil.common;

/**
 * Created by Jaison.
 */
public class BarcodeScanner
{
    /*
    s = 9×1 + 7×3 + 8×1 + 0×3 + 3×1 + 0×3 + 6×1 + 4×3 + 0×1 + 6×3 + 1×1 + 5×3
                =   9 +  21 +   8 +   0 +   3 +   0 +   6 +  12 +   0 +  18 +   1 +  15
                = 93
        93 / 10 = 9 remainder 3
        10 –  3 = 7
     */
    public static boolean isValidISBN13(String input) {
        if (input.length() != 13 || !input.matches("[0-9]+")) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < input.length() - 1; i++) {
            if (i%2 == 0) {
                sum += input.charAt(i) - '0';
            } else {
                sum += (input.charAt(i) - '0') * 3;
            }
        }

        int remainder = sum%10;
        int checkDigit = 10 - remainder;
        if (checkDigit == input.charAt(input.length() - 1) - '0') {
            return true;
        } else {
            return false;
        }
    }

    public static class Constants
    {
        public static final int PERMISSION_REQUEST_CAMERA = 1001;

        public static final int FACING_BACK = 0;
        public static final int FACING_FRONT = 1;

        public static final int FLASH_OFF = 0;
        public static final int FLASH_ON = 1;
        public static final int FLASH_AUTO = 2;
        public static final int FLASH_TORCH = 3;

        public static final int FOCUS_OFF = 0;
        public static final int FOCUS_CONTINUOUS = 1;
        public static final int FOCUS_TAP = 2;
        public static final int FOCUS_TAP_WITH_MARKER = 3;

        public static final String KEY_CAMERA_PERMISSION_GRANTED="CAMERA_PERMISSION_GRANTED";

    }

    static class Defaults {
        static final int DEFAULT_FACING = Constants.FACING_BACK;
        static final int DEFAULT_FLASH = Constants.FLASH_OFF;
        static final int DEFAULT_FOCUS = Constants.FOCUS_CONTINUOUS;
    }

    public static class MessageStrings
    {
        public static final String ERROR_LOW_STORAGE="QR Scanner dependencies cannot be downloaded due to low device storage";
    }
}
