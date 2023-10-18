package UserManagement;

import Helper.FileDiskProcessor;
import Helper.Manageable;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerManagementSystem extends FileDiskProcessor implements Manageable {
    private static final Path FILEPATH = Path.of("src/Storage/Customers.txt");
    private static final String OBJPATH = "src/Storage/Serialized/Customers.dat";
    private TreeMap<Integer, UserManagement.Customer> customers = restoreCustomers();
    private static final CustomerManagementSystem INSTANCE = new CustomerManagementSystem();
    private CustomerManagementSystem() {
    }
    public static CustomerManagementSystem getInstance() {
        return INSTANCE;
    }

    @Override
    public void add() {
        try {
            var scanner = new Scanner(System.in);
            System.out.println("Insert a name of the customer");
            String name = scanner.nextLine();
            System.out.println("Insert a password of the customer");
            String password = scanner.nextLine();
            System.out.println("Insert address of the customer");
            String address = scanner.nextLine();
            System.out.println("Insert a phone number of the customer");
            String phoneNumber = scanner.nextLine();
            if (!customers.isEmpty()){
                for (var entry : customers.entrySet()) {
                    if (entry.getValue().getPhoneNumber().equalsIgnoreCase(phoneNumber)) {
                        System.out.println("User with this phone number is already exists");
                        return;
                    }
                }
            }
            UserManagement.Customer customer = new UserManagement.Customer(name, password, address, phoneNumber);

            customers.put(customer.getId(), customer);
            writeToDisk(FILEPATH, customers.values().stream().map(UserManagement.Customer::toString).collect(Collectors.toList()));
            serializeToDisk(OBJPATH, customers);
            System.out.println("The customer has been added successfully");
        } catch (Exception e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        var result = customers.remove(id);
        if(result == null) {
            System.out.println("There is no such a customer");
        } else {
            writeToDisk(FILEPATH, customers.values().stream().map(UserManagement.Customer::toString).collect(Collectors.toList()));
            serializeToDisk(OBJPATH, customers);
            System.out.printf("Customer %s with id = %d has been deleted\n", result.getName(), result.getId());
        }
    }

    @Override
    public void view(Integer id) {
        System.out.println(customers.get(id).toString());
    }

    @Override
    public void edit(Integer id) {
        var customer = customers.get(id);
        if(customer == null) {
            System.out.println("There is no customer with this Id");
        } else {
            try {
                var scanner = new Scanner(System.in);
                System.out.println("Insert the customer's address:");
                String address = scanner.nextLine();
                System.out.println("Insert the customer's phone number:");
                String phoneNumber = scanner.nextLine();
                Customer newCustomer = new Customer(customer.getName(), customer.getPassword(), address, phoneNumber);
                newCustomer.setId(customer.getId());
                if (customers.containsValue(newCustomer)) {
                    System.out.println("The customer with the same name & phone number is already exists");
                } else {
                    customers.remove(customer.getId());
                    customers.put(newCustomer.getId(), newCustomer);
                    writeToDisk(FILEPATH, customers.values().stream().map(Customer::toString).collect(Collectors.toList()));
                    serializeToDisk(OBJPATH, customers);
                    System.out.println("The customer updated successfully");
                }
            } catch (Exception e) {
                System.out.println("Incorrect input. Please input field in correct format");
            }
        }
    }

    @Override
    public void search(String name) {
        List<Customer> customerList = new ArrayList<>();
        for (var entry : customers.entrySet()) {
            if (entry.getValue().getName().toLowerCase().contains(name.toLowerCase())) {
                customerList.add(entry.getValue());
            }
        }
        if (customerList.isEmpty()) {
            System.out.printf("There is no customer with name %s\n", name);
        }
        customerList.forEach(System.out::println);
    }
    private TreeMap<Integer, UserManagement.Customer> restoreCustomers() {
        var tempMap = (TreeMap<Integer, UserManagement.Customer>) deserializeFromDisk(OBJPATH);
        if (!tempMap.isEmpty()) {
            UserManagement.Customer.setIdCounter(tempMap.lastKey());
        }
        return tempMap;
    }
    public UserManagement.Customer getCustomer(Integer id) {
        return customers.get(id);
    }
    public Collection<UserManagement.Customer> getCustomers() {

        return customers.values();
    }
}
