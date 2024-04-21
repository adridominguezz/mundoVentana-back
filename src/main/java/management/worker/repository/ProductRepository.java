package management.worker.repository;

import management.worker.model.Customer;
import management.worker.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository <Product, Long> {
    List<Product> findByType (String type);
}
