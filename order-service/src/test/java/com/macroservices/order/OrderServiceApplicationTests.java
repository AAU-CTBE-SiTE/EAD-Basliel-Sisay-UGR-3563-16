package com.macroservices.order;

import com.macroservices.order.model.Order;
import com.macroservices.order.repository.OrderRepository;
import com.macroservices.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceApplicationTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
    }

    @Test
    void testCreateAndFindOrder() {
        Order order = new Order(150.0, 1L, "Mechanical Keyboard");
        Order saved = orderService.createOrder(order);

        assertNotNull(saved.getId());
        assertEquals("Mechanical Keyboard", saved.getProduct());
        assertEquals(1L, saved.getCustomerId());
        assertEquals(150.0, saved.getAmount());

        Optional<Order> found = orderService.getOrderById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Mechanical Keyboard", found.get().getProduct());
    }

    @Test
    void testGetOrdersByCustomerId() {
        orderService.createOrder(new Order(100.0, 1L, "Headphones"));
        orderService.createOrder(new Order(50.0, 1L, "USB Cable"));
        orderService.createOrder(new Order(800.0, 2L, "Smartphone"));

        List<Order> customer1Orders = orderService.getOrdersByCustomerId(1L);
        assertEquals(2, customer1Orders.size());

        List<Order> customer2Orders = orderService.getOrdersByCustomerId(2L);
        assertEquals(1, customer2Orders.size());
        assertEquals("Smartphone", customer2Orders.get(0).getProduct());

        List<Order> customer3Orders = orderService.getOrdersByCustomerId(3L);
        assertTrue(customer3Orders.isEmpty());
    }

    @Test
    void testDeleteOrder() {
        Order order = orderService.createOrder(new Order(20.0, 1L, "Mousepad"));
        Long id = order.getId();

        boolean deleted = orderService.deleteOrder(id);
        assertTrue(deleted);
        assertFalse(orderService.getOrderById(id).isPresent());
    }
}
