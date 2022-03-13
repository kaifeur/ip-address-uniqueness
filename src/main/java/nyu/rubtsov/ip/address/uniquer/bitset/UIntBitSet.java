package nyu.rubtsov.ip.address.uniquer.bitset;

public interface UIntBitSet {

    /**
     * Set the bit by index {@param uIntIndex} to 1.
     *
     * @param uIntIndex bit index as unsigned 32-bit integer
     * @return true if bit's value was changed, false otherwise
     */
    boolean set(int uIntIndex);

    /**
     * Unset the bit by index {@param uIntIndex} to 0.
     *
     * @param uIntIndex bit index as unsigned 32-bit integer
     * @return true if bit's value was changed, false otherwise
     */
    boolean unset(int uIntIndex);

    /**
     * Get bit's value by index {@param uIntIndex}.
     *
     * @param uIntIndex bit index as unsigned 32-bit integer
     * @return true if bit is set to 1, false otherwise
     */
    boolean getValue(int uIntIndex);
}
