package com.example.boket.cameraUtil.common;

/**
 * @author Alexander Jyborn
 *
 * Class that contains constants and helper functions for the camera
 * @since 2020-10-01
 */
public class BarcodeScanner
{
    /**
     * Checks if code is valid ISBN
     *
     * @param input
     * @return boolean if input is a vaild ISBN13 code
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
