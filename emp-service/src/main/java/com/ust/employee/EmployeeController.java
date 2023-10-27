package com.ust.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/emp")
public class EmployeeController {
    private String deptBasePath = "http://DEPT-SERVICE/api/dept";
    private String fetchAll = "/all";

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);
    private static List<EmployeeBean> employees;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ReactiveCircuitBreakerFactory circuitBreakerFactory;


    public EmployeeController() {
        employees = new ArrayList<>(5);
        employees.add(new EmployeeBean("1", "Vijay", "vijay@gmail.com", 1L));
        employees.add(new EmployeeBean("2", "Samantha", "Samantha@gmail.com", 2L));
        employees.add(new EmployeeBean("3", "Praveen", "Praveen@gmail.com", 3L));
        employees.add(new EmployeeBean("4", "Kavya", "Kavya@gmail.com", 4L));
        employees.add(new EmployeeBean("5", "Samarth", "Samarth@gmail.com", 5L));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmployeeBean> get(@PathVariable String id) {
        log.debug("Request received to fetch the employee and its department by employee id {}", id);
        EmployeeBean result = employees.stream().filter(dept -> dept.getId().equals(id)).findAny().orElse(null);
        if (ObjectUtils.isEmpty(result)) {
            log.debug("Employee is NOT present for emp id {}", id);
            return ResponseEntity.notFound().build();
        } else {
            ReactiveCircuitBreaker rcb = circuitBreakerFactory.create("dept-cb-getOne");
            Mono<DepartmentBean> department = rcb.run(
                    webClientBuilder
                            .build().
                            get()
                            .uri(String.format("%s/%s", deptBasePath, result.getDeptId()))
                            .retrieve()
                            .bodyToMono(DepartmentBean.class),
                    throwable -> getDefault());
            result.setDepartment(department.block());
            return ResponseEntity.ok().body(result);
        }
    }

    private Mono<DepartmentBean> getDefault() {
        log.debug("Circuit breaker fallback method called");
        return Mono.just(new DepartmentBean(0, "Default", "Default001"));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<EmployeeBean>> getAll() {
        log.debug("Request received to fetch all employees and its departments");
        ReactiveCircuitBreaker rcb = circuitBreakerFactory.create("dept-cb-getAll");
        return ResponseEntity.ok().body(employees.stream().map(e -> {
            Mono<DepartmentBean> department = rcb.run(webClientBuilder
                            .build()
                            .get()
                            .uri(String.format("%s/%s", deptBasePath, e.getDeptId()))
                            .retrieve()
                            .bodyToMono(DepartmentBean.class),
                    throwable -> getDefault());
            if (!ObjectUtils.isEmpty(department)) {
                e.setDepartment(department.block());
            }
            return e;
        }).collect(Collectors.toList()));
    }

}
