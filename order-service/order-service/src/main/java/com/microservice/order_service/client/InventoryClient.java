package com.microservice.order_service.client;


import com.microservice.order_service.dto.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="inventory-service",path = "/inventory")
public interface InventoryClient {

    @PutMapping("/products/reduce-stocks")
    Double reduceStocks(@RequestBody OrderRequestDto orderRequestDto);
}
