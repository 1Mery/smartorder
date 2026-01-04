package com.turkcell.orderservice.infrastructure.persistence;

import com.turkcell.orderservice.infrastructure.persistence.entity.JpaOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataOrderRepository extends JpaRepository<JpaOrderEntity, UUID> {
}
