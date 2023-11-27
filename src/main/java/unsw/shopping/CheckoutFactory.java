package unsw.shopping;

public class CheckoutFactory {

    public static CheckoutSystem createCheckoutSystem(String supermarket) {
        if (supermarket.equalsIgnoreCase("Coles")) {
            return new ColesCheckout(supermarket);
        }
        if (supermarket.equalsIgnoreCase("Woolies")) {
            return new WoolworthCheckout(supermarket);
        }
        return null;
    }
}
