package com.ust.department;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/dept")
public class DepartmentController {
    private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);
    private static List<DepartmentBean> departments;

    public DepartmentController() {
        departments = new ArrayList<>(5);
        departments.add(new DepartmentBean(1, "IT", "BCT001"));
        departments.add(new DepartmentBean(2, "SALES", "BCT002"));
        departments.add(new DepartmentBean(3, "MARKETING", "BCT003"));
        departments.add(new DepartmentBean(4, "HR", "BCT004"));
        departments.add(new DepartmentBean(5, "TMG", "BCT005"));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DepartmentBean> get(@PathVariable("id") Long id) {
        log.debug("Request received to fetch the department by id {}", id);
        DepartmentBean result = departments.stream().filter(dept -> dept.getDeptId() == id).findAny().orElse(null);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<DepartmentBean>> getAll() {
        log.debug("Request received to fetch all departments");
        return ResponseEntity.ok().body(departments);
    }

}
