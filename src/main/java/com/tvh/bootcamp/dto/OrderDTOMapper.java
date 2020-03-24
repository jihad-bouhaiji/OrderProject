package com.tvh.bootcamp.dto;

import com.tvh.bootcamp.domain.Order;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Component
public class OrderDTOMapper {

    public Order mapToOrder(OrderDto dto) {
        ArrayList<Order.Line> orderLines = new ArrayList<>();
        for (OrderDto.LineDto Dto : dto.getOrderLines()) {
            orderLines.add(
                    new Order.Line(Dto.getProduct(), Dto.getPrice(), Dto.getAmount())
            );
        }

        return Order.builder().id(dto.getId())
                .client(dto.getClient())
                .creationDateTime(ZonedDateTime.parse(dto.getCreationDateTime()))
                .orderLines(orderLines)
                .build();
    }

    public OrderDto mapToDto(Order order)
    {
        ArrayList<OrderDto.LineDto> dtoLines = new ArrayList<>();
        for (Order.Line line : order.getOrderLines()) {
            dtoLines.add(new OrderDto.LineDto(line.getProduct(),line.getPrice(),line.getAmount()));
        }
        return OrderDto.builder().id(order.getId())
                .client(order.getClient())
                .creationDateTime(order.getCreationDateTime().toString())
                .orderLines(dtoLines)
                .build();
    }
}
