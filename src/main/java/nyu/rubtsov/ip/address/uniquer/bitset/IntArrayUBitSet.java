package nyu.rubtsov.ip.address.uniquer.bitset;

public class IntArrayUBitSet implements UIntBitSet {

    public static final int MAX_BIT_COUNT = 1 << 31; // 2^32

    private static final int UINT_BITS = 1 << 4; // 2^5
    private static final int INT_ARRAY_SIZE = MAX_BIT_COUNT >>> UINT_BITS; // 2^32 bits at all, each int can handle 2^5 bits

    private final int[] bits = new int[INT_ARRAY_SIZE];

    public IntArrayUBitSet() {
    }

    @Override
    public synchronized boolean set(int uIntIndex) {
        return setBitTo(uIntIndex, true);
    }

    @Override
    public synchronized boolean unset(int uIntIndex) {
        return setBitTo(uIntIndex, false);
    }

    /**
     * Setting bit to the specific value – 1 or 0.
     *
     * @param uIntIndex bit index as unsigned 32-bit integer
     * @param value     true == 1, false == 0
     * @return true if bit's value was changed, false otherwise
     */
    private boolean setBitTo(int uIntIndex, boolean value) {
        int intContainerIndex = getIntContainerIndex(uIntIndex);
        int intContainer = getIntContainer(intContainerIndex);

        int bitIndex = getBitIndex(uIntIndex);
        int bitValue = getBitValue(intContainer, bitIndex);

        int expectedValue = value ? 1 : 0;

        if (bitValue != expectedValue) {
            if (value) {
                intContainer = intContainer | (1 << bitIndex);
            } else {
                intContainer = intContainer & (1 << bitIndex);
            }

            bits[intContainerIndex] = intContainer;

            return true;
        } else {
            return false;
        }
    }

    private int getIntContainer(int intContainerIndex) {
        return bits[intContainerIndex];
    }

    private int getIntContainerIndex(int uIntIndex) {
        return uIntIndex >>> UINT_BITS;
    }

    private static int getBitIndex(int uIntIndex) {
        return uIntIndex & ((UINT_BITS << 1) - 1);
    }

    private static int getBitValue(int intContainer, int bitIndex) {
        return intContainer & (1 << bitIndex);
    }
}
