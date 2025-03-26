package com.project.sdp.Controller;

import com.project.sdp.Dto.EmployeeDTO;
import com.project.sdp.Service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/save")
    public ResponseEntity<String> saveEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        try {
            String responseMessage = employeeService.addEmployee(employeeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save employee: " + e.getMessage());
        }
    }
}
