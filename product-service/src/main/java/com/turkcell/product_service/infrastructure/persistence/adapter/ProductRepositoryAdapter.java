package com.turkcell.product_service.infrastructure.persistence.adapter;

import com.turkcell.product_service.domain.entity.Product;
import com.turkcell.product_service.domain.entity.Product.ProductId;
import com.turkcell.product_service.domain.repositories.ProductRepository;
import com.turkcell.product_service.infrastructure.persistence.entity.JpaProductEntity;
import com.turkcell.product_service.infrastructure.persistence.mapper.ProductEntityMapper;
import com.turkcell.product_service.infrastructure.persistence.repository.SpringDataProductRepository;

import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryAdapter implements ProductRepository {

    private final SpringDataProductRepository repository;
    private final ProductEntityMapper mapper;

    public ProductRepositoryAdapter(SpringDataProductRepository repository, ProductEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Product save(Product product) {
        JpaProductEntity entity = mapper.toEntity(product);
        entity = repository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public Optional<Product> findById(ProductId productId) {
        return repository
                .findById(productId.getValue()                )
                .map(mapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toDomain) // Method Reference
                .toList();
    }

    @Override
    public List<Product> findAllPaged(Integer pageIndex, Integer pageSize) {
        return repository
                .findAll(PageRequest.of(pageIndex, pageSize))
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(ProductId productId) {
        repository.deleteById(productId.getValue());
    }

}
