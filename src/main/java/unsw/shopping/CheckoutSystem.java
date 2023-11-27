package unsw.shopping;

import java.util.List;

import unsw.shopping.PaymentType.PaymentTypeStrategy;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class CheckoutSystem {

    private String supermarket;
    private PaymentTypeStrategy p = null;
    private int amountPurchased;

    protected CheckoutSystem(String supermarket) {
        this.supermarket = supermarket;
    }

    // Apply factory method
    public static CheckoutSystem instance(String supermarket) {
        return CheckoutFactory.createCheckoutSystem(supermarket);
    }

    protected abstract void printReceipt(List<Item> items);

    public abstract void scanItems(List<Item> items);

    public abstract String superMarketCard();

    public void checkout(List<Item> items, String paymentMethod, int paymentAmount, boolean receipt) {
        // Welcome the user
        String cardName = superMarketCard();
        System.out.println("Welcome! Please scan your first item. If you have a " + cardName
                + " card, please scan it at any time.");

        // Scan the items
        scanItems(items);

        // Take the user's payment
        if (paymentAmount < getAmountPurchased()) {
            System.out.println("Not enough $$$.");
            return;
        }

        // Only two payment methods!
        p = PaymentTypeStrategy.paymentTypeFactory(paymentMethod);
        paymentType(paymentAmount, getAmountPurchased());

        // Print the receipt
        if (receipt) {
            printReceipt(items);
        }
    }

    protected void paymentType(int paymentAmount, int amountPurchased) {
        p.paymentType(paymentAmount, amountPurchased);
    }

    public String getSupermarketName() {
        return supermarket;
    }

    public void setAmountPurchased(int amountPurchased) {
        this.amountPurchased = amountPurchased;
    }

    public int getAmountPurchased() {
        return this.amountPurchased;
    }

    public static void main(String[] args) {
        List<Item> items = new ArrayList<Item>(Arrays.asList(
                new Item("Apple", 1),
                new Item("Orange", 1),
                new Item("Avocado", 5)));

        CheckoutSystem checkout = instance("Woolies");
        checkout.checkout(items, "cash", 200, true);
    }

}