package mk.finki.ukim.mk.lab.repository;


import mk.finki.ukim.mk.lab.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


 //   List<Product> findAllByStatus(ProductStatus productStatus);

    Optional<Product> findByName(String name);

    void deleteByName(String name);

    @Query("select p from Product p where p.name like :text or p.description like :text or p.manufacturer.name like :text")
    List<Product> findAllByNameLikeOrDescriptionLikeOrCategoryLikeOrManufacturerLike(String text);
}
