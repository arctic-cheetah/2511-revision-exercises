package unsw.cycle;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CycleIterator<E> implements Iterator<E> {
    private List<E> data = null;
    private int curr = 0;

    // TODO we may need to cycle through the elements
    public CycleIterator(List<E> data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return !data.isEmpty();
    }

    @Override
    public E next() {
        if (data.isEmpty()) {
            throw new NoSuchElementException("There are no elements here!");
        }
        E val = data.get(curr);
        curr++;
        curr = curr % data.size();
        return val;
    }

}
