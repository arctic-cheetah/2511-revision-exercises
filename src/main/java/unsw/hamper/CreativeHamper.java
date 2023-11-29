package unsw.hamper;

public class CreativeHamper extends ArrayListItemHamper<Item> {
    /**
     * invariant: if hamper contains 5 or more items,
     * it must contain at least 2 toy cars (at least 1 must be premium)
     * and at least 2 fruits.
     * Otherwise, adding an item should do nothing
     * creative hamper has a price surcharge of $10
     */

    private static final int FRUIT_COUNT = 2;
    private static final int CAR_PREM_COUNT = 1;
    private static final int TOY_CAR_COUNT = 2;
    private static final int SURCHARGE = 10;

    @Override
    public int getPrice() {
        int cost = getCounts().stream().map(e -> {
            Item it = e.getElement();

            if (it instanceof ToyCar) {
                return ((ToyCar) it).getPrice();
            }
            int numFruit = e.getCount();
            return numFruit * ((Fruit) it).getPrice();
        }).reduce(Integer::sum).get();
        return cost + SURCHARGE;
    }

    @Override
    public void add(Item e, int n) {

        if (size() + n < 5) {
            super.add(e, n);
        } else {
            if (e instanceof Fruit && !isFruitCountMet()) {
                super.add(e, n);
                return;
            } else if (e instanceof ToyCar && !isCarCountMet()) {
                super.add(e, n);
                return;
            } else if (e instanceof PremiumToyCar && !isPremCarCountMet()) {
                super.add(e, n);
                return;
            }
            // Has the apple / avo count been met?
            else if (isFruitCountMet() && isCarCountMet() && isPremCarCountMet()) {
                super.add(e, n);
            }
        }
    }

    boolean isFruitCountMet() {
        return getClassCount(Fruit.class) >= FRUIT_COUNT ? true : false;
    }

    boolean isCarCountMet() {
        return getClassCount(ToyCar.class) >= TOY_CAR_COUNT ? true : false;
    }

    boolean isPremCarCountMet() {
        return getClassCount(PremiumToyCar.class) >= CAR_PREM_COUNT ? true : false;
    }

}
