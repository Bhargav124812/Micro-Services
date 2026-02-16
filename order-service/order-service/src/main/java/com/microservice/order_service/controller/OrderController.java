package com.microservice.order_service.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.order_service.dto.OrderRequestDto;
import com.microservice.order_service.dto.OrderRequestItemDto;
import com.microservice.order_service.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/helloOrders")
    public String helloOrders() {
        return "Hello from Orders Service";
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
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody JsonNode payload) {
        try {
            OrderRequestDto orderRequestDto;
            if (payload.isArray()) {
                List<OrderRequestItemDto> items = objectMapper.convertValue(payload, new TypeReference<List<OrderRequestItemDto>>() {});
                orderRequestDto = new OrderRequestDto();
                orderRequestDto.setItems(items);
            } else {
                orderRequestDto = objectMapper.treeToValue(payload, OrderRequestDto.class);
            }

            OrderRequestDto orderRequestDto1 = orderService.createOrder(orderRequestDto);
            return ResponseEntity.ok(orderRequestDto1);
        } catch (Exception ex) {
            log.error("Failed to parse create-order payload", ex);
            return ResponseEntity.badRequest().build();
        }
    }
}
