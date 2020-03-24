package com.tvh.bootcamp.infrastructure;

import com.tvh.bootcamp.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Order.class, idClass = String.class)
public interface OrderRepository extends CrudRepository<Order,String> {


}
