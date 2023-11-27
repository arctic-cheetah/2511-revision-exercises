package unsw.shopping.PaymentType;

public class CardPayment extends PaymentTypeStrategy {
    public void paymentType(int paymentAmount, int amountPurchased) {
        paymentAmount = amountPurchased;
        System.out.println("Paid $" + paymentAmount + ".");
    }

}
