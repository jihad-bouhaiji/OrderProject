package com.tvh.bootcamp.dto;

import com.tvh.bootcamp.domain.Order;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Component
public class OrderDTOMapper {
    public static Order mapToOrder(OrderDto DTO) {
        ArrayList<Order.Line> orderLines = new ArrayList<>();
        for (OrderDto.LineDto dto : DTO.getOrderLines()) {
            orderLines.add(
                    new Order.Line(dto.getProduct(), dto.getPrice(), dto.getAmount())
            );
        }

        return Order.builder().withId(DTO.getId())
                .withClient(DTO.getClient())
                .withCreationDateTime(ZonedDateTime.parse(DTO.getCreationDateTime()))
                .withOrderLines(orderLines)
                .build();
    }

    public static OrderDto mapToDto(Order order)
    {
        ArrayList<OrderDto.LineDto> dtoLines = new ArrayList<>();
        for (Order.Line line : order.getOrderLines()) {
            dtoLines.add(new OrderDto.LineDto(line.getProduct(),line.getPrice(),line.getAmount()));
        }
        return OrderDto.builder().withId(order.getId())
                .withClient(order.getClient())
                .withCreationDateTime(order.getCreationDateTime().toString())
                .withOrderLines(dtoLines)
                .build();
    }
}
