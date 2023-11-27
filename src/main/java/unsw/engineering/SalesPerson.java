package unsw.engineering;

public class SalesPerson extends Employee {

	private float commission;
	private double salesAchieved;

	public SalesPerson(String title, String firstName, String lastName, int quota) {
		super(title, firstName, lastName, quota);
	}

	public double calculateSalary() {
		double totalSal;
		totalSal = super.getBaseSalary() + commission * getSalesAchieved()
				+ super.calculateParkingFringeBenefits()
				- super.calculateTax();
		return totalSal;
	}

	public String getSalesSummary() {
		return getFirstName() + getLastName() + "Sales Target: " + getQuota()
				+ "$\n" +
				"Sales to date: " + getSalesAchieved() + "$";
	}

	public double getSalesAchieved() {
		return salesAchieved;
	}

	public void setSalesAchieved(double salesAchieved) {
		this.salesAchieved = salesAchieved;
	}

}
