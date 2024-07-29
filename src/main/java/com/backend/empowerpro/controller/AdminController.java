package com.backend.empowerpro.controller;

import com.backend.empowerpro.dto.employee.EmployeeCreationDto;
import com.backend.empowerpro.entity.Employee;
import com.backend.empowerpro.service.impl.EmployeeServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final EmployeeServiceImp employeeServiceImp;
    @PostMapping("/creation")
    public ResponseEntity<Employee> creation(@RequestBody EmployeeCreationDto employeeCreationDto) {
        return ResponseEntity.ok(employeeServiceImp.createEmployee(employeeCreationDto));
    }

    @PostMapping("/complaint-creation")
    public ResponseEntity<String> creation(@RequestBody ComplaintDto complaintDto) {
        return ResponseEntity.ok(complaintService.createComplaint(complaintDto));
    }
    @GetMapping("/complaint-ToMe")
    public ResponseEntity<List<ComplaintDto>> getComplaintsToMe() {
        return ResponseEntity.ok(complaintService.getComplaintsToMe());
    }

    @GetMapping("/complaint-FromMyself")
    public ResponseEntity<List<VacancyDto>> getComplaintsFromMyself() {
        return ResponseEntity.ok(vacancyService.getComplaintsFromMyself());
    }

    //Delete Employees
}
