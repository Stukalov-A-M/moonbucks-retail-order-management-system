package Helper;

import OrderManagement.OrderManagementSystem;
import ProductManagement.ProductManagementSystem;
import UserManagement.CustomerManagementSystem;

public abstract class AccessController {
    public static final ProductManagementSystem PMS = ProductManagementSystem.getInstance();
    public static final OrderManagementSystem OMS = OrderManagementSystem.getInstance();
    public static final CustomerManagementSystem CMS = CustomerManagementSystem.getInstance();

}
