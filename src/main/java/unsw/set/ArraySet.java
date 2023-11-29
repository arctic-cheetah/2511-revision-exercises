package unsw.set;

import java.lang.reflect.Array;

public class ArraySet<E> {
    private E[] elements;

    public ArraySet(Class<E> cls, int length) {
        this.elements = (E[]) Array.newInstance(cls, length);

    }
}
