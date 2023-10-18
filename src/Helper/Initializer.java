package Helper;

import UserManagement.Admin;
import UserManagement.Customer;

import java.util.Collection;
import java.util.Scanner;

public abstract class Initializer {
    public static void initialize() {
        var scanner = new Scanner(System.in);
        Collection<Customer> customers = AccessController.CMS.getCustomers();
        boolean userFound = false;
        String password;
        System.out.println("Welcome to the Moonbucks retail order management system.");
        System.out.println("Please enter login:");
        String login = scanner.nextLine();
        System.out.println("Please enter password:");
        password = scanner.nextLine();
        if (login.equals("admin") && password.equals("admin")) {
            new Admin().adminMenu();
        } else {
            Customer customer = new Customer("Dummy", "Dummy", "Dummy", "Dummy");
            for (Customer cus : customers) {
                if (cus.getUsername().equals(login) && cus.getPassword().equals(password)) {
                    userFound = true;
                    customer = cus;
                }
            }
            if (!userFound) {
                System.out.println("Incorrect login or password");

        } else {
                System.out.println("Welcome, "+customer.getUsername() + "!");
                customer.customerMenu();
            }
        }
    }
}
