package com.turkcell.productservice.infrastructure.persistence;

import com.turkcell.productservice.domain.model.Product;
import com.turkcell.productservice.domain.model.ProductId;
import com.turkcell.productservice.domain.ports.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaProductRepositoryAdapter implements ProductRepository {

    private final ProductEntityMapper mapper;
    private final SpringDataProductRepository repository;

    public JpaProductRepositoryAdapter(ProductEntityMapper mapper, SpringDataProductRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        JpaProductEntity entity = mapper.toEntity(product);
        JpaProductEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(ProductId productId) {
        return repository.findById(productId.value())
                .map(mapper::toDomain);
    }

    @Override
    public void delete(ProductId id) {
        repository.deleteById(id.value());
    }
}
