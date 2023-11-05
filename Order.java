package src;

import java.util.ArrayList;

public class Order {
    ArrayList<Product> orders;
    public Order(){
        orders = new ArrayList<>();
    }
    public void addProducts(Product product){
        orders.add(product);
    }
    public ArrayList<Product> getOrders(){
        return orders;
    }
    public void clearOrder(){
        orders.clear();
    }
    public double totalPrice() {
        double result = 0;
        for (Product product : orders) {
            result += product.getPrice();
        }
        return result;
    }
}
