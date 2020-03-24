package com.tvh.bootcamp.api.web;

import com.tvh.bootcamp.application.OrderService;
import com.tvh.bootcamp.domain.Order;
import com.tvh.bootcamp.dto.OrderDTOMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
public class OrderControllerIT {

    private List<Order> testOrders = List.of(
            Order.builder().id(UUID.randomUUID().toString())
                    .client("Jihad")
                    .creationDateTime(ZonedDateTime.of(2020, 2, 2, 2, 2, 2, 2, ZoneId.systemDefault()))
                    .orderLines(new ArrayList<>(List.of(
                            new Order.Line("gun", 5, 2),
                            new Order.Line("MAG", 3, 10),
                            new Order.Line("bullet", 1, 20),
                            new Order.Line("silver bullet", 10, 1))))
                    .build(),
            Order.builder().id(UUID.randomUUID().toString())
                    .client("Belmont")
                    .creationDateTime(ZonedDateTime.of(2020, 3, 2, 2, 2, 2, 2, ZoneId.systemDefault()))
                    .orderLines(new ArrayList<>(List.of(
                            new Order.Line("cross", 5, 2),
                            new Order.Line("whip", 3, 10),
                            new Order.Line("booze", 1, 20),
                            new Order.Line("silver bullet", 10, 1))))
                    .build());

    private String allOrdersAsJson = "[{\"id\":\"" + testOrders.get(0).getId() + "\",\"client\":\"Jihad\",\"creationDateTime\":\"2020-02-02T02:02:02.000000002+01:00\",\"orderLines\":[{\"price\":5.0,\"product\":\"gun\",\"amount\":2},{\"price\":3.0,\"product\":\"MAG\",\"amount\":10},{\"price\":1.0,\"product\":\"bullet\",\"amount\":20},{\"price\":10.0,\"product\":\"silver bullet\",\"amount\":1}]},{\"id\":\"" + testOrders.get(1).getId().toString() + "\",\"client\":\"Belmont\",\"creationDateTime\":\"2020-03-02T02:02:02.000000002+01:00\",\"orderLines\":[{\"price\":5.0,\"product\":\"cross\",\"amount\":2},{\"price\":3.0,\"product\":\"whip\",\"amount\":10},{\"price\":1.0,\"product\":\"booze\",\"amount\":20},{\"price\":10.0,\"product\":\"silver bullet\",\"amount\":1}]}]";
    private String oneOrderAsJson = "{\"id\":\"" + testOrders.get(0).getId() + "\",\"client\":\"Jihad\",\"creationDateTime\":\"2020-02-02T02:02:02.000000002+01:00\",\"orderLines\":[{\"price\":5.0,\"product\":\"gun\",\"amount\":2},{\"price\":3.0,\"product\":\"MAG\",\"amount\":10},{\"price\":1.0,\"product\":\"bullet\",\"amount\":20},{\"price\":10.0,\"product\":\"silver bullet\",\"amount\":1}]}";
    private String orderSinceAsJson = "[{\"id\":\"" + testOrders.get(1).getId() + "\",\"client\":\"Belmont\",\"creationDateTime\":\"2020-03-02T02:02:02.000000002+01:00\",\"orderLines\":[{\"price\":5.0,\"product\":\"cross\",\"amount\":2},{\"price\":3.0,\"product\":\"whip\",\"amount\":10},{\"price\":1.0,\"product\":\"booze\",\"amount\":20},{\"price\":10.0,\"product\":\"silver bullet\",\"amount\":1}]}]";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService service;

    private OrderDTOMapper mapper = new OrderDTOMapper();


    @Test
    void getAllOrdersIT() throws Exception {
        Mockito.when(service.getAllOrders()).thenReturn(testOrders.stream().map(mapper::mapToDto).collect(Collectors.toCollection(ArrayList::new)));

        MvcResult mvcResult = mockMvc.perform(get("/main/orders")).andReturn();

        assertEquals(mvcResult.getResponse().getContentAsString(), allOrdersAsJson);

    }

    @Test
    void getOrderByIdIT() throws Exception {
        Mockito.when(service.getOrder(testOrders.get(0).getId())).thenReturn(mapper.mapToDto(testOrders.get(0)));

        MvcResult mvcResult = mockMvc.perform(get("/main/orders/" + testOrders.get(0).getId())).andReturn();
        assertEquals(mvcResult.getResponse().getContentAsString(), oneOrderAsJson);
    }

    @Test
    void getOrderSinceIT() throws Exception {
        Mockito.when(service.getAllOrders()).thenReturn(testOrders.stream().map(mapper::mapToDto).collect(Collectors.toCollection(ArrayList::new)));

        MvcResult mvcResult = mockMvc.perform(get("/main/orders/?since=20200301")).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertEquals(mvcResult.getResponse().getContentAsString(), orderSinceAsJson);
    }

}
