package nyu.rubtsov.ip.address.uniquer.bitset;

import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void shouldSetBitValue() {
        int uIntIndex = random.nextInt();
        boolean previousValue = bitSet.set(uIntIndex);
        Assertions.assertTrue(
                previousValue,
                "Bit by index " + ((long) uIntIndex) + " is already set to 1"
        );

        boolean storedValue = bitSet.getValue(uIntIndex);
        Assertions.assertTrue(
                storedValue,
                "Bit value by index " + ((long) uIntIndex) + " was not changed"
        );
    }

    @Test
    void unset() {
    }

    @Test
    void getValue() {

    }
}