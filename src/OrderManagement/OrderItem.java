package OrderManagement;

import ProductManagement.Product;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private Product product;
    private Integer quantity;
    private double priceAmount;
    private double fragileOvercharge; // in percents

    public OrderItem (Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        if(product.isFragile()) {
            fragileOvercharge = 20.0;
            priceAmount = (product.getPrice() + (product.getPrice() * (fragileOvercharge / 100))) * quantity;
        } else {
            fragileOvercharge = 0;
            priceAmount = product.getPrice() * quantity;
        }
    }

    public double getPriceAmount() {
        return priceAmount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        if(product.isFragile()) {
            fragileOvercharge = 20.0;
            priceAmount = (product.getPrice() + (product.getPrice() * (fragileOvercharge / 100))) * quantity;
        } else {
            fragileOvercharge = 0;
            priceAmount = product.getPrice() * quantity;
        }
    }

    @Override
    public String toString() {
        return "OrderItems:\n" +
                "product=" + product +
                ", quantity=" + quantity +
                ", priceAmount=" + priceAmount +
                ", fragileOvercharge=" + fragileOvercharge;
    }
}
