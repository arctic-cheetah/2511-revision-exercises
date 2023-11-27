package unsw.cycle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Cycle<E> implements Iterable<E> {
    /**
     * finite sequence of elements which will be repeated in the cycle infinitely
     */
    private final ArrayList<E> sublist;
    private List<E> foundCycle = new ArrayList<>();

    public Cycle(List<E> initialSublist) {
        sublist = new ArrayList<>(initialSublist);
        findCycle(sublist);
    }

    /**
     * return the size of the cycle - infinity if 1 or more elements, otherwise 0
     */
    public double size() {
        if (isEmpty()) {
            return 0;
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    /**
     * return whether the cycle is empty
     */
    public boolean isEmpty() {
        return sublist.isEmpty();
    }

    /**
     * return whether the cycle contains this object
     */
    public boolean contains(Object o) {
        return sublist.contains(o);
    }

    /**
     * add element to the cycle
     */
    public boolean add(E e) {
        boolean val = sublist.add(e);
        findCycle(sublist);
        return val;
    }

    /**
     * remove first instance of the input value
     */
    public boolean remove(Object o) {
        boolean val = sublist.remove(o);
        findCycle(sublist);
        return val;
    }

    public String toString() {
        return getClass().getSimpleName() + " items=" + sublist;
    }

    public List<E> getFoundCycle() {
        return foundCycle;
    }

    /**
     * create and return iterator for the cycle (infinitely repeating)
     */
    @Override
    public Iterator<E> iterator() {
        // Cycle has been found now implement the iterator
        return new CycleIterator<>(foundCycle);
    }

    // Find the smallest cycle
    private void findCycle(List<E> list) {
        foundCycle = getCycle(list);
    }

    private boolean simplifyCycleHelper(List<E> list, List<E> cycle) {
        int cycleSize = cycle.size();
        List<E> tmp = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            tmp.add(list.get(i));
            if (i % cycleSize == 0) {
                // Check that ALL elements in tmp match the subList!
                for (int j = 0; j < list.size(); j += cycleSize) {
                    if (j + cycleSize > list.size()) {
                        return false;
                    }
                    List<E> sub = list.subList(j, j + cycleSize);
                    if (!sub.containsAll(tmp)) {
                        return false;
                    }
                }

            }
        }

        return true;
    }

    private List<E> getCycle(List<E> list) {
        List<E> cycle = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            E curr = list.get(i);
            // add the numbers to the cycle and check if it repeats itself twice in the
            // list.
            // If it does, we found a cycle!!!
            cycle.add(curr);
            if (simplifyCycleHelper(list, cycle)) {
                return cycle;
            }
        }
        return cycle;
    }

    @Override
    public boolean equals(Object o) {
        // TODO = implement this
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Cycle<E> other = (Cycle) o;
        // Need to shift the cycle inorder for them to start on the correct element
        // rotate mutates the list. Make a copy

        for (int i = 0; i < foundCycle.size(); i++) {
            List<E> tmp = new ArrayList<>(other.getFoundCycle());
            Collections.rotate(tmp, i);

            int res = Collections.indexOfSubList(tmp, foundCycle);

            if (res != -1) {
                return true;
            }
        }
        return false;
    }
}
