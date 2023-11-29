package unsw.friends;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class NetworkIterator<P extends Comparable<P>> implements Iterator<P> {

    private List<Person<P>> data;
    private Comparator<Person<P>> compare;

    private int curr = 0;

    public NetworkIterator(List<Person<P>> data, Comparator<Person<P>> compare) {
        this.data = data;
        this.compare = compare;
        data.sort(compare);
    }

    // Update the observer to sort the list!
    public void notifyObserver() {
        data.sort(compare);
    }

    public void setCompare(Comparator<Person<P>> compare) {
        this.compare = compare;
        notifyObserver();
    }

    @Override
    public boolean hasNext() {
        return curr < data.size();
    }

    @Override
    public P next() {
        if (curr == 0) {
            data.sort(compare);
        }
        P val = data.get(curr).getId();
        curr++;
        return val;
    }

}
