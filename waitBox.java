package src;

import java.util.ArrayList;
import java.util.List;

public class waitBox {
    private List<Product> products;
    private Order order = new Order();
    private double TotalPrice;
    public waitBox(){
        products = new ArrayList<>();
    }
    public List<Product> getWaitBoxs(){
        return products;
    }
    public void addWaitBoxs(Product product){
        products.add(product);
    }
    public void clearWaitBoxs(){
        products.clear();
    }
    public double totalPrice(){
        double result = 0;
        for(Product product : products){
            result += product.getPrice();
        }
        return result;
    }
}
