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

    private OrderRepository repository;

    @BeforeEach
    void setUp(){
        testOrder = Order.builder().id(UUID.randomUUID().toString())
                .client("Jihad")
                .creationDateTime(ZonedDateTime.now())
                .orderLines(new ArrayList<>(List.of(
                        new Order.Line("gun", 5, 2),
                        new Order.Line("MAG", 3, 10),
                        new Order.Line("bullet", 1, 20),
                        new Order.Line("silver bullet", 10, 1))))
                .build();
    }

    @Test
    void placeHolder(){
        assertEquals(true,true);

    }

}