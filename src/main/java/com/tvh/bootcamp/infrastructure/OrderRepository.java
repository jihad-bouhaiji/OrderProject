package com.tvh.bootcamp.infrastructure;

import com.tvh.bootcamp.domain.Order;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Annotation;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

@Repository
public class OrderRepository implements IRepository<Order> {
    private TreeMap<UUID, Order> orders;

    public OrderRepository() {
        this.orders = new TreeMap<>();
        var testOrders = List.of(
                Order.builder().withId(UUID.randomUUID())
                        .withClient("Jihad")
                        .withCreationDateTime(ZonedDateTime.now().minusDays(5))
                        .withOrderLines(new ArrayList<Order.Line>(List.of(
                                new Order.Line("gun", 5, 2),
                                new Order.Line("MAG", 3, 10),
                                new Order.Line("bullet", 1, 20),
                                new Order.Line("silver bullet", 10, 1))))
                        .build(),
                Order.builder().withId(UUID.randomUUID())
                        .withClient("Belmontnow")
                        .withCreationDateTime(ZonedDateTime.now())
                        .withOrderLines(new ArrayList<Order.Line>(List.of(
                                new Order.Line("cross", 5, 2),
                                new Order.Line("whip", 3, 10),
                                new Order.Line("booze", 1, 20),
                                new Order.Line("silver bullet", 10, 1))))
                        .build(),
                Order.builder().withId(UUID.randomUUID())
                        .withClient("Belmont50")
                        .withCreationDateTime(ZonedDateTime.now().minusDays(50))
                        .withOrderLines(new ArrayList<Order.Line>(List.of(
                                new Order.Line("cross", 5, 2),
                                new Order.Line("whip", 3, 10),
                                new Order.Line("booze", 1, 20),
                                new Order.Line("silver bullet", 10, 1))))
                        .build(),
                Order.builder().withId(UUID.randomUUID())
                        .withClient("Belmont1")
                        .withCreationDateTime(ZonedDateTime.now().minusDays(1))
                        .withOrderLines(new ArrayList<Order.Line>(List.of(
                                new Order.Line("cross", 5, 2),
                                new Order.Line("whip", 3, 10),
                                new Order.Line("booze", 1, 20),
                                new Order.Line("silver bullet", 10, 1))))
                        .build());


        for (Order testOrder : testOrders) {
            this.orders.put(testOrder.getId(), testOrder);
        }

    }

    public Order getOrder(UUID id) {
        return orders.get(id);
    }

    public void removeOrder(UUID id) {
        orders.remove(id);
    }

    @Override
    public void add(Order toAdd) {
        orders.put(toAdd.getId(), toAdd);
    }

    @Override
    public List<Order> getAll() {
        return new ArrayList<>(orders.values());
    }

}
