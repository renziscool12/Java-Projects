public class Gym {
    private String memberName, membershipType;
    private int monthlyPayment, expirationDate;

    public Gym(String memberName, String membershipType, int monthlyPayment, int expirationDate) {
        this.memberName = memberName;
        this.membershipType = membershipType;
        this.monthlyPayment = monthlyPayment;
        this.expirationDate = expirationDate;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public int getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(int monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public int getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(int expirationDate) {
        this.expirationDate = expirationDate;
    }

}
