package com.microservice.order_service.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.order_service.config.FeatureEnabledConfig;
import com.microservice.order_service.dto.OrderRequestDto;
import com.microservice.order_service.dto.OrderRequestItemDto;
import com.microservice.order_service.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
@RefreshScope
public class OrderController {

    private final OrderService orderService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final FeatureEnabledConfig featureEnabledConfig;

    @Value("${my.variable}")
    private String myVariable;

    @GetMapping("/helloOrders")
    public String helloOrders() {
        if (featureEnabledConfig.isUserTrackingEnabled()) {
            return "User tracking enabled wohoo, my variable is: "+ myVariable;
        } else {
            return "User tracking disabled awww, my variable is: "+ myVariable;
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders(HttpServletRequest httpServletRequest) {
        log.info("Fetching all orders via controller");
        List<OrderRequestDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);  // Returns 200 OK with the list of orders
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long id) {
        log.info("Fetching order with ID: {} via controller", id);
        OrderRequestDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);  // Returns 200 OK with the order
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderRequestDto orderRequestDto1 = orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(orderRequestDto1);
    }
}
