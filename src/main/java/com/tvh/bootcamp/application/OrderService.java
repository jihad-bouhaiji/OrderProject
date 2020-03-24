package com.tvh.bootcamp.application;

import com.tvh.bootcamp.domain.Order;
import com.tvh.bootcamp.dto.OrderDto;
import com.tvh.bootcamp.dto.OrderDTOMapper;
import com.tvh.bootcamp.infrastructure.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        repository.add(mapper.mapToOrder(dto));
    }

    public void removeOrder(UUID id){
        repository.removeOrder(id);
    }

    public OrderDto getOrder(UUID id) throws OrderNotFoundException{
        Order order = repository.getOrder(id);
        if(order == null) throw new OrderNotFoundException("Order with id: " + id + "could not be found" );
        return mapper.mapToDto(order);
    }

    public List<OrderDto> getAllOrders(){
        return repository.getAll().stream().map(OrderDTOMapper::mapToDto).collect(Collectors.toCollection(ArrayList::new));
    }
}
