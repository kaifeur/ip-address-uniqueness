package nyu.rubtsov.ip.address.uniquer.bitset;

import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import static java.lang.Integer.toUnsignedLong;

class IntArrayUBitSetTest {

    private final Random random = new Random();

    UIntBitSet bitSet;

    @BeforeEach
    void setUp() {
        bitSet = new IntArrayUBitSet();
    }

    @AfterEach
    void tearDown() {
        bitSet = null;
    }

    @RepeatedTest(100)
    void shouldSetBitValue() {
        int uIntIndex = random.nextInt();
        boolean previousValue = bitSet.set(uIntIndex);
        Assertions.assertTrue(
                previousValue,
                "Bit by index " + toUnsignedLong(uIntIndex) + " is already set to 1"
        );

        boolean storedValue = bitSet.getValue(uIntIndex);
        Assertions.assertTrue(
                storedValue,
                "Bit value by index " + toUnsignedLong(uIntIndex) + " was not changed"
        );
    }

    @RepeatedTest(100)
    void shouldUnsetBitValue() {
        int uIntIndex = random.nextInt();

        boolean wasChanged = bitSet.set(uIntIndex);
        Assertions.assertTrue(
                wasChanged,
                "Bit by index " + toUnsignedLong(uIntIndex) + " is already set to 1"
        );

        wasChanged = bitSet.unset(uIntIndex);
        Assertions.assertTrue(
                wasChanged,
                "Bit by index " + toUnsignedLong(uIntIndex) + " was not changed by unset"
        );

        boolean storedValue = bitSet.getValue(uIntIndex);
        Assertions.assertFalse(
                storedValue,
                "Bit value by index " + toUnsignedLong(uIntIndex) + " has unexpected value"
        );
    }

    @Test
    void getValue() {

    }
}