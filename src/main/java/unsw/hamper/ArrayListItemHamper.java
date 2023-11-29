package unsw.hamper;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A hamper implemented using an ArrayList.
 *
 * @author Matthew Perry
 *
 * @invariant for all c in counts, c.getCount() > 0
 *
 * @param <E>
 */
public class ArrayListItemHamper<E extends Item> implements Hamper<E> {

    private ArrayList<Count<E>> counts;

    /**
     * Create a new empty hamper.
     */
    public ArrayListItemHamper() {
        this.counts = new ArrayList<Count<E>>();
    }

    private Count<E> getCount(Object o) {
        for (Count<E> c : counts)
            if (c.getElement().equals(o))
                return c;
        return null;
    }

    @Override
    public void add(E e) {
        add(e, 1);
    }

    @Override
    public void add(E e, int n) {
        Count<E> c = getCount(e);
        if (c != null) {
            c.incrementCount(n);
        } else if (n > 0) {
            counts.add(new Count<E>(e, n));
        }
    }

    @Override
    public void remove(E e) {
        remove(e, 1);
    }

    @Override
    public void remove(E e, int n) {
        // Check
        // Is the element present?
        Count<E> c = getCount(e);
        if (c == null) {
            return;
        }
        int numElements = c.getCount();

        // Do we have enough elements to remove?
        if (numElements <= n) {
            counts.remove(c);
        } else {
            c.decrementCount(n);
        }
    }

    @Override
    public int count(Object o) {
        Count<E> c = getCount(o);
        if (c != null)
            return c.getCount();
        return 0;
    }

    @Override
    public int size() {
        return counts.stream().map(e -> e.getCount()).reduce(Integer::sum).orElse(0);
    }

    @Override
    public Hamper<E> sum(Hamper<? extends E> hamper) {
        Hamper<E> h = new ArrayListItemHamper<>();

        this.counts.forEach((Count<E> e) -> {
            int target = hamper.count(e.getElement());
            int source = e.getCount();
            h.add(e.getElement(), source + target);
        });

        return h;
    }

    @Override
    public Iterator<Count<E>> iterator() {
        return counts.iterator();
    }

    /**
     * For this method, hampers should be the same class to be equal (ignore the
     * generic type component). For example, a CreativeHamper cannot be equal to a
     * FruitHamper,
     * And a FruitHamper cannot be equal to an ArrayListItemHamper<Fruit>,
     * However an ArrayListItemHamper<Fruit> can be equal to a
     * ArrayListItemHamper<Item> if they both only contain fruit.
     * HINT: use getclass() to compare objects.
     */
    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }

        // So they are an ArrayListItemHamper huh!
        ArrayListItemHamper<E> other = (ArrayListItemHamper<E>) o;
        // TODO:
        // Check item types!
        if (other.counts.get(0).getClass() != this.counts.get(0).getClass()) {
            return false;
        }
        // Check that all the items are the same
        // BUG only checks for thisCount!!!! Need to check the opposite way!

        // Check both ways
        for (Count<E> thisCount : counts) {
            Count<E> otherCount = other.getCount(thisCount.getElement());
            if (!thisCount.equals(otherCount)) {
                return false;
            }
        }

        for (Count<E> otherCount : other.counts) {
            Count<E> thisCount = this.getCount(otherCount.getElement());
            if (!otherCount.equals(thisCount)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 
     * @return price of the hamper - for ArrayListItemHamper, this should be the sum
     *         of the prices of items with each price multiplied by the number of
     *         times that item occurs
     */
    @Override
    public int getPrice() {
        // TODO implement this
        return 0;
    }

    @Override
    public String toString() {
        return counts.toString();
    }

    public ArrayList<Count<E>> getCounts() {
        return counts;
    }

    public <T extends Item> int getClassCount(Class<T> c) {
        int res = counts.stream()
                .filter(e -> e.getElement().getClass() == c)
                .map(e -> e.getCount())
                .reduce(Integer::sum).orElse(0);
        return res;
    }

}
