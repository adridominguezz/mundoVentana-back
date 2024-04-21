package management.worker;

import java.util.Date;
import java.util.List;

public class TaskCreationRequest {
    private Long customerId;
    private List<Long> assignedWorkerIds;
    private String taskStatus;
    private Date startDate;
    private Double budget;
    private String address;
    // Getters y Setters

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getAssignedWorkerIds() {
        return assignedWorkerIds;
    }

    public void setAssignedWorkerIds(List<Long> assignedWorkerIds) {
        this.assignedWorkerIds = assignedWorkerIds;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
