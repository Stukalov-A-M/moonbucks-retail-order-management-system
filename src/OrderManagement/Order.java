package OrderManagement;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {
    private static Integer idCounter = 0;
    private Integer id;
    private List<OrderItem> orderItems;
    private int customerId;
    private int numberOfItems;
    private double orderPrice;

    public Order(List<OrderItem> orderItems, Integer customerId) {
        this.id = ++idCounter;
        this.orderItems = orderItems;
        this.customerId = customerId;
        for (OrderItem item : orderItems) {
            orderPrice += item.getPriceAmount();
            numberOfItems += item.getQuantity();
        }
    }

    public Integer getId() {
        return id;
    }
    public Integer getCustomerId() {
        return customerId;
    }

    public static void setIdCounter(Integer idCounter) {
        Order.idCounter = idCounter;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "id = " + id +
                ", customerId = " + customerId +
                ", numberOfItems = " + numberOfItems +
                ", orderPrice = " + orderPrice +
                ", orderItems = " + orderItems;
    }
}
