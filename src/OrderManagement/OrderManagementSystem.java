package OrderManagement;
import Helper.AccessController;
import Helper.FileDiskProcessor;
import ProductManagement.ProductManagementSystem;
import UserManagement.Customer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class OrderManagementSystem extends FileDiskProcessor  {
    private static final Path FILEPATH = Path.of("src/Storage/Orders.txt");
    private static final String OBJPATH = "src/Storage/Serialized/Orders.dat";
    private static final OrderManagementSystem INSTANCE = new OrderManagementSystem();
    private TreeMap<Integer, Order> orders = restoreOrders();
    private ProductManagementSystem pms = AccessController.PMS;

    private OrderManagementSystem () {

    }

    public static OrderManagementSystem getInstance() {
        return INSTANCE;
    }


    public void add(Customer customer) {
        Order order = createOrder(customer);
        if (order == null) {
            System.out.println("Надо обработать эту проверку на возврат в меню"); // Обработай!!!!
            return;
        }
       orders.put(order.getId(), order);
       writeToDisk(FILEPATH, orders.values().stream().map(Order::toString).collect(Collectors.toList()));
       serializeToDisk(OBJPATH, orders);
       System.out.printf("Order #%d for customer %s has been created\n", order.getId(), customer.getName());

    }


    public void delete(Integer orderId) {
        var result = orders.remove(orderId);
        if(result == null) {
            System.out.println("There is no such an order");
        } else {
            writeToDisk(FILEPATH, orders.values().stream().map(Order::toString).collect(Collectors.toList()));
            serializeToDisk(OBJPATH, orders);
            System.out.printf("Order with id = %d has been deleted\n",  result.getId());
        }
    }


    public void view(Integer orderId) {
        var result = orders.get(orderId);
        if(result == null) {
            System.out.println("There is no such an order");
        } else System.out.println(result);
    }


    public void edit(Integer orderId) {
        var scanner = new Scanner(System.in);
        var order = orders.get(orderId);
        if(order == null) {
            System.out.println("There is no such an order");
        } else {
            var orderItems = order.getOrderItems();
            try {
                System.out.println("You have the next products in your order:");
                orderItems.forEach(System.out::println);
                System.out.println("Insert a name of the product that you want to change");
                String productName = scanner.nextLine();
                OrderItem chosenItem = null;
                for (OrderItem o : orderItems ) {
                    if (o.getProduct().getName().equalsIgnoreCase(productName)) {
                        chosenItem = o;
                    }
                }
                if (chosenItem == null) {
                    System.out.println("There is no such a product");
                    return;
                }
                System.out.println("Choose one of the options:\n" +
                        "1. Change the product's quantity\n" +
                        "2. Change the product");
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase("1")) {
                    System.out.println("Insert a new quantity");
                    chosenItem.setQuantity(scanner.nextInt());
                    writeToDisk(FILEPATH, orders.values().stream().map(Order::toString).collect(Collectors.toList()));
                    serializeToDisk(OBJPATH, orders);
                    System.out.println("Order updated successfully");
                    System.out.println("You have the next products in your order:");
                    orderItems.forEach(System.out::println);
                } else if (answer.equalsIgnoreCase("2")) {
                    System.out.println("Please choose a new product. Available products are");
                    pms.getProductIds();
                    Integer productId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Insert a new quantity");
                    chosenItem.setProduct(pms.getProductById(productId));
                    chosenItem.setQuantity(scanner.nextInt());
                    writeToDisk(FILEPATH, orders.values().stream().map(Order::toString).collect(Collectors.toList()));
                    serializeToDisk(OBJPATH, orders);
                    System.out.println("Order updated successfully");
                    System.out.println("You have the next products in your order:");
                    orderItems.forEach(System.out::println);
                } else {
                    System.out.println("Incorrect input. Please try again");
                }
            } catch (Exception e) {
                System.out.println("Incorrect input. Please try again" );
            }
        }
    }


    public void search(String productName, List<Order> ordersList) {
        var tempSet = new HashSet<Order>();
        for(var order : ordersList) {
            for (var orderItem : order.getOrderItems()) {
                if(orderItem.getProduct().getName().toLowerCase().contains(productName.toLowerCase())) {
                    tempSet.add(order);
                }
            }
        }
        System.out.println(tempSet);
    }

    private Order createOrder(Customer customer) {
        List<OrderItem> orderItems = new ArrayList<>();
        Integer productQty;
        Integer productId;
        String answer;

        try {
            var reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                // Creating an OrderItem
                System.out.println("Choose the product and it's quantity.");
                System.out.println("Insert id of a product. Available products are:");
                pms.getProductIds();
                productId = Integer.parseInt(reader.readLine());
                System.out.println("Set quantity of a product.");
                productQty = Integer.parseInt(reader.readLine());
                System.out.println("Do you want anything else? (Yes/No).");
                answer = reader.readLine();
                if(answer.equalsIgnoreCase("Yes")) {
                    orderItems.add(new OrderItem(pms.getProductById(productId), productQty));
                    // Creating order
                } else if (answer.equalsIgnoreCase("No")) {
                    orderItems.add(new OrderItem(pms.getProductById(productId), productQty));
                    return new Order(orderItems, customer.getId());
                }
            }
        } catch (Exception e) {
            System.out.println("Incorrect input. Please try again" );
            //e.printStackTrace();
            return null;
        }
    }
    public String getOrders() {
        StringBuilder builder = new StringBuilder();
        if (orders == null) {
            return "No orders found";
        }
        for (var entry : orders.entrySet()) {
            builder.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
        }
        return builder.toString();
    }
    private TreeMap<Integer, Order> restoreOrders() {
        var tempMap = (TreeMap<Integer, Order>) deserializeFromDisk(OBJPATH);
        if (!tempMap.isEmpty()) {
            Order.setIdCounter(tempMap.lastKey());
        }
        return tempMap;
    }
    public List<Order> getCustomerOrders(Customer customer) {
        var customerOrders = new ArrayList<Order>();
        for (var orders : orders.values()) {
            if (orders.getCustomerId().equals(customer.getId())) {
                customerOrders.add(orders);
            }
        }
        if (customerOrders.isEmpty()) System.out.println("You don't have orders yet.");
        return customerOrders;
    }
    public void editCustomerOrder(Customer customer) {
        var customerOrders = new ArrayList<Order>();
        boolean orderFound = false;
        for (var orders : orders.values()) {
            if (orders.getCustomerId().equals(customer.getId())) {
                customerOrders.add(orders);
            }
        }
        var scanner = new Scanner(System.in);
        System.out.println("Insert an id of the order");
        Integer orderId;
        try {
            orderId = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Incorrect input");
            return;
        }
        for (Order order : customerOrders) {
            if (order.getId().equals(orderId)) {
                orderFound = true;
                edit(orderId);
            }
        }
        if(!orderFound) {
            System.out.println("You don't have an order with id = " + orderId);
        }
    }
    public void viewCustomerOrder(Customer customer) {
        var customerOrders = new ArrayList<Order>();
        boolean orderFound = false;
        for (var orders : orders.values()) {
            if (orders.getCustomerId().equals(customer.getId())) {
                customerOrders.add(orders);
            }
        }
        var scanner = new Scanner(System.in);
        System.out.println("Insert an id of the order");
        Integer orderId;
        try {
            orderId = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Incorrect input");
            return;
        }
        for (Order order : customerOrders) {
            if (order.getId().equals(orderId)) {
                orderFound = true;
                view(orderId);
            }
        }
        if(!orderFound) {
            System.out.println("You don't have an order with id = " + orderId);
        }
    }
    public void deleteCustomerOrder(Customer customer) {
        var customerOrders = new ArrayList<Order>();
        boolean orderFound = false;
        for (var orders : orders.values()) {
            if (orders.getCustomerId().equals(customer.getId())) {
                customerOrders.add(orders);
            }
        }
        var scanner = new Scanner(System.in);
        System.out.println("Insert an id of the order");
        Integer orderId;
        try {
            orderId = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Incorrect input");
            return;
        }
        for (Order order : customerOrders) {
            if (order.getId().equals(orderId)) {
                orderFound = true;
                delete(orderId);
            }
        }
        if(!orderFound) {
            System.out.println("You don't have an order with id = " + orderId);
        }
    }
    public void searchCustomerOrderByProductName(Customer customer) {
        var scanner = new Scanner(System.in);
        System.out.println("Enter name of a product from your order");
        String productName = scanner.nextLine();
        var customerOrders = new ArrayList<Order>();
        for (var orders : orders.values()) {
            if (orders.getCustomerId().equals(customer.getId())) {
                customerOrders.add(orders);
            }
        }
        search(productName, customerOrders);
    }
    public List<Order> getListOfOrders() {
        return orders.values().stream().toList();
    }

}
