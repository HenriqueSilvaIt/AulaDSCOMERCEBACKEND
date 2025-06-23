package com.hen.aula.repositories;

import com.hen.aula.entities.OrderItem;
import com.hen.aula.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> /*A
chave primária de orderItem é a classe orderItemPk*/{

}
