package com.tvh.bootcamp.api.web;

import com.tvh.bootcamp.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    private List<Order> testOrders;

    @Mock
    private OrderFacade facade;

    @InjectMocks
    private OrderController controller;

    @BeforeEach
    void setUp(){
        testOrders = List.of(
                Order.builder().withId(UUID.randomUUID())
                        .withClient("Jihad")
                        .withCreationDateTime(ZonedDateTime.of(2020,02,2,2,2,2,2, ZoneId.systemDefault()))
                        .withOrderLines(new ArrayList<Order.Line>(List.of(
                                new Order.Line("gun", 5, 2),
                                new Order.Line("MAG", 3, 10),
                                new Order.Line("bullet", 1, 20),
                                new Order.Line("silver bullet", 10, 1))))
                        .build(),
                Order.builder().withId(UUID.randomUUID())
                        .withClient("Belmont")
                        .withCreationDateTime(ZonedDateTime.now())
                        .withOrderLines(new ArrayList<Order.Line>(List.of(
                                new Order.Line("cross", 5, 2),
                                new Order.Line("whip", 3, 10),
                                new Order.Line("booze", 1, 20),
                                new Order.Line("silver bullet", 10, 1))))
                        .build());
    }

    @Test
    void getAllOrdersTest(){
        Mockito.when(facade.getAllOrders()).thenReturn(testOrders);
        assertEquals(controller.getAllOrders(),testOrders);
    }

    @Test
    void GetOrderWithIdTest(){
        Mockito.when(facade.getOrder(testOrders.get(0).getId())).thenReturn(testOrders.get(0));
        assertEquals(controller.getOrderWithId(testOrders.get(0).getId().toString()),testOrders.get(0));
    }

    @Test
    void GetOrderSinceDateTest(){
        Mockito.when(facade.getAllOrders()).thenReturn(testOrders);
        List<Order> ordersSince = controller.getOrdersSince("20200301");
        assertEquals(ordersSince.get(0),testOrders.get(1));
    }
}
