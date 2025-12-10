package org.yevhens.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yevhens.parkinglot.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}