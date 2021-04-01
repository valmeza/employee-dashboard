package com.employeecrud.controller;

import com.employeecrud.model.Employee;
import com.employeecrud.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;

public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public List<Employee> getAllEmployees() {
        return employeeService.getEmployees();
    }


}
