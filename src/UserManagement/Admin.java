package UserManagement;

import Helper.AccessController;
import OrderManagement.OrderManagementSystem;
import ProductManagement.ProductManagementSystem;

import java.util.Collection;
import java.util.Scanner;

public class Admin extends User {
    private final Scanner scanner = new Scanner(System.in);
    private OrderManagementSystem orderManagement () {
        return AccessController.OMS;
    }
    private CustomerManagementSystem customerManagement () {
        return AccessController.CMS;
    }
    private ProductManagementSystem productManagement () {
        return AccessController.PMS;
    }

    public Admin() {
        super("admin", "admin", UserRole.ADMIN);
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public UserRole getRole() {
        return super.getRole();
    }

    public void adminMenu() {
        int answer;
        System.out.println("Enter id of operation.");
        System.out.println("Available operations:");
        System.out.println(
                """
                 1. Orders
                 2. Customers
                 3. Products
                 4. Exit program
                             """

        );
        try {
            answer = scanner.nextInt();
            switch (answer) {
                case 1 -> adminOrders();
                case 2 -> adminCustomers();
                case 3 -> adminProducts();
                case 4 -> {
                    System.out.println("Thank you for using Moonbucks retail order management system.");
                    System.exit(0);
                }
                default -> System.out.println("Incorrect input");
            }
        } catch (Exception e) {
            System.out.println("Incorrect input");
        }
    }
    private void adminOrders() {
        System.out.println("Welcome to the order management.");
        int answer;
        System.out.println("Enter id of operation.");
        System.out.println("Available operations:");
        System.out.println(
                """
                        1. Add order
                        2. Show orders
                        3. Edit order
                        4. View order
                        5. Search order
                        6. Delete order
                        7. Return to the previous menu
                                    """

        );
        try {
            Collection<Customer> customers = this.customerManagement().getCustomers();
            answer = scanner.nextInt();
            switch (answer) {
                case 1 -> {
                    System.out.println("Select the id of customer for whom order will be created");
                    System.out.println("The available customers are:");
                    System.out.println(customers.toString());
                    Integer customerId = scanner.nextInt();
                    Customer customer = customerManagement().getCustomer(customerId);
                    orderManagement().add(customer);
                    adminOrders();
                }
                case 2 -> {
                    System.out.println(orderManagement().getOrders());
                    adminOrders();
                }
                case 3 -> {
                    System.out.println("Enter the id of the order");
                    Integer orderId = scanner.nextInt();
                    orderManagement().edit(orderId);
                    adminOrders();
                }
                case 4 -> {
                    System.out.println("Enter the id of the order");
                    Integer orderId = scanner.nextInt();
                    orderManagement().view(orderId);
                    adminOrders();
                }
                case 5 -> {
                    System.out.println("Enter the name of a product which is in a necessary order");
                    scanner.nextLine();
                    String word = scanner.nextLine();
                    orderManagement().search(word, orderManagement().getListOfOrders());
                    adminOrders();
                }
                case 6 -> {
                    System.out.println("Enter the id of the order");
                    Integer orderId = scanner.nextInt();
                    orderManagement().delete(orderId);
                    adminOrders();
                }
                case 7 -> adminMenu();
                default -> System.out.println("Incorrect input");
            }
        } catch (Exception e) {
            System.out.println("Incorrect input");
        }
    }
    private void adminCustomers() {
        System.out.println("Welcome to the customer management.");
        int answer;
        System.out.println("Enter id of operation.");
        System.out.println("Available operations:");
        System.out.println(
                """
                        1. Add customer
                        2. View customer
                        3. Edit customer
                        4. Search customer
                        5. Delete customer
                        6. Return to the previous menu
                                    """

        );
        try {
            answer = scanner.nextInt();
            switch (answer) {
                case 1 -> {
                    customerManagement().add();
                    adminCustomers();
                }
                case 2 -> {
                    System.out.println("Enter the id of the customer");
                    Integer customerId = scanner.nextInt();
                    customerManagement().view(customerId);
                    adminCustomers();
                }
                case 3 -> {
                    System.out.println("Enter the id of the customer");
                    Integer customerId = scanner.nextInt();
                    customerManagement().edit(customerId);
                    adminCustomers();
                }
                case 4 -> {
                    System.out.println("Enter the name of a customer");
                    scanner.nextLine();
                    String word3= scanner.nextLine();
                    customerManagement().search(word3);
                    adminCustomers();
                }
                case 5 -> {
                    System.out.println("Enter the id of the customer");
                    Integer customerId = scanner.nextInt();
                    customerManagement().delete(customerId);
                    adminCustomers();
                }
                case 6 -> adminMenu();
                default -> System.out.println("Incorrect input");
            }
        } catch (Exception e) {
            System.out.println("Incorrect input");
        }
    }
    private void adminProducts() {
        System.out.println("Welcome to the product management.");
        int answer;
        System.out.println("Enter id of operation.");
        System.out.println("Available operations:");
        System.out.println(
                """
                        1. Add product
                        2. View product
                        3. Edit product
                        4. Search product
                        5. Delete product
                        6. Return to the previous menu
                                    """

        );
        try {
            answer = scanner.nextInt();
            scanner.nextLine();
            switch (answer) {
                case 1 -> {
                    productManagement().add();
                    adminProducts();
                }
                case 2 -> {
                    System.out.println("Enter the id of the product");
                    Integer orderId = scanner.nextInt();
                    productManagement().view(orderId);
                    adminProducts();
                }
                case 3 -> {
                    System.out.println("Enter the id of the product");
                    Integer orderId = scanner.nextInt();
                    productManagement().edit(orderId);
                    adminProducts();
                }
                case 4 -> {
                    System.out.println("Enter the name of a product");
                    String word = scanner.nextLine();
                    productManagement().search(word);
                    adminProducts();
                }
                case 5 -> {
                    System.out.println("Enter the id of the product");
                    Integer orderId = scanner.nextInt();
                    productManagement().delete(orderId);
                    adminProducts();
                }
                case 6 -> adminMenu();
                default -> System.out.println("Incorrect input");
            }
        } catch (Exception e) {
            System.out.println("Incorrect input");
        }
    }

}
