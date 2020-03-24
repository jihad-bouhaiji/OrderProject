package com.tvh.bootcamp.dto;

import com.tvh.bootcamp.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderDTOMapperTest {

    private OrderDto testDto;
    private Order testOrder;
    private OrderDTOMapper mapper;

    @BeforeEach
    void setUp(){
        testDto = OrderDto.builder()
                .withId(UUID.randomUUID())
                .withClient("Belmont")
                .withCreationDateTime(ZonedDateTime.now().toString())
                .withOrderLines(new ArrayList<OrderDto.LineDto>(List.of(
                        new OrderDto.LineDto("gun", 5, 2),
                        new OrderDto.LineDto("booze", 3, 1000000),
                        new OrderDto.LineDto("whip", 1, 20),
                        new OrderDto.LineDto("Sypha", 10000000, 1))))
                .build();

        testOrder = Order.builder().withId(UUID.randomUUID())
                .withClient("Jihad")
                .withCreationDateTime(ZonedDateTime.now())
                .withOrderLines(new ArrayList<Order.Line>(List.of(
                        new Order.Line("gun", 5, 2),
                        new Order.Line("MAG", 3, 10),
                        new Order.Line("bullet", 1, 20),
                        new Order.Line("silver bullet", 10, 1))))
                .build();
        mapper = new OrderDTOMapper();
    }

    @Test
    void mapToOrder() {
        Order mappedOrder = mapper.mapToOrder(testDto);

        assertEquals(mappedOrder.getId(),testDto.getId());
        assertEquals(mappedOrder.getClient(), testDto.getClient());
        assertEquals(mappedOrder.getCreationDateTime().toString(),testDto.getCreationDateTime());
        assertEquals(mappedOrder.getOrderLines().get(0).getProduct(),testDto.getOrderLines().get(0).getProduct());
    }

    @Test
    void mapToDto() {
        OrderDto mappedDto = mapper.mapToDto(testOrder);

        assertEquals(testOrder.getId(),mappedDto.getId());
        assertEquals(testOrder.getClient(), mappedDto.getClient());
        assertEquals(testOrder.getCreationDateTime().toString(),mappedDto.getCreationDateTime());
        assertEquals(testOrder.getOrderLines().get(0).getProduct(),mappedDto.getOrderLines().get(0).getProduct());
    }

    @Test
    void MapToDtoAndBackToOrder(){
        Order mappedOrder = mapper.mapToOrder(mapper.mapToDto(testOrder));

        assertEquals(testOrder,mappedOrder);
    }
}