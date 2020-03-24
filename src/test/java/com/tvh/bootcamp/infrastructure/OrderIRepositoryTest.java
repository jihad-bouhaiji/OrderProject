package com.tvh.bootcamp.infrastructure;

import com.tvh.bootcamp.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderIRepositoryTest {

    private Order testOrder;

    @Mock
    private TreeMap<UUID, Order> orders;

    @InjectMocks
    private OrderRepository repository;

    @BeforeEach
    void setUp(){
        testOrder = Order.builder().withId(UUID.randomUUID())
                .withClient("Jihad")
                .withCreationDateTime(ZonedDateTime.now())
                .withOrderLines(new ArrayList<Order.Line>(List.of(
                        new Order.Line("gun", 5, 2),
                        new Order.Line("MAG", 3, 10),
                        new Order.Line("bullet", 1, 20),
                        new Order.Line("silver bullet", 10, 1))))
                .build();
    }
    @Test
    void AddOrderTest(){
        doReturn(null).when(orders).put(testOrder.getId(),testOrder);
        repository.add(testOrder);
        verify(orders).put(testOrder.getId(),testOrder);
    }

    @Test
    void RemoveOrderTest(){
        doReturn(null).when(orders).remove(testOrder.getId());
        repository.removeOrder(testOrder.getId());
        verify(orders).remove(testOrder.getId());
    }

    @Test
    void getOrderReturnsOrderTest(){
        when(orders.get(testOrder.getId())).thenReturn(testOrder);
        Order foundOrder = repository.getOrder(testOrder.getId());
        assertEquals(foundOrder,testOrder);
    }

    @Test
    void GetUnknownOrderReturnsNull(){
        when(orders.get(any(UUID.class))).thenReturn(null);
        Order foundOrder = repository.getOrder(UUID.randomUUID());
        assertNull(foundOrder);
    }

    @Test
    void GetAllOrdersTest(){
        when(orders.values()).thenReturn(List.of(testOrder));
        List<Order> allOrders = repository.getAll();
        assertEquals(allOrders,List.of(testOrder));
        verify(orders).values();
    }

}