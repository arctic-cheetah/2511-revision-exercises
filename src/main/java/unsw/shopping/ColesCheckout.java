package unsw.shopping;

import java.util.List;

public class ColesCheckout extends CheckoutSystem {

    public ColesCheckout(String supermarket) {
        super(supermarket);
    }

    // Different code below

    protected void printReceipt(List<Item> items) {
        System.out.println("Today at Coles you purchased the following:");
        items.forEach(e -> System.out.println("- " + e.getName() + " : $" + e.getPrice()));
    }

    public void scanItems(List<Item> items) {
        // Supermarkets have restrictions on the number of items allowed
        if (items.size() > 20) {
            System.out.println("Too many items.");
        }
        if (items.size() == 0) {
            System.out.println("You do not have any items to purchase.");
            return;
        }
        items.forEach(e -> setAmountPurchased(getAmountPurchased() + e.getPrice()));

    }

    public String superMarketCard() {
        return "flybuys";
    }

}