package com.employeecrud.controller;

import com.employeecrud.model.Employee;
import com.employeecrud.service.EmployeeService;

import java.util.List;

public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public List<Employee> getAllEmployees() {
        return employeeService.getEmployees();
    }

    public Employee getEmployeeById(long id) {
        return employeeService.getById(id);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeService.save(employee);
    }

    public Employee updateEmployee(Employee employee, long id) {
        return employeeService.update(employee, id);
    }

    public void deleteEmployeeById(long id) {
        employeeService.delete(id);
    }
}
