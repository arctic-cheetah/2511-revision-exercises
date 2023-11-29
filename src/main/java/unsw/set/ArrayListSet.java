package unsw.set;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ArrayListSet<E> implements Set<E> {
    private List<E> data = new ArrayList<>();

    public ArrayListSet(Collection<E> dat) {
        data = new ArrayList<>(dat);
    }

    public ArrayListSet() {
    }

    /**
     * Add an element to the set. Set is unchanged if it already contains the
     * element.
     *
     * @param e The element to add
     * @post contains(e)
     */
    public void add(E e) {
        if (!data.contains(e)) {
            data.add(e);
        }
    }

    /**
     * Remove an element from the set.
     *
     * @param e The element to remove
     * @post !contains(e)
     */
    public void remove(E e) {
        if (data.contains(e)) {
            data.remove(e);
        }
    }

    /**
     * Determine if the given element is in the set.
     *
     * @param e The element to test against
     * @return true - if the element is in the set, false otherwise
     */
    public boolean contains(Object e) {
        return data.contains(e);
    }

    /**
     * Get the number of elements in the set.
     * 
     * @return size - the number of elements in the set
     * @post size >= 0
     */
    public int size() {
        return data.size();
    }

    /**
     * Determine if this set is a subset of another set.
     *
     * @param other The possible super set.
     * @return subset - Whether or not the subset relation holds.
     * @post subset if and only if (forall e. contains(e) => other.contains(e))
     */
    public boolean subsetOf(Set<?> other) {
        for (E e : data) {
            if (!other.contains(e)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return a new set that is the union of this set and the given set
     *
     * @param other The other set operand.
     * @return result - A new set that is the union of these two sets.
     * @post for all e in result, contains(e) or other.contains(e)
     */
    public Set<E> union(Set<? extends E> other) {
        Set<E> s = new ArrayListSet<>(data);
        for (E e : other) {
            s.add(e);
        }
        return s;
    }

    /**
     * Return a new set that is the intersection of this set and the given set
     *
     * @param other The other set operand.
     * @return result - A new set that is the intersection of these two sets.
     * @post for all e in result, contains(e) and other.contains(e)
     */
    public Set<E> intersection(Set<? extends E> other) {
        Set<E> s = new ArrayListSet<>();
        for (E e : data) {
            if (other.contains(e)) {
                s.add(e);
            }
        }
        return s;
    }

    public Iterator<E> iterator() {
        return data.iterator();
    }

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
        Set<E> other = (Set<E>) o;

        for (E e : data) {
            if (!other.contains(e)) {
                return false;
            }
        }
        for (E e : other) {
            if (!data.contains(e)) {
                return false;
            }
        }
        return true;

    }

}
