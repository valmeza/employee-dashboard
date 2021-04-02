package com.employeecrud.controller;

import com.employeecrud.model.Employee;
import com.employeecrud.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;


@WebMvcTest(EmployeeController.class)
public class EmployeeControllerIT {

    MockMvc mockMvc;

    Employee employee;

    @SpyBean
    EmployeeController employeeController;

    @MockBean
    EmployeeService employeeService;

    @Captor
    private ArgumentCaptor<Employee> employeeArgumentCaptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        employee = new Employee(null, "Valeria", "Meza", "email@email.com");
    }

    @Test
    public void getAllEmployees_returnsStatus200() throws Exception {
        mockMvc.perform(get("/api/employees")).andExpect(status().isOk());
    }

    @Test
    public void getAllEmployees_callsEmployeeService() throws Exception {
        mockMvc.perform(get("/api/employees")).andExpect(status().isOk());
        Mockito.verify(employeeService).getEmployees();
    }

    @Test
    public void getEmployeeById_returnsStatus200() throws  Exception {
        mockMvc.perform(get("/api/employees/1")).andExpect(status().isOk());
    }

    @Test
    public void getEmployeeById_callsEmployeeService() throws Exception {
        mockMvc.perform(get("/api/employees/1")).andExpect(status().isOk());
        Mockito.verify(employeeService).getById(1L);
    }

    @Test
    public void saveEmployee_returns201Status() throws Exception {
        String testBody = new ObjectMapper().writeValueAsString(employee);
        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testBody)
        ).andExpect(status().isCreated());

        Mockito.verify(employeeService).save(employeeArgumentCaptor.capture());
        assertEquals(employee.getId(), employeeArgumentCaptor.getValue().getId());
    }

    @Test
    public void updateEmployee_returns200Status() throws Exception {
        employee.setId(1L);
        String testBody = new ObjectMapper().writeValueAsString(employee);
        mockMvc.perform(put("/api/employees/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testBody)
        ).andExpect(status().isOk());

        Mockito.verify(employeeService).update(employeeArgumentCaptor.capture());
        assertEquals(employee.getId(), employeeArgumentCaptor.getValue().getId());
    }

    @Test
    public void deleteEmployeeById_Returns204Status() throws Exception {
        mockMvc.perform(delete("/api/employees/1")).andExpect(status().isNoContent());
        Mockito.verify(employeeService).delete(1L);
    }
}
