package nyu.rubtsov.ip.address.uniquer.utils;

public final class BinaryStringUtils {
    private BinaryStringUtils() {
    }

    public static String toBinaryString(int value) {
        return String.format("%32s", Integer.toBinaryString(value))
                .replace(" ", "0");
    }
}
