package com.macroservices.customer.service;

import com.macroservices.customer.client.OrderClient;
import com.macroservices.customer.model.Customer;
import com.macroservices.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderClient orderClient;
    private final ObjectMapper objectMapper;

    public CustomerService(CustomerRepository customerRepository, OrderClient orderClient, ObjectMapper objectMapper) {
        this.customerRepository = customerRepository;
        this.orderClient = orderClient;
        this.objectMapper = objectMapper;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> updateCustomer(Long id, Customer updatedData) {
        return customerRepository.findById(id).map(existingCustomer -> {
            existingCustomer.setName(updatedData.getName());
            existingCustomer.setEmail(updatedData.getEmail());
            return customerRepository.save(existingCustomer);
        });
    }

    public boolean deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Inter-Service Communication via RestClient returning JsonNode (Approach C)
    @Transactional(readOnly = true)
    public JsonNode getCustomerOrders(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer not found with id: " + customerId);
        }
        return orderClient.getOrdersByCustomerId(customerId);
    }

    @Transactional(readOnly = true)
    public Optional<ObjectNode> getCustomerWithOrders(Long customerId) {
        return customerRepository.findById(customerId).map(customer -> {
            JsonNode ordersNode = orderClient.getOrdersByCustomerId(customerId);
            ObjectNode customerNode = objectMapper.valueToTree(customer);
            customerNode.set("orders", ordersNode);
            return customerNode;
        });
    }
}
