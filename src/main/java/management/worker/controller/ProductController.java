package management.worker.controller;


import management.worker.exception.ResourceNotFoundException;
import management.worker.model.Product;
import management.worker.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin (origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/v1")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/products")
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/products")
    public Product saveProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<Product> listProductById (@PathVariable Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El trabajador con ese ID no existe: " + id));
        return  ResponseEntity.ok(product);
    }


    @PutMapping("/products/{id}")
    public  ResponseEntity<Product> updateProduct (@PathVariable Long id, @RequestBody Product productRequest){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("El producto con ese ID no existe: " + id);
                });
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setType(productRequest.getType());
        product.setStock(productRequest.getStock());
        product.setImg(productRequest.getImg());

        Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);

    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Map<String,Boolean>> deletProduct (@PathVariable Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("El producto con ese ID no existe: " + id));

        productRepository.delete(product);
        Map<String, Boolean> response = new HashMap<>();

        response.put("deleted", Boolean.TRUE);
        return  ResponseEntity.ok(response);

    }

    @GetMapping("/products/by-type/{type}")
    public List<Product> listProductByType(@PathVariable String type) {
        return productRepository.findByType(type);
    }

}