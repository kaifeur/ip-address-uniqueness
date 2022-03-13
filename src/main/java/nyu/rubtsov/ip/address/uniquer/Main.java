package nyu.rubtsov.ip.address.uniquer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import nyu.rubtsov.ip.address.uniquer.bitset.IntArrayUBitSet;
import nyu.rubtsov.ip.address.uniquer.converter.IpAddressConverter;
import nyu.rubtsov.ip.address.uniquer.counter.UniqueIpAddressCounter;

public class Main {

    private static final String HOW_TO = "Run program with arguments like `--file [your file with IPs]";

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IllegalArgumentException(HOW_TO);
        }

        if (!Objects.equals(args[0], "--file")) {
            throw new IllegalArgumentException(HOW_TO);
        }

        String filePath = args[1];

        UniqueIpAddressCounter counter = new UniqueIpAddressCounter(
                new IntArrayUBitSet(),
                IpAddressConverter::ipAsStringToUInt,
                Path.of(filePath)
        );

        long uniqueIpAddresses = counter.count();
        System.out.println(uniqueIpAddresses);
    }
}
