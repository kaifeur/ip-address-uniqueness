package nyu.rubtsov.ip.address.uniquer.counter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import nyu.rubtsov.ip.address.uniquer.converter.IPv4AddressToUIntConverter;
import nyu.rubtsov.ip.address.uniquer.converter.IpAddressConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

class IpAddressConverterTest {

    private final Random random = new Random();
    IPv4AddressToUIntConverter converter = IpAddressConverter::ipAsStringToUInt;

    String ip;

    @BeforeEach
    void createIp() {
        ip = random.nextInt(256) + "."
                + random.nextInt(256) + "."
                + random.nextInt(256) + "."
                + random.nextInt(256);
    }

    @RepeatedTest(100)
    void ipAsStringToUInt() throws UnknownHostException {
        int ipAsInt = converter.convert(ip.toCharArray());

        int ipAsIntWithInetAddress = ipAsIntWithInetAddress();

        Assertions.assertEquals(
                ipAsIntWithInetAddress,
                ipAsInt,
                "Result differs from java.net.InetAddress"
        );
    }

    private int ipAsIntWithInetAddress() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(ip);
        byte[] a = inetAddress.getAddress();

        return ((a[0] & 0xFF) << 24) | ((a[1] & 0xFF) << 16)
                | ((a[2] & 0xFF) << 8) | (a[3] & 0xFF);
    }
}