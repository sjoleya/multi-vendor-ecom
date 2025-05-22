package com.vena.ecom.service.impl;

import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.CartItem;
import com.vena.ecom.model.Order;
import com.vena.ecom.model.OrderItem;
import com.vena.ecom.model.Review;
import com.vena.ecom.model.ShoppingCart;
import com.vena.ecom.model.User;
import com.vena.ecom.model.Address;
import com.vena.ecom.model.VendorProduct;
import com.vena.ecom.repo.CartItemRepository;
import com.vena.ecom.repo.OrderItemRepository;
import com.vena.ecom.repo.OrderRepository;
import com.vena.ecom.repo.ReviewRepository;
import com.vena.ecom.repo.ShoppingCartRepository;
import com.vena.ecom.repo.AddressRepository;
import com.vena.ecom.repo.UserRepository;
import com.vena.ecom.service.OrderService;
import com.vena.ecom.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository userAddressRepository;

    @Override
    public Order checkout(String customerId, String addressId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByCustomer_Id(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found"));

        User user = userRepository.findById(customerId).get();
        Address address = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address with id: " + addressId + "not found!"));
        Order order = new Order();
        order.setCustomer(user);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(com.vena.ecom.model.enums.OrderStatus.PENDING_PAYMENT);
        order.setShippingAddress(address);

        List<CartItem> cartItems = cartItemRepository.findByCart_Id(shoppingCart.getId());
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {

            VendorProduct vendorProduct = cartItem.getVendorProduct();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setVendorProduct(cartItem.getVendorProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(vendorProduct.getPrice());
            orderItem.setSubtotal(orderItem.getPriceAtPurchase().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItem.setItemStatus(com.vena.ecom.model.enums.ItemStatus.PENDING);

            totalAmount = totalAmount.add(orderItem.getSubtotal());
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);

        shoppingCartService.clearCart(customerId);
        return savedOrder;
    }

    @Override
    public List<Order> getOrderHistory(String customerId) {
        return orderRepository.findByCustomer_Id(customerId);
    }

    @Override
    public Order getOrderDetails(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public Review submitProductReview(String orderId, String orderItemId, String customerId, Review review) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));
        if (!order.getCustomer().getId().equals(customerId)) {
            throw new ResourceNotFoundException("Customer mismatch");
        }

        review.setOrder(order);
        review.setOrderItem(orderItem);
        review.setCustomer(order.getCustomer());
        return reviewRepository.save(review);
    }

}
