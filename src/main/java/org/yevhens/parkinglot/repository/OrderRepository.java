package org.yevhens.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yevhens.parkinglot.entity.Receipt;

public interface OrderRepository extends JpaRepository<Receipt, Long> {
}