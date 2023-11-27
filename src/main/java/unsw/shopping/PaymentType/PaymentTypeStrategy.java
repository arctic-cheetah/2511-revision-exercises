package unsw.shopping.PaymentType;

public abstract class PaymentTypeStrategy {

    public static PaymentTypeStrategy paymentTypeFactory(String paymentMethod) {
        if (paymentMethod.equals("cash")) {
            return new CashPayment();
        } else {
            return new CardPayment();
        }
    }

    public abstract void paymentType(int paymentAmount, int amountPurchased);

}
