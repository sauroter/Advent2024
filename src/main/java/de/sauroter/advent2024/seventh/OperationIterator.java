package de.sauroter.advent2024.seventh;

import java.lang.reflect.Array;
import java.math.BigInteger;

public class OperationIterator<T> {
    private final NaryIterator naryIterator;
    private final T[] operations;
    private final BigInteger max;
    private BigInteger count = BigInteger.ZERO;

    public OperationIterator(final int length, final T[] operations) {
        naryIterator = new NaryIterator(operations.length, length);
        this.operations = operations;
        max = BigInteger.valueOf(operations.length).pow(length);
    }

    public boolean hasNext() {
        return count.compareTo(max) < 0;
    }

    public T[] next() {
        final var naryString = naryIterator.getAndIncrement();
        @SuppressWarnings("unchecked") final var ret = (T[]) Array.newInstance(operations[0].getClass(), naryString.length);
        for (int i = 0; i < naryString.length; i++) {
            ret[i] = operations[naryString[i]];
        }
        count = count.add(BigInteger.ONE);
        return ret;
    }
}
