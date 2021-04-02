package com.employeecrud.service;

import com.employeecrud.exception.EmployeeNotFoundException;
import com.employeecrud.model.Employee;
import com.employeecrud.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    public void getEmployees_callsRepositoryAndReturnsList() {
        // arrange
        List<Employee> expected = Arrays.asList(
                new Employee(null, "Val", "Mez", "email"),
                new Employee(null, "Gina", "Meza", "email"),
                new Employee(null, "Leo", "Meza", "email"));
        Mockito.when(employeeRepository.findAll()).thenReturn(expected);

        // act
        List<Employee> response = employeeService.getEmployees();

        // assert
        Mockito.verify(employeeRepository).findAll();
        assertEquals(expected, response);
    }

    @Test
    public void save_savesEmployeeAndReturnsUpdatedEmployeeWithId() {
        // arrange
        Employee input = new Employee(null, "Val", "Meza", "email");
        Employee expected = new Employee(1L, "Val", "Meza", "email");
        Mockito.when(employeeRepository.save(input)).thenReturn(expected);

        // act
        Employee response = employeeService.save(input);

        // assert
        Mockito.verify(employeeRepository).save(input);
        assertEquals(expected, response);
    }

    @Test
    public void update_saveEmployeeAndReturnUpdatedEmployee() {
        // arrange
        Employee input = new Employee(1L, "Val", "Meza", "email");
        Employee expected = new Employee(1L, "Val", "Meza", "email");
        Mockito.when(employeeRepository.save(input)).thenReturn(expected);

        // act
        Employee response = employeeService.update(input);

        // assert
        Mockito.verify(employeeRepository).save(input);
        assertEquals(expected, response);
    }

    @Test
    public void getById_shouldReturnEmployeeWithThatId() {
        // arrange
        Employee expected = new Employee(1L, "Val", "Meza", "email");
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(expected));

        // act
        Employee response = employeeService.getById(1L);

        // assert
        Mockito.verify(employeeRepository).findById(1L);
        assertEquals(expected, response);
    }

    @Test
    public void getById_shouldThrowExceptionIfIdDoesNotExist() {
        // arrange
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // act
        EmployeeNotFoundException employeeNotFoundException = assertThrows(EmployeeNotFoundException.class, () -> {
           employeeService.getById(1L);
        });

        // assert
        assertEquals("There is no employee with that id.", employeeNotFoundException.getMessage());
    }

    @Test
    public void delete_callRepositoryAndDeleteEmployee() {
        // arrange
        long id = 1L;

        // act
        employeeService.delete(id);

        // assert
        Mockito.verify(employeeRepository).deleteById(id);
    }

}