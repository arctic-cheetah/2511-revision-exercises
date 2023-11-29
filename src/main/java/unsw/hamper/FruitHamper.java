package unsw.hamper;

public class FruitHamper extends ArrayListItemHamper<Fruit> {
    /**
     * invariant: FruitHamper must have at least 2 apples and 2 avocados when have
     * >= 6 fruits.
     * Otherwise, adding an item should do nothing
     * fruit hamper has price surcharge of 25%, rounded up to nearest integer
     */

    private static final int APPLE_COUNT = 2;
    private static final int AVO_COUNT = 2;
    private static final double SURCHARGE = 1.25;

    @Override
    public int getPrice() {
        int cost = getCounts().stream().map(e -> {
            return fruitPrice(e);
        }).reduce(Integer::sum).get();

        return (int) Math.ceil(cost * SURCHARGE);
    }

    @Override
    public void add(Fruit e, int n) {

        if (size() + n < 6) {
            super.add(e, n);
        } else {
            if (e instanceof Apple && !isAppleCountMet()) {
                super.add(e, n);
                return;
            } else if (e instanceof Avocado && !isAvocadoCountMet()) {
                super.add(e, n);
                return;
            }
            // Has the apple / avo count been met?
            else if (isAppleCountMet() && isAvocadoCountMet()) {
                super.add(e, n);
            }
        }

        return;
    }

    boolean isAppleCountMet() {
        return getClassCount(Apple.class) >= APPLE_COUNT ? true : false;
    }

    boolean isAvocadoCountMet() {
        return getClassCount(Avocado.class) >= AVO_COUNT ? true : false;
    }

    private Integer fruitPrice(Count<Fruit> e) {
        int num = e.getCount();
        Fruit fruit = e.getElement();
        return num * fruit.getPrice();
    }
}
