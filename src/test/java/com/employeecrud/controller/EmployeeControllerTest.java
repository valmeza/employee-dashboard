package com.employeecrud.controller;

import com.employeecrud.model.Employee;
import com.employeecrud.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeControllerTest {

    private EmployeeService employeeService;
    private EmployeeController employeeController;

    @BeforeEach
    public void setup() {
        // we need to mock the employee service
        employeeService = Mockito.mock(EmployeeService.class);

        // pass in services injected in the employee controller
        employeeController = new EmployeeController(employeeService);
    }


    @Test
    public void createEmployee_shouldCreateEmployee() {
        // arrange
        Employee postBodyInput = new Employee(null, "Valeria", "Meza", "email@email.com");
        Employee expected = new Employee(1L, "Valeria", "Meza", "email@email.com");
        Mockito.when(employeeService.save(Mockito.any())).thenReturn(expected);

        // act
        Employee response = employeeController.saveEmployee(postBodyInput);

        // assert
        assertEquals(expected, response);
    }


    @Test
    public void getAllEmployees_shouldReturnEmptyListIfEmpty() {
        // arrange
        List<Employee> expected = new ArrayList<>();
        Mockito.when(employeeService.getEmployees()).thenReturn(expected);

        // act
        List<Employee> response = employeeController.getAllEmployees();

        // assert
        assertEquals(expected, response);
    }


    @Test
    public void getAllEmployees_shouldReturnAListOfEmployees() {
        // arrange
        List<Employee> expected = new ArrayList<>();
        expected.add(new Employee(1L, "Valeria", "Meza", "email@email.com"));
        expected.add(new Employee(1L, "Val", "Me", "email@email.com"));
        Mockito.when(employeeService.getEmployees()).thenReturn(expected);

        // act
        List<Employee> response = employeeController.getAllEmployees();

        // assert
        assertEquals(expected, response);
    }


    @Test
    public void updateEmployee_shouldCallServiceAndReturnUpdatedRecord() {
        // arrange
        Employee postBodyInput = new Employee(1L, "Valeria", "Meza", "Email");
        Mockito.when(employeeService.update(postBodyInput)).thenReturn(postBodyInput);

        // act
        Employee response = employeeController.updateEmployee(postBodyInput);

        // assert
        Mockito.verify(employeeService).update(postBodyInput);
        assertEquals(postBodyInput, response);
    }


    @Test
    public void getEmployeeById_shouldReturnEmployeeWithCorrespondingId() {
        // arrange
        Employee existing = new Employee(1L, "Valeria", "Meza", "email@email.com");
        Mockito.when(employeeService.getById(1L)).thenReturn(existing);

        // act
        Employee response = employeeController.getEmployeeById(1L);

        // assert
        assertEquals(existing, response);
    }


    @Test
    public void deleteEmployeeById_shouldDeleteEmployee() {
        // arrange
        long id = 1L;
        // act
        employeeController.deleteEmployeeById(id);
        // assert
        Mockito.verify(employeeService).delete(1L);
    }
}