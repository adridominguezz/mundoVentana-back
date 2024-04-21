package management.worker.controller;


import management.worker.exception.ResourceNotFoundException;
import management.worker.model.Customer;
import management.worker.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin (origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/v1")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;


    @GetMapping("/customers")
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping("/customers")
    public Customer saveCustomer(@RequestBody Customer customer) {return customerRepository.save(customer);
    }


    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> listWorkerById (@PathVariable Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El trabajador con ese ID no existe: " + id));
        return  ResponseEntity.ok(customer);
    }


    @PutMapping("/customers/{id}")
    public  ResponseEntity<Customer> updateCustomer (@PathVariable Long id, @RequestBody Customer customerRequest){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("El trabajador con ese ID no existe: " + id);
                });
        customer.setName(customerRequest.getName());
        customer.setSurname(customerRequest.getSurname());
        customer.setDni(customerRequest.getDni());
        customer.setAddress(customerRequest.getAddress());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhone(customerRequest.getPhone());

        Customer updatedCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(updatedCustomer);

    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Map<String,Boolean>> deletCustomer (@PathVariable Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("El trabajador con ese ID no existe: " + id));

        customerRepository.delete(customer);
        Map<String, Boolean> response = new HashMap<>();

        response.put("deleted", Boolean.TRUE);
        return  ResponseEntity.ok(response);

    }


}