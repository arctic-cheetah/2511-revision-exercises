package unsw.shopping.PaymentType;

public class CashPayment extends PaymentTypeStrategy {
    public void paymentType(int paymentAmount, int amountPurchased) {
        System.out.println("Paid $" + paymentAmount + " with $" + (paymentAmount - amountPurchased) + " change.");
    }

}
