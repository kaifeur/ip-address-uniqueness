package nyu.rubtsov.ip.address.uniquer.utils;

import java.util.Random;

public final class RandomIpAddressGenerator {

    private static final Random random = new Random();

    private RandomIpAddressGenerator() {
    }

    public static String nextIP() {
        return random.nextInt(256) + "."
                + random.nextInt(256) + "."
                + random.nextInt(256) + "."
                + random.nextInt(256);
    }
}
