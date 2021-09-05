package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> listProducts();

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    Product save(String name, String description, Double price, Integer quantity,
                 List<Long> categoryIds, Long manufacturerId);

    Optional<Product> edit(Long id, String name, String description, Double price, Integer quantity, List<Long> categoryIds, Long manufacturerIds);

    void deleteById(Long id);

    List<Product> searchByNameOrDescription(String text);

   // List<Product> findByStatus(ProductStatus status);

//    List<String> findAllStatuses();
}
