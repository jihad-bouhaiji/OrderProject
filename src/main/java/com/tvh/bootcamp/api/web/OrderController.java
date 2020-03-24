package com.tvh.bootcamp.api.web;

import com.tvh.bootcamp.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class OrderController {
    private final OrderFacade facade;

    @GetMapping(value = "/orders", produces = "application/json")
    public List<Order> getAllOrders() {
        return facade.getAllOrders();
    }

    @GetMapping(value = "/orders/{id}", produces = "application/json")
    public Order getOrderWithId(@PathVariable String id){
        return facade.getOrder(UUID.fromString(id));
    }

    //since = yyyymmdd
    @GetMapping("/orders/")
    public List<Order> getOrdersSince(@RequestParam String since){
        List<Order> allOrders = facade.getAllOrders();
        return allOrders.stream()
                .filter(order -> order.getCreationDateTime().isAfter(ZonedDateTime.of(LocalDateTime.of(LocalDate.parse(since,DateTimeFormatter.BASIC_ISO_DATE), LocalTime.now()),ZoneId.systemDefault())))
                .collect(Collectors.toList());
    }

}
