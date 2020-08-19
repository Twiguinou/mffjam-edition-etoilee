package com.twihick.bunlyu.common.lib;

import javax.annotation.Nullable;

public class PairPicker<F, L> {

    @Nullable
    private final F first;

    @Nullable
    private final L last;

    public PairPicker(@Nullable F first, @Nullable L last) {
        this.first = first;
        this.last = last;
        this.check();
    }

    private void check() {
        if((first == null && last == null) || (first != null && last != null))
            throw new IllegalStateException("How does that happen ?");
    }

    public PairPicker.State getValidState() {
        this.check();
        return first != null ? PairPicker.State.FIRST : PairPicker.State.LAST;
    }

    @Nullable
    public F getFirst() {
        return this.first;
    }

    @Nullable
    public L getLast() {
        return this.last;
    }

    public Object getNotNull() {
        PairPicker.State state = this.getValidState();
        return state == PairPicker.State.FIRST ? this.getFirst() : this.getLast();
    }

    public static <F, L> PairPicker<F, L> firstOf(F first) {
        return new PairPicker<>(first, null);
    }

    public static <F, L> PairPicker<F, L> lastOf(L last) {
        return new PairPicker<>(null, last);
    }

    public enum State {
        FIRST,
        LAST
    }

}
