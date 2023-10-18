package ProductManagement;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
    private static Integer idCounter = 0;
    private Integer id;
    private String name;
    private Double rate;
    private String description;
    private boolean isFragile;
    private Integer price;

    public Product(String name, Double rate, String description, boolean isFragile, Integer price) {
        this.id = ++idCounter;
        this.name = name;
        this.rate = rate;
        this.description = description;
        this.isFragile = isFragile;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFragile() {
        return isFragile;
    }

    public void setFragile(boolean fragile) {
        isFragile = fragile;
    }
    public Integer getPrice () {
        return price;
    }

    public static void setIdCounter(Integer idCounter) {
        Product.idCounter = idCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return isFragile() == product.isFragile() && Objects.equals(getName(), product.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), isFragile());
    }



    @Override
    public String toString() {
        return  "id = " + id +
                ", name = " + name  +
                ", rate = " + rate +
                ", description = " + description +
                ", isFragile = " + isFragile +
                ", price = " + price ;

    }
}
