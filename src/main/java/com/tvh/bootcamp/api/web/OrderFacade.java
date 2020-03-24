package com.tvh.bootcamp.api.web;


import com.tvh.bootcamp.application.EmptyOrderException;
import com.tvh.bootcamp.application.OrderNotFoundException;
import com.tvh.bootcamp.application.OrderService;
import com.tvh.bootcamp.application.PrintService;
import com.tvh.bootcamp.domain.Order;
import com.tvh.bootcamp.dto.OrderDto;
import com.tvh.bootcamp.dto.OrderDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final PrintService printService;


    public UUID addOrder(String client, List<String> orderLines){
        OrderDto dto = new OrderDto(UUID.randomUUID(),client, ZonedDateTime.now().toString(),parseOrderLines(orderLines));
        try {
            orderService.addOrder(dto);
        } catch (EmptyOrderException e) {
            e.printStackTrace();
        }
        return dto.getId();
    }

    public Order getOrder(UUID id){
        try{
            return OrderDTOMapper.mapToOrder(orderService.getOrder(id));
        } catch (OrderNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void removeOrder(UUID id) { orderService.removeOrder(id);}

    public List<Order> getAllOrders(){
        return orderService.getAllOrders()
                .stream()
                .map(OrderDTOMapper::mapToOrder)
                .collect(Collectors.toList());
    }

    public void printAllOrders(){
        printService.printOrders(getAllOrders());
    }


    private ArrayList<OrderDto.LineDto> parseOrderLines(List<String> input){
        ArrayList<OrderDto.LineDto> dto = new ArrayList<>();
        for (String current : input) {
            String[] options = current.split(" ");
            dto.add(new OrderDto.LineDto(options[0],Double.parseDouble(options[1]),Integer.parseInt(options[2])));
        }
        return dto;
    }
}
