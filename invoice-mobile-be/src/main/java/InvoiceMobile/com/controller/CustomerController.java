package InvoiceMobile.com.controller;

import InvoiceMobile.com.dto.ApiResponse;
import InvoiceMobile.com.model.Customer;
import InvoiceMobile.com.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * GET /api/customers
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Customer>>> getCustomers(@RequestParam(required = false) String search) {
        List<Customer> customers = customerService.getAllCustomers(search);
        return ResponseEntity.ok(ApiResponse.success(customers));
    }

    /**
     * GET /api/customers/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> getCustomerById(@PathVariable String id) {
        Optional<Customer> cust = customerService.getCustomerById(id);
        return cust.map(customer -> ResponseEntity.ok(ApiResponse.success(customer)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Pelanggan tidak ditemukan.")));
    }

    /**
     * POST /api/customers
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> createCustomer(@RequestBody Customer customer) {
        Customer created = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Pelanggan berhasil ditambahkan", created));
    }

    /**
     * PUT /api/customers/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> updateCustomer(
            @PathVariable String id,
            @RequestBody Customer updatedData) {
        Customer updated = customerService.updateCustomer(id, updatedData);
        if (updated != null) {
            return ResponseEntity.ok(ApiResponse.success("Data pelanggan berhasil diperbarui", updated));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Pelanggan tidak ditemukan."));
    }

    /**
     * DELETE /api/customers/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable String id) {
        boolean deleted = customerService.deleteCustomer(id);
        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success("Pelanggan berhasil dihapus", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Pelanggan tidak ditemukan."));
    }
}
