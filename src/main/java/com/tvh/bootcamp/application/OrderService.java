package com.tvh.bootcamp.application;

import com.tvh.bootcamp.domain.Order;
import com.tvh.bootcamp.dto.OrderDTOMapper;
import com.tvh.bootcamp.dto.OrderDto;
import com.tvh.bootcamp.infrastructure.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderService {
    private OrderRepository repository;
    private OrderDTOMapper mapper;

    public OrderService(OrderRepository repository, OrderDTOMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void addOrder(OrderDto dto) throws EmptyOrderException {
        if(dto == null) throw new EmptyOrderException("empty Object to add ");
        repository.save(mapper.mapToOrder(dto));
    }

    public void removeOrder(String id){
        repository.deleteById(id);
    }

    public OrderDto getOrder(String id) throws OrderNotFoundException{
        Optional<Order> order = repository.findById(id);
        if(order.isEmpty()) throw new OrderNotFoundException("Order with id: " + id + "could not be found" );
        return mapper.mapToDto(order.get());
    }

    public List<OrderDto> getAllOrders(){
        return StreamSupport.stream(repository.findAll().spliterator(),false).map(mapper::mapToDto).collect(Collectors.toCollection(ArrayList::new));
    }
}
