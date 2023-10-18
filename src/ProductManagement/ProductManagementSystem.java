package ProductManagement;
import Helper.FileDiskProcessor;
import Helper.Manageable;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;


public class ProductManagementSystem extends FileDiskProcessor implements Manageable {
    private static final Path PATH = Path.of("src/Storage/Products.txt");
    private static final String OBJPATH = "src/Storage/Serialized/Products.dat";
    private static final ProductManagementSystem INSTANCE = new ProductManagementSystem();
    private TreeMap<Integer, Product> products = restoreProducts();

    private ProductManagementSystem() {

    }

    public static ProductManagementSystem getInstance() {
        return INSTANCE;
    }

    @Override
    public void add() {
        Product product = createProduct();
        if (product == null) {
            System.out.println("Надо обработать эту проверку на возврат в меню"); // Обработай!!!!
            return;
        }
        int key = product.getId();
        if (products.isEmpty()) {
            products.put(key, product);
            writeToDisk(PATH, products.values().stream().map(Product::toString).collect(Collectors.toList()));
            serializeToDisk(OBJPATH, products);
        } else if (products.containsKey(key)) {
            System.out.println("The product with this ID is already exists");
        } else if (products.containsValue(product)) {
            System.out.println("The product with the same name & fragile properties is already exists");
        } else {
            products.put(key, product);
            writeToDisk(PATH, products.values().stream().map(Product::toString).collect(Collectors.toList()));
            serializeToDisk(OBJPATH, products);
            System.out.println("The product has been added successfully");
        }
    }

    @Override
    public void delete(Integer id) {
        var result = products.remove(id);
        if(result == null) {
            System.out.println("There is no such a product");
        } else {
            writeToDisk(PATH, products.values().stream().map(Product::toString).collect(Collectors.toList()));
            serializeToDisk(OBJPATH, products);
            System.out.printf("Product %s with id = %d has been deleted\n", result.getName(), result.getId());
        }
    }

    @Override
    public void view(Integer id) {
        var result = products.get(id);
        if(result == null) {
            System.out.println("There is no such a product");
        } else System.out.println(result);
    }

    @Override
    public void edit(Integer id) {
        var product = products.get(id);
        if(product == null) {
            System.out.println("There is no such a product");
        } else {
            try {
                var scanner = new Scanner(System.in);
                System.out.println("Insert the product name:");
                String name = scanner.nextLine();
                System.out.println("Insert the product rate:");
                Double rate = Double.parseDouble(scanner.nextLine());
                System.out.println("Insert the product description:");
                String description = scanner.nextLine();
                System.out.println("Is the product fragile? (true or false):");
                boolean isFragile = scanner.nextBoolean();
                System.out.println("Insert the product's price:");
                Integer price = scanner.nextInt();


                Product newProduct = new Product(name, rate, description, isFragile, price);
                newProduct.setId(product.getId());

                if (products.containsValue(newProduct)) {
                    System.out.println("The product with the same name & fragile properties is already exists");
                } else {
                    products.remove(product.getId());
                    products.put(newProduct.getId(), newProduct);
                    writeToDisk(PATH, products.values().stream().map(Product::toString).collect(Collectors.toList()));
                    serializeToDisk(OBJPATH, products);
                    System.out.println("The product has been edited successfully");
                }
            } catch (Exception e) {
                System.out.println("Incorrect input. Please input field in correct format");
            }
        }
    }

    private Product createProduct() {
        try {
            var scanner = new Scanner(System.in);
            System.out.println("Insert the product name:");
            String name = scanner.nextLine();
            System.out.println("Insert the product rate:");
            Double rate = Double.parseDouble(scanner.nextLine());
            System.out.println("Insert the product description:");
            String description = scanner.nextLine();
            System.out.println("Is the product fragile? (true or false):");
            boolean isFragile = scanner.nextBoolean();
            System.out.println("Insert the product's price:");
            Integer price = scanner.nextInt();

            return new Product(name, rate, description, isFragile, price);

        } catch (Exception e) {
            System.out.println("Incorrect input. Please input field in correct format");
            return null;
        }
    }

    @Override
    public void search(String name) {
        List<Product> productList = new ArrayList<>();
        for (var entry : products.entrySet()) {
            if (entry.getValue().getName().toLowerCase().contains(name.toLowerCase())) {
                productList.add(entry.getValue());
            }
        }
        productList.forEach(System.out::println);
    }
    public String getProducts() {
        StringBuilder builder = new StringBuilder();
        for (var entry : products.entrySet()) {
            builder.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
        }
        return builder.toString();
    }
    public Product getProductById(Integer id) {
        return products.get(id);
    }
    public void getProductIds() {
        for (var entry : products.entrySet()) {
            System.out.println(
                    "id = " + entry.getValue().getId() + " " +
                    "Name = " + entry.getValue().getName()+ " " +
                    "Price = " + entry.getValue().getPrice()
            );
        }
    }
    private TreeMap<Integer, Product> restoreProducts() {
        var tempMap = (TreeMap<Integer, Product>) deserializeFromDisk(OBJPATH);
        if (!tempMap.isEmpty()) {
            Product.setIdCounter(tempMap.lastKey());
        }
        return tempMap;
    }
}
