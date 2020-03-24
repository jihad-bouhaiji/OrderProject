package com.tvh.bootcamp;

import com.tvh.bootcamp.api.web.OrderFacade;
import com.tvh.bootcamp.domain.Order;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class OrderApp {

    @Bean
    public ApplicationRunner runner(OrderFacade facade){
        return args -> {
            List<Order> testOrders = List.of(
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
                            .orderLines(new ArrayList<Order.Line>(List.of(
                                    new Order.Line("cross", 5, 2),
                                    new Order.Line("whip", 3, 10),
                                    new Order.Line("booze", 1, 20),
                                    new Order.Line("silver bullet", 10, 1))))
                            .build());

            facade.addOrder(testOrders.get(0).getClient(),List.of("cross 3 2","gun 4 3"));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class,args);
    }
}
