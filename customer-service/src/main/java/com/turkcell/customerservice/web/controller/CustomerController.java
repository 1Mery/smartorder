package com.turkcell.customerservice.web.controller;

import com.turkcell.customerservice.application.dto.CreateCustomerRequest;
import com.turkcell.customerservice.application.dto.CustomerResponse;
import com.turkcell.customerservice.application.services.CreateCustomerService;
import com.turkcell.customerservice.application.services.DeleteCustomerService;
import com.turkcell.customerservice.application.services.GetCustomerById;
import com.turkcell.customerservice.application.services.VerifyCustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CreateCustomerService createService;
    private final GetCustomerById getService;
    private final DeleteCustomerService deleteService;
    private final VerifyCustomerService verifyCustomerService;

    public CustomerController(CreateCustomerService createService, GetCustomerById getService, DeleteCustomerService deleteService, VerifyCustomerService verifyCustomerService) {
        this.createService = createService;
        this.getService = getService;
        this.deleteService = deleteService;
        this.verifyCustomerService = verifyCustomerService;
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

    // Order service'in çağırdığı endpoint
    @GetMapping("/{customerId}/verify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verify(@PathVariable UUID customerId) {
        verifyCustomerService.verify(customerId);
    }
}
