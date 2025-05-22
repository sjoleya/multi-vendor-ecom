package com.vena.ecom.utils;

import com.vena.ecom.model.*;
import com.vena.ecom.model.enums.ApprovalStatus;
import com.vena.ecom.model.enums.OrderStatus;
import com.vena.ecom.model.enums.UserRole;
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
    private AddressRepository addressRepository;

    @Override
    public void run(String... args) throws Exception {
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
        user1.setEmail("john.doe@example.com");
        user1.setPasswordHash("password");
        user1.setRole(UserRole.CUSTOMER);
        user1.setPhoneNumber("123-456-7890");
        userRepository.save(user1);

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setEmail("jane.smith@example.com");
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
        addressRepository.save(address1);

        // Seed vendor profiles
        VendorProfile vendorProfile1 = new VendorProfile();
        vendorProfile1.setUser(user2);
        vendorProfile1.setStoreName("Jane's Boutique");
        vendorProfile1.setStoreDescription("A trendy clothing store");
        vendorProfile1.setContactNumber("555-123-4567");
        vendorProfile1.setApprovalStatus(ApprovalStatus.APPROVED);
        vendorProfileRepository.save(vendorProfile1);

        // Seed vendor products
        ProductCatalog prodCat = productCatalogRepository.findByName("T-Shirt").orElse(null);
        if (prodCat == null) {
            prodCat = product2;
        }

        VendorProduct vendorProduct1 = new VendorProduct();
        vendorProduct1.setCatalogProductId(prodCat);
        vendorProduct1.setVendorId(vendorProfile1);
        vendorProduct1.setSKU("VP001");
        vendorProduct1.setPrice(new BigDecimal("25.00"));
        vendorProduct1.setStockQuantity(100);
        vendorProduct1.setApprovalStatus(ApprovalStatus.APPROVED);
        vendorProduct1.setActive(true);
        vendorProduct1.setAverageRating(new BigDecimal("4.5"));
        vendorProductRepository.save(vendorProduct1);

        // Seed orders
        Order order1 = new Order();
        order1.setCustomer(user1);
        order1.setOrderDate(LocalDateTime.now());
        order1.setTotalAmount(new BigDecimal("25.00"));
        order1.setOrderStatus(OrderStatus.SHIPPED);
        orderRepository.save(order1);

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
    }
}
