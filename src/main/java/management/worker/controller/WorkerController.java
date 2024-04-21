package management.worker.controller;

import management.worker.exception.ResourceNotFoundException;
import management.worker.model.Worker;
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
public class WorkerController {
    @Autowired
    private WorkerRepository workerRepository;

    @GetMapping("/workers")
    public List<Worker> listWorkers() {
        return workerRepository.findAll();
    }

    @PostMapping("/workers")
    public Worker saveWorker (@RequestBody Worker worker){
        return workerRepository.save(worker);
    }

    @GetMapping("/workers/{id}")
    public ResponseEntity<Worker> listWorkerById (@PathVariable Long id){
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El trabajador con ese ID no existe: " + id));
        return  ResponseEntity.ok(worker);
    }

    @PutMapping("/workers/{id}")
    public  ResponseEntity<Worker> updateWorker (@PathVariable Long id, @RequestBody Worker workerRequest){
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("El trabajador con ese ID no existe: " + id);
                });
        worker.setName(workerRequest.getName());
        worker.setFirstSurname(workerRequest.getFirstSurname());
        worker.setSecondSurname(workerRequest.getSecondSurname());
        worker.setDni(workerRequest.getDni());
        worker.setEmail(workerRequest.getEmail());
        worker.setUser(workerRequest.getUser());

        Worker updatedWorker = workerRepository.save(worker);
        return ResponseEntity.ok(updatedWorker);

    }

    @DeleteMapping("/workers/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteWorker (@PathVariable Long id){
        Worker worker = workerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("El trabajador con ese ID no existe: " + id));

        workerRepository.delete(worker);
        Map<String, Boolean> response = new HashMap<>();

        response.put("deleted", Boolean.TRUE);
        return  ResponseEntity.ok(response);

    }


}


