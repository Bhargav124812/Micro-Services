package com.microservice.inventory_service.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "order-service", path = "/orders")
public interface OrderService {

    @GetMapping("/core/helloOrders")
    String helloOrders();
}
