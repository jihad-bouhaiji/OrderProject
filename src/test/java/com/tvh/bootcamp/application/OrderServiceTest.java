package com.tvh.bootcamp.application;

import com.tvh.bootcamp.application.EmptyOrderException;
import com.tvh.bootcamp.application.OrderNotFoundException;
import com.tvh.bootcamp.application.OrderService;
import com.tvh.bootcamp.domain.Order;
import com.tvh.bootcamp.dto.OrderDTOMapper;
import com.tvh.bootcamp.dto.OrderDto;
import com.tvh.bootcamp.infrastructure.OrderRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    private List<Order> testOrders;
    private OrderDto basicDto;

    private OrderDTOMapper mapper = new OrderDTOMapper();

    @Mock
    private OrderRepository repository;

    private OrderService service;

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

        service = new OrderService(repository,mapper);

    }

    @Test
    void GetAllOrdersTest() {
        Mockito.when(repository.getAll()).thenReturn(testOrders);
        List<OrderDto> allOrders = service.getAllOrders();
        assertEquals(testOrders.size(), allOrders.size());
    }

    @Test
    void RemoveOrderCallTest() {
        UUID idToDelete = UUID.randomUUID();
        Mockito.doNothing().when(repository).removeOrder(idToDelete);
        service.removeOrder(idToDelete);
        Mockito.verify(repository).removeOrder(idToDelete);
    }

    @Test
    void AddBasicOrderTest() {
        Mockito.doNothing().when(repository).add(any(Order.class));
        assertDoesNotThrow(() -> service.addOrder(basicDto));
        Mockito.verify(repository).add(any(Order.class));
    }

    @Test
    void GetFirstOrderFromTestOrders() {
        Mockito.doReturn(testOrders.get(0)).when(repository).getOrder(testOrders.get(0).getId());
        OrderDto dto = assertDoesNotThrow(() -> service.getOrder(testOrders.get(0).getId()));
        assertEquals(dto.getId(), testOrders.get(0).getId());
    }

    @Test
    void GetOrderNotInRepositoryTest() {
        UUID idToGet = UUID.randomUUID();
        Mockito.doReturn(null).when(repository).getOrder(idToGet);
        assertThrows(OrderNotFoundException.class, () -> service.getOrder(idToGet));
        Mockito.verify(repository).getOrder(idToGet);
    }

    @Test
    void AddEmptyOrderTest() {
        assertThrows(EmptyOrderException.class, () -> service.addOrder(null));
        Mockito.verifyNoInteractions(repository);
    }

}
