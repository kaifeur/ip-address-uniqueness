package nyu.rubtsov.ip.address.uniquer.reader;

public final class IpAddressConverter {

    private static final int IP_PART_SHIFT = 8;
    private static final char DOT = '.';
    private static final int RADIX = 10;
    public static final char ZERO_CHAR = '0';
    public static final char NINE_CHAR = '9';

    private IpAddressConverter() {
    }

    public static int ipAsStringToUInt(char[] ip) {
        int size = ip.length;

        int result = 0;

        int currentPart = 0;
        int dotIndex = 0;

        for (int i = 0; i < size; i++) {
            char currentChar = ip[i];

            if (currentChar == DOT) {
                result |= currentPart << ((3 - dotIndex) * 8);

                dotIndex++;
                currentPart = 0;
            } else if (currentChar >= ZERO_CHAR && currentChar <= NINE_CHAR) {
                currentPart = currentPart * 10 + (currentChar - '0');
            } else {
                throw new IllegalArgumentException("Unexpected char at index " + i + " in string " + new String(ip));
            }
        }

        result |= currentPart << ((3 - dotIndex) * 8);

        return result;
    }
}
