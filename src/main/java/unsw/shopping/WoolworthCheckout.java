package unsw.shopping;

import java.util.List;

public class WoolworthCheckout extends CheckoutSystem {

    public WoolworthCheckout(String supermarket) {
        super(supermarket);
    }

    // Different code below
    protected void printReceipt(List<Item> items) {
        System.out.print("Your purchase: ");
        for (int i = 0; i < items.size() - 1; i++) {
            System.out.print(items.get(i).getName() + ", ($" + items.get(i).getPrice() + "), ");
        }
        System.out.println(
                items.get(items.size() - 1).getName() + " ($" + items.get(items.size() - 1).getPrice() + ").");
    }

    public void scanItems(List<Item> items) {
        // Supermarkets have restrictions on the number of items allowed
        if (items.size() >= 55) {
            System.out.println("Sorry, that's more than we can handle in a single order!");
        }

        if (items.size() == 0) {
            System.out.println("You do not have any items to purchase.");
            return;
        }

        items.forEach(e -> setAmountPurchased(getAmountPurchased() + e.getPrice()));
    }

    public String superMarketCard() {
        return "Everyday Rewards";
    }

}