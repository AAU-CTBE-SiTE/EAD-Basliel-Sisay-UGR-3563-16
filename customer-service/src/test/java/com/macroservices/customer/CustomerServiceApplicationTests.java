package com.macroservices.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.macroservices.customer.client.OrderClient;
import com.macroservices.customer.model.Customer;
import com.macroservices.customer.repository.CustomerRepository;
import com.macroservices.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerServiceApplicationTests {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderClient orderClient;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    void testCreateAndFindCustomer() {
        Customer customer = new Customer("John Doe", "john@example.com");
        Customer saved = customerService.createCustomer(customer);

        assertNotNull(saved.getId());
        assertEquals("John Doe", saved.getName());
        assertEquals("john@example.com", saved.getEmail());

        Optional<Customer> found = customerService.getCustomerById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("John Doe", found.get().getName());
    }

    @Test
    void testUpdateCustomer() {
        Customer customer = customerService.createCustomer(new Customer("Jane", "jane@example.com"));
        customer.setName("Jane Doe");
        customer.setEmail("janedoe@example.com");

        Optional<Customer> updated = customerService.updateCustomer(customer.getId(), customer);
        assertTrue(updated.isPresent());
        assertEquals("Jane Doe", updated.get().getName());
        assertEquals("janedoe@example.com", updated.get().getEmail());
    }

    @Test
    void testDeleteCustomer() {
        Customer customer = customerService.createCustomer(new Customer("Mark", "mark@example.com"));
        Long id = customer.getId();

        boolean deleted = customerService.deleteCustomer(id);
        assertTrue(deleted);
        assertFalse(customerService.getCustomerById(id).isPresent());
    }

    @Test
    void testGetCustomerWithOrdersInterServiceWithoutDTO() {
        Customer customer = customerService.createCustomer(new Customer("Alice", "alice@example.com"));

        ArrayNode mockOrders = objectMapper.createArrayNode();
        mockOrders.addObject()
                .put("id", 101L)
                .put("amount", 250.0)
                .put("customerId", customer.getId())
                .put("product", "Mechanical Keyboard");

        when(orderClient.getOrdersByCustomerId(customer.getId())).thenReturn(mockOrders);

        Optional<ObjectNode> result = customerService.getCustomerWithOrders(customer.getId());

        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().get("name").asText());
        assertTrue(result.get().has("orders"));
        assertEquals(1, result.get().get("orders").size());
        assertEquals("Mechanical Keyboard", result.get().get("orders").get(0).get("product").asText());
    }
}
