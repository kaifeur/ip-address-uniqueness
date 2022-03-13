package nyu.rubtsov.ip.address.uniquer.counter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import nyu.rubtsov.ip.address.uniquer.bitset.IntArrayUBitSet;
import nyu.rubtsov.ip.address.uniquer.converter.IpAddressConverter;
import nyu.rubtsov.ip.address.uniquer.utils.RandomIpAddressGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class UniqueIpAddressCounterTest {

    private static final String TEMP_FILE_NAME = "ips.txt";
    private static final int IP_COUNT = 1024;

    Path tempDirectory;
    Path tempFile;

    long expectedUniqueIPsCount;

    UniqueIpAddressCounter counter;

    @BeforeEach
    void setUp(@TempDir Path tempDirectory) throws IOException {
        this.tempDirectory = tempDirectory;
        tempFile = this.tempDirectory.resolve(TEMP_FILE_NAME);
        Files.createFile(tempFile);

        File file = this.tempFile.toFile();
        fillFileWithIPs(file);

        counter = new UniqueIpAddressCounter(
                new IntArrayUBitSet(),
                IpAddressConverter::ipAsStringToUInt,
                tempFile
        );
    }

    private void fillFileWithIPs(File file) throws IOException {
        HashSet<String> uniqueIPs = new HashSet<>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < IP_COUNT - 1; i++) {
                String ip = RandomIpAddressGenerator.nextIP();
                uniqueIPs.add(ip);

                writer.write(ip);
                writer.write('\n');
            }

            String ip = RandomIpAddressGenerator.nextIP();
            uniqueIPs.add(ip);
            writer.write(ip);
        }

        this.expectedUniqueIPsCount = uniqueIPs.size();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void count() throws IOException {
        long count = counter.count();

        Assertions.assertEquals(
                expectedUniqueIPsCount,
                count,
                "Invalid unique IPs count received"
        );
    }
}