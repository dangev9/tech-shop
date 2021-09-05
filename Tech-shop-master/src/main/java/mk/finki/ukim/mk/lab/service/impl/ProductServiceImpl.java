package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.model.Product;
import mk.finki.ukim.mk.lab.model.exception.ManufacturerNotFoundException;
import mk.finki.ukim.mk.lab.model.exception.ProductNotFoundException;
import mk.finki.ukim.mk.lab.repository.CategoryRepository;
import mk.finki.ukim.mk.lab.repository.ManufacturerRepository;
import mk.finki.ukim.mk.lab.repository.ProductRepository;
import mk.finki.ukim.mk.lab.service.ManufacturerService;
import mk.finki.ukim.mk.lab.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerService manufacturerService;


    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,  ManufacturerRepository manufacturerRepository, ManufacturerService manufacturerService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;

        this.manufacturerService = manufacturerService;
    }

    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Product save(String name, String description, Double price, Integer quantity,
                        List<Long> categoryIds, Long manufacturerId) {
        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));
        List<Category> categories = this.categoryRepository.findAllByIdIn(categoryIds);
        this.productRepository.deleteByName(name);
        Product product = new Product(name, description, price, quantity, categories, manufacturer);

        return this.productRepository.save(product);
    }

    @Override
    public Optional<Product> edit(Long id, String name, String description, Double price, Integer quantity, List<Long> categoryIds, Long manufacturerId) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        List<Category> categories = this.categoryRepository.findAllByIdIn(categoryIds);
        product.setCategories(categories);
        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));
        product.setManufacturer(manufacturer);

        return Optional.of(this.productRepository.save(product));

    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public List<Product> searchByNameOrDescription(String text) {
        if (text != null) {
            text = String.format("%%%s%%", text);
        }
        return this.productRepository.findAllByNameLikeOrDescriptionLikeOrCategoryLikeOrManufacturerLike(text);
    }
}
