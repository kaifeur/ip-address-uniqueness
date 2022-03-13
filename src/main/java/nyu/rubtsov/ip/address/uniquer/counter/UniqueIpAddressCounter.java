package nyu.rubtsov.ip.address.uniquer.counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import nyu.rubtsov.ip.address.uniquer.bitset.UIntBitSet;
import nyu.rubtsov.ip.address.uniquer.converter.IPv4AddressToUIntConverter;

public class UniqueIpAddressCounter {

    private static final long MAX_UNIQUE_IP_COUNT = Integer.toUnsignedLong(-1);

    private final UIntBitSet bitSet;
    private final IPv4AddressToUIntConverter converter;

    private final Path targetFile;

    public UniqueIpAddressCounter(
            UIntBitSet bitSet,
            IPv4AddressToUIntConverter converter,
            Path targetFile
    ) {
        this.bitSet = bitSet;
        this.converter = converter;

        if (!targetFile.toFile().isFile()) {
            throw new IllegalArgumentException("Path " + targetFile + "is not a file");
        }
        this.targetFile = targetFile;
    }

    public long count() throws IOException {
        long counter = 0;

        File file = targetFile.toFile();
        FileReader fileReader = new FileReader(file);

        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            if (counter >= MAX_UNIQUE_IP_COUNT) { // no need to continue reading the file
                return counter;
            }

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int ipAsUInt = converter.convert(line.toCharArray());

                boolean wasSeenBefore = bitSet.getValue(ipAsUInt);

                if (!wasSeenBefore) {
                    counter++;
                    bitSet.set(ipAsUInt);
                }
            }
        }

        return counter;
    }
}
