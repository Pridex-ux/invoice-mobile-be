package InvoiceMobile.com.service;

import InvoiceMobile.com.model.Customer;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final Map<String, Customer> customerRepository = new ConcurrentHashMap<>();

    public CustomerService() {
        // Empty constructor - no mock data
    }

    public List<Customer> getAllCustomers(String search) {
        if (search == null || search.trim().isEmpty()) {
            return new ArrayList<>(customerRepository.values());
        }
        String q = search.toLowerCase().trim();
        return customerRepository.values().stream()
                .filter(c -> (c.getName() != null && c.getName().toLowerCase().contains(q)) ||
                             (c.getPhone() != null && c.getPhone().contains(q)) ||
                             (c.getEmail() != null && c.getEmail().toLowerCase().contains(q)))
                .collect(Collectors.toList());
    }

    public Optional<Customer> getCustomerById(String id) {
        return Optional.ofNullable(customerRepository.get(id));
    }

    public Customer createCustomer(Customer customer) {
        if (customer.getId() == null || customer.getId().trim().isEmpty()) {
            customer.setId("cust-" + System.currentTimeMillis());
        }
        customerRepository.put(customer.getId(), customer);
        return customer;
    }

    public Customer updateCustomer(String id, Customer updatedData) {
        Customer existing = customerRepository.get(id);
        if (existing != null) {
            if (updatedData.getName() != null) existing.setName(updatedData.getName());
            if (updatedData.getPhone() != null) existing.setPhone(updatedData.getPhone());
            if (updatedData.getEmail() != null) existing.setEmail(updatedData.getEmail());
            if (updatedData.getAddress() != null) existing.setAddress(updatedData.getAddress());
            return existing;
        }
        return null;
    }

    public boolean deleteCustomer(String id) {
        return customerRepository.remove(id) != null;
    }
}
