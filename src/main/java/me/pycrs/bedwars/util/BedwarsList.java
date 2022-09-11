package me.pycrs.bedwars.util;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Wrapper for a custom list
 * @param <T> type of data this list will be storing
 */
public class BedwarsList<T> extends AbstractList<T> {
    protected final List<T> elements;
    /**
     * Cache
     */
    private BedwarsList<T> sorted;

    public BedwarsList(List<T> elements) {
        this.elements = new ArrayList<>(elements);
    }

    /**
     * Returns a new {@link BedwarsList<T>} consisting of the elements of the previous {@code BedwarsList} that match the given predicate.
     * This is an intermediate operation.
     *
     * @param predicate a non-interfering, stateless predicate to apply to each element to determine if it should be included
     * @return the new {@link BedwarsList<T>}
     */
    public BedwarsList<T> filter(Predicate<? super T> predicate) {
        return new BedwarsList<>(stream().filter(predicate).toList());
    }

    /**
     * @return a sorted instance of a previous {@link BedwarsList<T>} (cached)
     */
    public BedwarsList<T> sortedCache() {
        if (sorted == null)
            this.sorted = sorted();
        return sorted;
    }

    /**
     * @return a sorted instance of a previous {@link BedwarsList<T>}
     */
    public BedwarsList<T> sorted() {
        return new BedwarsList<>(elements.stream().sorted().toList());
    }

    @Override
    public T get(int index) {
        return elements.get(index);
    }

    @Override
    public int size() {
        return elements.size();
    }
}
