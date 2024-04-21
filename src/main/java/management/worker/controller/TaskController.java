package management.worker.controller;

import management.worker.TaskCreationRequest;
import management.worker.exception.ResourceNotFoundException;
import management.worker.model.Customer;
import management.worker.model.Task;
import management.worker.model.Worker;
import management.worker.repository.CustomerRepository;
import management.worker.repository.TaskRepository;
import management.worker.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/v1")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private WorkerRepository workerRepository;

    @GetMapping("/tasks")
    public List<Task> listTasks() {
        return taskRepository.findAll();
    }

    @PostMapping("/tasks")
    public Task saveTask(@RequestBody TaskCreationRequest request) {
        // Retrieve customer by ID
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.getCustomerId()));

        // Retrieve workers by IDs
        List<Worker> assignedWorkers = new ArrayList<>();
        for (Long workerId : request.getAssignedWorkerIds()) {
            Worker worker = workerRepository.findById(workerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Worker not found with id: " + workerId));
            assignedWorkers.add(worker);
        }

        // Create task object
        Task task = new Task();
        task.setCustomer(customer);
        task.setAssignedWorkers(assignedWorkers);
        task.setTaskStatus(request.getTaskStatus());
        task.setStartDate(request.getStartDate()); // Convertimos LocalDate a LocalDateTime
        task.setBudget(request.getBudget());
        task.setAddress(request.getAddress());
        // Set other task properties as needed

        return taskRepository.save(task);
    }




    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return ResponseEntity.ok(task);
    }

    @PutMapping("/tasks/{id}/assign-worker/{workerId}")
    public ResponseEntity<Task> assignWorkerToTask(@PathVariable Long id, @PathVariable Long workerId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new ResourceNotFoundException("Worker not found with id: " + workerId));

        // Add worker to task
        task.getAssignedWorkers().add(worker);

        Task updatedTask = taskRepository.save(task);
        return ResponseEntity.ok(updatedTask);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        // Update task properties
        task.setCustomer(taskRequest.getCustomer());
        task.setTaskStatus(taskRequest.getTaskStatus());
        task.setStartDate(taskRequest.getStartDate());
        task.setBudget(taskRequest.getBudget());
        task.setAddress(taskRequest.getAddress());
        task.setAssignedWorkers(taskRequest.getAssignedWorkers());

        Task updatedTask = taskRepository.save(task);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteTask(@PathVariable Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        taskRepository.delete(task);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}