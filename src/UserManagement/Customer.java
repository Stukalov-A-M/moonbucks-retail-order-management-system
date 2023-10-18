package UserManagement;

import Helper.AccessController;
import OrderManagement.OrderManagementSystem;

import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;

public class Customer extends User implements Serializable {
    private static Integer idCounter = 0;
    private Integer id;
    String address;
    String phoneNumber;

    public Customer(String name, String password, String address, String phoneNumber ) {
        super(name = name, password = password, UserRole.CUSTOMER);
        this.id = ++idCounter;
        this.address = address;
        this.phoneNumber = phoneNumber;

    }
    public OrderManagementSystem orderManagement () {
        return AccessController.OMS;
    }

    public static Integer getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(Integer idCounter) {
        Customer.idCounter = idCounter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getName() {
        return super.getUsername();
    }

    @Override
    public String toString() {
        return "id = " + id +
                ", name = " + super.getUsername() +
                ", address = " + address +
                ", phoneNumber = " + phoneNumber +
                ", password = " + super.getPassword();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getPhoneNumber(), customer.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPhoneNumber());
    }
    public void customerMenu() {
        var scanner = new Scanner(System.in);
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
                 7. Exit program
                             """

        );
        try {
            answer = Integer.parseInt(scanner.nextLine());
            switch (answer) {
                case 1 -> {
                    orderManagement().add(this);
                    customerMenu();
                }
                case 2 -> {
                    System.out.println(orderManagement().getCustomerOrders(this));
                    customerMenu();
                }
                case 3 -> {
                    orderManagement().editCustomerOrder(this);
                    customerMenu();
                }
                case 4 -> {
                    orderManagement().viewCustomerOrder(this);
                    customerMenu();
                }
                case 5 -> {
                    orderManagement().searchCustomerOrderByProductName(this);
                    customerMenu();
                }
                case 6 -> {
                    orderManagement().deleteCustomerOrder(this);
                    customerMenu();
                }
                case 7 -> {
                    System.out.println("Thank you for using Moonbucks retail order management system.");
                    System.exit(0);
                }
                default -> System.out.println("Incorrect input");
            }
        } catch (Exception e) {
            System.out.println("Incorrect input");
        }
    }
}

