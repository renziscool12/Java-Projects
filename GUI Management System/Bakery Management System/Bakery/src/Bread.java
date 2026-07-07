public class Bread {
    private String breadType;
    private int quantity, price, breadId;

    public Bread(int breadId, String breadType, int quantity, int price) {
        this.breadId = breadId;
        this.breadType = breadType;
        this.quantity = quantity;
        this.price = price;
    }

    public String getBreadType() {
        return breadType;
    }

    public void setBreadType(String breadType) {
        this.breadType = breadType;
    }

    public int getBreadId() {
        return breadId;
    }

    public void setBreadId(int breadId) {
        this.breadId = breadId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
