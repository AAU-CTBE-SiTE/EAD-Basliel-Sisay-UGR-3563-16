package com.macroservices.customer.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

@Component
public class OrderClient {

    private static final Logger log = LoggerFactory.getLogger(OrderClient.class);
    private final RestClient restClient;

    public OrderClient(RestClient orderRestClient) {
        this.restClient = orderRestClient;
    }

    public JsonNode getOrdersByCustomerId(Long customerId) {
        try {
            return restClient.get()
                    .uri("/api/orders/customer/{customerId}", customerId)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(JsonNode.class);
        } catch (Exception e) {
            log.error("Failed to retrieve orders for customer ID {} from Order Service: {}", customerId, e.getMessage());
            throw new RuntimeException("Error communicating with Order Service: " + e.getMessage(), e);
        }
    }
}
