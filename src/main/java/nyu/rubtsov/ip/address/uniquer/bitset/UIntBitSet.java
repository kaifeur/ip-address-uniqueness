package nyu.rubtsov.ip.address.uniquer.bitset;

public interface UIntBitSet {

    /**
     * Setting a bit by index {@param uIntIndex} to 1.
     *
     * @param uIntIndex bit index as unsigned 32-bit integer
     * @return true if bit's value was changed, false otherwise
     */
    boolean set(int uIntIndex);

    /**
     * Setting a bit by index {@param uIntIndex} to 0.
     *
     * @param uIntIndex bit index as unsigned 32-bit integer
     * @return true if bit's value was changed, false otherwise
     */
    boolean unset(int uIntIndex);
}
