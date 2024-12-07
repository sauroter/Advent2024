package de.sauroter.advent2024.seventh;

import java.util.Arrays;

public class NaryIterator {

    private final int n;
    private final int[] array;

    public NaryIterator(final int n, final int length) {
        this.n = n;
        array = new int[length];
    }

    public int[] getAndIncrement(){
        final var ret = get();
        increment();
        return ret;
    }

    public int[] get(){
        return Arrays.copyOf(array, array.length);
    }

    public void increment() {
        for (int i = 0; i < array.length; i++) {
            array[i]++;
            if (array[i] == n) {
                array[i] = 0;
            } else {
                return;
            }
        }
    }
}
