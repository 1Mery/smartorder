package com.turkcell.product_service.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.turkcell.product_service.infrastructure.persistence.entity.JpaProductEntity;


import java.util.UUID;

public interface SpringDataProductRepository extends JpaRepository<JpaProductEntity, UUID>
{

}
