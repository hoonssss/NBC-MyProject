package src;

import java.util.ArrayList;
import java.util.List;

public class CompletedBox{
    private List<Product> completeBoxs;
    private List<Product> orders = new ArrayList<>();

    public CompletedBox(){
        completeBoxs = new ArrayList<>();
    }
    public List<Product> getCompleteBoxs(){
        return completeBoxs;
    }
    public void addCompleteBoxs(Product product){
        completeBoxs.add(product);
    }
    public void clearCompleteBoxs(){
        completeBoxs.clear();
    }
    public double totalPrice() {
        double result = 0;
        for (Product product : completeBoxs) {
            result += product.getPrice();
        }
        return result;
    }
}
