package com.tvh.bootcamp.api.web;

import com.tvh.bootcamp.application.OrderService;
import com.tvh.bootcamp.application.PrintService;
import com.tvh.bootcamp.domain.Order;
import com.tvh.bootcamp.dto.OrderDTOMapper;
import com.tvh.bootcamp.dto.OrderDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class OrderFacadeTest {
    private List<Order> testOrders;
    private OrderDto basicDto;


    @Mock
    private OrderService orderService;

    @Mock
    private PrintService printService;

    @InjectMocks
    private OrderFacade orderFacade;

    @BeforeEach
    void setUp() {
        testOrders = List.of(
                Order.builder().withId(UUID.randomUUID())
                        .withClient("Jihad")
                        .withCreationDateTime(ZonedDateTime.now())
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

        basicDto = OrderDto.builder()
                .withId(UUID.randomUUID())
                .withClient("basic")
                .withCreationDateTime(ZonedDateTime.now().toString())
                .withOrderLines(new ArrayList<OrderDto.LineDto>(List.of(
                        new OrderDto.LineDto("gun", 5, 2),
                        new OrderDto.LineDto("MAG", 3, 10),
                        new OrderDto.LineDto("bullet", 1, 20),
                        new OrderDto.LineDto("silver bullet", 10, 1))))
                .build();
    }

    @Test
    void GetAllOrdersTest(){
        when(orderService.getAllOrders()).thenReturn(testOrders.stream()
                .map(OrderDTOMapper::mapToDto)
                .collect(Collectors.toList()));

        List<Order> orders = orderFacade.getAllOrders();
        assertEquals(orders.get(0).getId(),testOrders.get(0).getId());
    }

    @Test
    void PrintCallTest(){
        doReturn(List.of()).when(orderService).getAllOrders();

        orderFacade.printAllOrders();

        verify(orderService).getAllOrders();
        verify(printService).printOrders(any(List.class));
    }

    @SneakyThrows
    @Test
    void AddOrderTest() {
        ArgumentCaptor<OrderDto> dtoArgumentCaptor = ArgumentCaptor.forClass(OrderDto.class);
        doNothing().when(orderService).addOrder(dtoArgumentCaptor.capture());
        UUID idCreated = orderFacade.addOrder("test",List.of("test 2 2", "test 3 3"));

        verify(orderService, VerificationModeFactory.times(1)).addOrder(any());
        assertEquals(idCreated,dtoArgumentCaptor.getValue().getId());
    }

    @Test
    void RemoveOrderTest() {
        UUID randomId = UUID.randomUUID();
        orderFacade.removeOrder(randomId);

        verify(orderService).removeOrder(randomId);
    }

    @SneakyThrows
    @Test
    void getOrderTest(){
        UUID orderToGet = testOrders.get(0).getId();
        when(orderService.getOrder(orderToGet))
                .thenReturn(OrderDTOMapper.mapToDto(testOrders.get(0)));

        Order receivedOrder = orderFacade.getOrder(orderToGet);

        assertEquals(receivedOrder.getId(),orderToGet);
    }
}
