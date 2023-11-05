package src;

public class Product extends Menu{
    private double price;
    public Product(String name, double price, String explanation){
        super(name, explanation);
        this.price = price;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
}
