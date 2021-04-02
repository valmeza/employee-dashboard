package com.employeecrud.service;

import com.employeecrud.exception.EmployeeNotFoundException;
import com.employeecrud.model.Employee;
import com.employeecrud.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getById(long id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if(employee.isEmpty()) {
            throw new EmployeeNotFoundException("There is no employee with that id.");
        }

        return employee.get();
    }

    public void delete(long id) {
        employeeRepository.deleteById(id);
    }
}
