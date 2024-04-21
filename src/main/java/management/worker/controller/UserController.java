package management.worker.controller;


import management.worker.exception.ResourceNotFoundException;
import management.worker.model.User;
import management.worker.model.Worker;
import management.worker.repository.UserRepository;
import management.worker.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin (origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkerRepository workerRepository;


    @GetMapping("/users")
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public User saveUser(@RequestBody User user) {
        String email = user.getEmail();
        Worker worker = workerRepository.findByEmail(email);

        if (worker == null) {
            throw new ResourceNotFoundException("No se encontró ningún trabajador con el correo electrónico asociado: " + email);
        }

        worker.setUser(user); // Establecer la relación entre el usuario y el trabajador
        userRepository.save(user); // Guardar el usuario
        return user;
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<User> listWorkerById (@PathVariable Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El trabajador con ese ID no existe: " + id));
        return  ResponseEntity.ok(user);
    }

    @GetMapping("/users/by-email/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new ResourceNotFoundException("No se encontró ningún usuario con el correo electrónico: " + email);
        }

        return ResponseEntity.ok(user);
    }
    @GetMapping("/users/by-username/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new ResourceNotFoundException("No se encontró ningún usuario con el usuario: " + username);
        }

        return ResponseEntity.ok(user);
    }



    @PutMapping("/users/{id}")
    public  ResponseEntity<User> updateUser (@PathVariable Long id, @RequestBody User userRequest){
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("El trabajador con ese ID no existe: " + id);
                });
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setPassword(userRequest.getPassword());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setAdmin(userRequest.getAdmin());
        user.setImgPerfil(userRequest.getImgPerfil());

        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteWorker (@PathVariable Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("El trabajador con ese ID no existe: " + id));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();

        response.put("deleted", Boolean.TRUE);
        return  ResponseEntity.ok(response);

    }


}


