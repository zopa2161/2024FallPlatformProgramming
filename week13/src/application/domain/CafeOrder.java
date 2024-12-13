package application.domain;

public class CafeOrder {
    private String orderId;
    private String itemName;
    private double price;

    public String getOrderId(){ return orderId; }
    public void setOrderId(String orderId){ this.orderId = orderId; }

    public String getItemName(){ return itemName; }
    public void setItemName(String itemName){ this.itemName = itemName; }

    public double getPrice(){ return price; }
    public void setPrice(double price){ this.price = price; }

    @Override
    public String toString() {
        return "CafeOrder{orderId='" + orderId + "', itemName='" + itemName + "', price=" + price + "}";
    }
}
