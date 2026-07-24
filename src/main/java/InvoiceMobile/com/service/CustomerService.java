package InvoiceMobile.com.service;

import InvoiceMobile.com.model.Customer;
import InvoiceMobile.com.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerService() {
        // Empty constructor
    }

    public List<Customer> getAllCustomers(String search) {
        List<Customer> all = customerRepository.findAll();
        if (search == null || search.trim().isEmpty()) {
            return all;
        }
        String q = search.toLowerCase().trim();
        return all.stream()
                .filter(c -> (c.getName() != null && c.getName().toLowerCase().contains(q)) ||
                             (c.getPhone() != null && c.getPhone().contains(q)) ||
                             (c.getEmail() != null && c.getEmail().toLowerCase().contains(q)))
                .collect(Collectors.toList());
    }

    public Optional<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        if (customer.getId() == null || customer.getId().trim().isEmpty()) {
            customer.setId("cust-" + System.currentTimeMillis());
        }
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(String id, Customer updatedData) {
        return customerRepository.findById(id).map(existing -> {
            if (updatedData.getName() != null) existing.setName(updatedData.getName());
            if (updatedData.getPhone() != null) existing.setPhone(updatedData.getPhone());
            if (updatedData.getEmail() != null) existing.setEmail(updatedData.getEmail());
            if (updatedData.getAddress() != null) existing.setAddress(updatedData.getAddress());
            return customerRepository.save(existing);
        }).orElse(null);
    }

    public boolean deleteCustomer(String id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
