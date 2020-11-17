package util;

public class Luhn {
    private Luhn() {

    }

    public static int getChecksum(long number) {
        int sum = 0;
        int[] array = new int[15];
        for (int i = 0; i < 15; i++) {
            array[i] = (int) (number % 10);
            number /= 10;
        }

        for (int i = 0; i < array.length; i++) {
            if (i % 2 == 0) {
                array[i] *= 2;
                if (array[i] > 9) {
                    array[i] -= 9;
                }
            }
            sum += array[i];
        }

        if (sum % 10 == 0) {
            return 0;
        } else {
            return 10 - (sum % 10);
        }
    }

    public static boolean isValid(String stringNumber) {
        long number = Long.parseLong(stringNumber);
        int checksum = (int) (number % 10);
        number /= 10;
        return getChecksum(number) == checksum;
    }
}
