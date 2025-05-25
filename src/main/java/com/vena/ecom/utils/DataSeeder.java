package com.vena.ecom.utils;

import com.vena.ecom.model.*;
import com.vena.ecom.model.enums.AddressType;
import com.vena.ecom.model.enums.ApprovalStatus;
import com.vena.ecom.model.enums.OrderStatus;
import com.vena.ecom.model.enums.UserRole;
import com.vena.ecom.repo.OrderItemRepository;
import com.vena.ecom.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private ProductCatalogRepository productCatalogRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private VendorProfileRepository vendorProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private VendorProductRepository vendorProductRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productCatalogRepository.count() == 0) {
            // Seed product categories
            // Seed product categories
            ProductCategory category1 = new ProductCategory();
            category1.setName("Electronics");
            category1.setDescription("Electronic gadgets and accessories");
            productCategoryRepository.saveAll(List.of(category1));

            ProductCategory category2 = new ProductCategory();
            category2.setName("Clothing");
            category2.setDescription("Apparel for men and women");
            productCategoryRepository.saveAll(List.of(category2));

            // Seed product catalogs
            ProductCatalog product1 = new ProductCatalog();
            product1.setName("Laptop");
            product1.setBrand("Dell");
            product1.setDescription("Dell XPS 13");
            product1.setGlobalSKU("SKU001");
            product1.setCategory(category1);
            productCatalogRepository.save(product1);

            ProductCatalog product2 = new ProductCatalog();
            product2.setName("T-Shirt");
            product2.setBrand("Nike");
            product2.setDescription("Nike Dri-FIT T-Shirt");
            product2.setGlobalSKU("SKU002");
            product2.setCategory(category2);
            productCatalogRepository.save(product2);

            // Seed users
            User user1 = new User();
            user1.setFirstName("John");
            user1.setLastName("Doe");
            user1.setEmail("customer@example.com");
            user1.setPasswordHash("password");
            user1.setRole(UserRole.CUSTOMER);
            user1.setPhoneNumber("123-456-7890");
            userRepository.save(user1);

            User user2 = new User();
            user2.setFirstName("Jane");
            user2.setLastName("Smith");
            user2.setEmail("vendor@example.com");
            user2.setPasswordHash("password");
            user2.setRole(UserRole.VENDOR);
            user2.setPhoneNumber("987-654-3210");
            userRepository.save(user2);

            User adminUser = new User();
            adminUser.setFirstName("Admin");
            adminUser.setLastName("User");
            adminUser.setEmail("admin@example.com");
            adminUser.setPasswordHash("password");
            adminUser.setRole(UserRole.ADMIN);
            adminUser.setPhoneNumber("111-222-3333");
            userRepository.save(adminUser);

            // Seed addresses
            Address address1 = new Address();
            address1.setStreet("123 Main St");
            address1.setCity("Anytown");
            address1.setState("CA");
            address1.setZipCode("12345");
            address1.setCountry("USA");
            address1.setUser(user1);
            address1.setAddressType(AddressType.SHIPPING);
            addressRepository.save(address1);

            Address address2 = new Address();
            address2.setStreet("456 Oak Ave");
            address2.setCity("Otherville");
            address2.setState("NY");
            address2.setZipCode("67890");
            address2.setCountry("USA");
            address2.setUser(user1);
            address2.setAddressType(AddressType.BILLING);
            addressRepository.save(address2);

            // Seed vendor profiles
            VendorProfile vendorProfile1 = new VendorProfile();
            vendorProfile1.setUser(user2);
            vendorProfile1.setStoreName("Jane's Boutique");
            vendorProfile1.setStoreDescription("A trendy clothing store");
            vendorProfile1.setContactNumber("555-123-4567");
            vendorProfile1.setApprovalStatus(ApprovalStatus.PENDING);
            vendorProfileRepository.save(vendorProfile1);

            // Seed vendor products
            ProductCatalog prodCat = productCatalogRepository.findByGlobalSKU("SKU002").orElse(null);
            if (prodCat == null) {
                prodCat = product2;
            }

            VendorProduct vendorProduct1 = new VendorProduct();
            vendorProduct1.setProductCatalog(product1);
            vendorProduct1.setVendorId(vendorProfile1);
            vendorProduct1.setSKU("VP001");
            vendorProduct1.setPrice(new BigDecimal("25.00"));
            vendorProduct1.setStockQuantity(100);
            vendorProduct1.setApprovalStatus(ApprovalStatus.PENDING);
            vendorProduct1.setActive(true);
            vendorProduct1.setName("Nike Dri-FIT T-Shirt - Vendor Edition");
            vendorProduct1.setDescription("Exclusive Nike Dri-FIT T-Shirt offered by Jane's Boutique");
            vendorProduct1.setAverageRating(new BigDecimal("4.5"));
            vendorProductRepository.save(vendorProduct1);

            VendorProduct vendorProduct2 = new VendorProduct();
            vendorProduct2.setProductCatalog(product2);
            vendorProduct2.setVendorId(vendorProfile1);
            vendorProduct2.setSKU("VP002");
            vendorProduct2.setName("Nike Dri-FIT T-Shirt - Alternate Edition");
            vendorProduct2.setDescription("Another Exclusive Nike Dri-FIT T-Shirt offered by Jane's Boutique");
            vendorProduct2.setPrice(new BigDecimal("30.00"));
            vendorProduct2.setStockQuantity(50);
            vendorProduct2.setApprovalStatus(ApprovalStatus.APPROVED);
            vendorProduct2.setActive(true);
            vendorProduct2.setAverageRating(new BigDecimal("4.8"));
            vendorProductRepository.save(vendorProduct2);

            // Seed orders
            ProductCatalog laptop = productCatalogRepository.findByName("Laptop").orElse(product1);
            ProductCatalog tShirt = productCatalogRepository.findByName("T-Shirt").orElse(product2);

            VendorProduct vendorProductLaptop = vendorProductRepository.findByProductCatalog_Id(laptop.getId())
                    .orElse(null);
            VendorProduct vendorProductTShirt = vendorProductRepository.findByProductCatalog_Id(tShirt.getId())
                    .orElse(null);

            Order order1 = new Order();
            order1.setCustomer(user1);
            order1.setOrderDate(LocalDateTime.now());
            order1.setOrderStatus(OrderStatus.SHIPPED);
            orderRepository.save(order1);

            // Seed order items for order1
            if (vendorProductLaptop != null) {
                OrderItem orderItem1 = new OrderItem();
                orderItem1.setOrder(order1);
                orderItem1.setVendorProduct(vendorProductLaptop);
                orderItem1.setQuantity(1);
                orderItem1.setPriceAtPurchase(new BigDecimal("999.99"));
                orderItemRepository.save(orderItem1);

                order1.setTotalAmount(
                        orderItem1.getPriceAtPurchase().multiply(new BigDecimal(orderItem1.getQuantity())));
                orderRepository.save(order1);
            }

            Order order2 = new Order();
            order2.setCustomer(user1);
            order2.setOrderDate(LocalDateTime.now().minusDays(1));
            order2.setOrderStatus(OrderStatus.DELIVERED);
            orderRepository.save(order2);

            // Seed order items for order2
            if (vendorProductTShirt != null) {
                OrderItem orderItem2 = new OrderItem();
                orderItem2.setOrder(order2);
                orderItem2.setVendorProduct(vendorProductTShirt);
                orderItem2.setQuantity(2);
                orderItem2.setPriceAtPurchase(new BigDecimal("25.00"));
                orderItemRepository.save(orderItem2);

                order2.setTotalAmount(
                        orderItem2.getPriceAtPurchase().multiply(new BigDecimal(orderItem2.getQuantity())));
                orderRepository.save(order2);
            }

            Order order3 = new Order();
            order3.setCustomer(user1);
            order3.setOrderDate(LocalDateTime.now().minusDays(2));
            order3.setOrderStatus(OrderStatus.PENDING_PAYMENT);
            orderRepository.save(order3);

            // Seed order items for order3
            if (vendorProductLaptop != null) {
                OrderItem orderItem3 = new OrderItem();
                orderItem3.setOrder(order3);
                orderItem3.setVendorProduct(vendorProductLaptop);
                orderItem3.setQuantity(1);
                orderItem3.setPriceAtPurchase(new BigDecimal("999.99"));
                orderItemRepository.save(orderItem3);

                order3.setTotalAmount(
                        orderItem3.getPriceAtPurchase().multiply(new BigDecimal(orderItem3.getQuantity())));
                orderRepository.save(order3);
            }

            // Seed reviews
            Review review1 = new Review();
            review1.setVendorProduct(vendorProduct1);
            review1.setCustomer(user1);
            review1.setOrder(order1);
            review1.setRating(5);
            review1.setComment("Great product!");
            reviewRepository.save(review1);

            // Seed shopping carts
            ShoppingCart cart1 = new ShoppingCart();
            cart1.setCustomer(user1);
            shoppingCartRepository.save(cart1);

            // Seed cart items
            CartItem cartItem1 = new CartItem();
            cartItem1.setVendorProduct(vendorProduct1);
            cartItem1.setQuantity(2);
            cartItemRepository.save(cartItem1);

            cart1.getCartItems().add(cartItem1);
            shoppingCartRepository.save(cart1);
        } else {
            System.out.println("Data already seeded. Skipping data seeding.");
        }
    }
}
