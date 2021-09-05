package mk.finki.ukim.mk.lab.repository;

import mk.finki.ukim.mk.lab.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByIdIn(List<Long> ids);

    List<Category> findAllByNameLike(String text);

    void deleteByName(String name);
}
