package com.turkcell.customerservice.web.controller;

import com.turkcell.customerservice.application.dto.CreateCustomerRequest;
import com.turkcell.customerservice.application.dto.CustomerResponse;
import com.turkcell.customerservice.application.services.CreateCustomerService;
import com.turkcell.customerservice.application.services.DeleteCustomerService;
import com.turkcell.customerservice.application.services.GetCustomerById;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CreateCustomerService createService;
    private final GetCustomerById getService;
    private final DeleteCustomerService deleteService;

    public CustomerController(CreateCustomerService createService, GetCustomerById getService, DeleteCustomerService deleteService) {
        this.createService = createService;
        this.getService = getService;
        this.deleteService = deleteService;
    }

    @PostMapping
    public CustomerResponse create(@RequestBody CreateCustomerRequest request){
        return createService.create(request);

    }

    @GetMapping("/{customerId}")
    public CustomerResponse getById(@PathVariable UUID customerId) {
        return getService.getById(customerId);
    }

    @DeleteMapping("/{customerId}")
    public void delete(@PathVariable UUID customerId) {
        deleteService.delete(customerId);
    }
}
