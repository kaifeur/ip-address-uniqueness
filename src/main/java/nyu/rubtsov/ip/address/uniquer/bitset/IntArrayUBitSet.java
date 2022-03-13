package nyu.rubtsov.ip.address.uniquer.bitset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Integer.toUnsignedLong;
import static nyu.rubtsov.ip.address.uniquer.utils.BinaryStringUtils.toBinaryString;

public class IntArrayUBitSet implements UIntBitSet {

    private static final Logger logger = LoggerFactory.getLogger(IntArrayUBitSet.class);

    public static final int MAX_BIT_COUNT = -1; // 2^32

    private static final int BITS_SHIFT = 5;

    private static final int CONTAINER_INDEX_MASK = (MAX_BIT_COUNT >>> BITS_SHIFT) << BITS_SHIFT; // left 27 bits in an unsigned int
    private static final int BIT_INDEX_MASK = (1 << BITS_SHIFT) - 1; // right 5 bits in an unsigned int
    private static final int INT_ARRAY_SIZE = MAX_BIT_COUNT >>> BITS_SHIFT; // 2^32 bits at all, each int can handle 2^5 bits

    private final int[] bits = new int[INT_ARRAY_SIZE];

    public IntArrayUBitSet() {
    }

    @Override
    public synchronized boolean set(int uIntIndex) {
        logger.debug("Setting bit by index {}", Integer.toUnsignedLong(uIntIndex));
        return setBitTo(uIntIndex, true);
    }

    @Override
    public synchronized boolean unset(int uIntIndex) {
        logger.debug("Unsetting bit by index {}", Integer.toUnsignedLong(uIntIndex));
        return setBitTo(uIntIndex, false);
    }

    @Override
    public boolean getValue(int uIntIndex) {
        int intContainer = getIntContainer(getIntContainerIndex(uIntIndex));
        int bitIndex = getBitIndex(uIntIndex);

        return getBitValue(intContainer, bitIndex);
    }

    /**
     * Setting bit to the specific value – 1 or 0.
     *
     * @param uIntIndex bit index as unsigned 32-bit integer
     * @param value     true == 1, false == 0
     * @return true if bit's value was changed, false otherwise
     */
    private boolean setBitTo(int uIntIndex, boolean value) {
        Long uIntIndexAsLong;
        if (logger.isTraceEnabled()) {
            uIntIndexAsLong = toUnsignedLong(uIntIndex);
        } else uIntIndexAsLong = null;

        int intContainerIndex = getIntContainerIndex(uIntIndex);
        logger.trace("Container index for bit {} is {}", uIntIndexAsLong, intContainerIndex);

        int intContainer = getIntContainer(intContainerIndex);
        logger.trace("Container value for bit {} is {}", uIntIndexAsLong, toBinaryString(intContainer));

        int bitIndex = getBitIndex(uIntIndex);
        logger.trace("Bit index for bit {} is {}", uIntIndexAsLong, bitIndex);

        boolean bitValue = getBitValue(intContainer, bitIndex);
        logger.trace("Bit value for bit {} is {}", uIntIndexAsLong, bitValue ? 1 : 0);

        if (bitValue != value) {
            logger.trace("Bit {} value ({}) != new value ({})",
                    uIntIndexAsLong, bitValue ? 1 : 0, value ? 1 : 0);

            int bitIndexAsInt = 1 << bitIndex;
            logger.trace("Bit {} index as int is {}", uIntIndexAsLong, bitIndexAsInt);

            if (value) {
                intContainer = intContainer | bitIndexAsInt;
            } else {
                intContainer = intContainer & ~bitIndexAsInt;
            }
            logger.trace("New container value is {} with bit by index {}", toBinaryString(intContainer), bitIndex);

            bits[intContainerIndex] = intContainer;

            return true;
        } else {
            logger.trace("Bit {} is already set to {}", uIntIndexAsLong, bitValue);
            return false;
        }
    }

    private int getIntContainer(int intContainerIndex) {
        return bits[intContainerIndex];
    }

    private int getIntContainerIndex(int uIntIndex) {
        return (uIntIndex & CONTAINER_INDEX_MASK) >>> BITS_SHIFT;
    }

    private static int getBitIndex(int uIntIndex) {
        return uIntIndex & BIT_INDEX_MASK;
    }

    private static boolean getBitValue(int intContainer, int bitIndex) {
        return (intContainer & (1 << bitIndex)) != 0;
    }
}
