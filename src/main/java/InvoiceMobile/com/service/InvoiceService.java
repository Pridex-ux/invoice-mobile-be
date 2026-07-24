package InvoiceMobile.com.service;

import InvoiceMobile.com.dto.CreateInvoiceRequest;
import InvoiceMobile.com.model.*;
import InvoiceMobile.com.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerService customerService;

    public InvoiceService() {
        // Empty constructor
    }

    public List<Invoice> getInvoices(PaymentStatus status, String search) {
        List<Invoice> all = invoiceRepository.findAll();
        return all.stream()
                .filter(inv -> {
                    if (status != null && inv.getStatus() != status) return false;
                    if (search != null && !search.trim().isEmpty()) {
                        String q = search.toLowerCase().trim();
                        boolean matchNo = inv.getInvoiceNumber() != null && inv.getInvoiceNumber().toLowerCase().contains(q);
                        boolean matchCust = inv.getCustomer() != null && inv.getCustomer().getName() != null && inv.getCustomer().getName().toLowerCase().contains(q);
                        return matchNo || matchCust;
                    }
                    return true;
                })
                .sorted(Comparator.comparing(Invoice::getIssueDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    public Optional<Invoice> getInvoiceById(String id) {
        return invoiceRepository.findById(id);
    }

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Invoice createInvoice(CreateInvoiceRequest req) {
        long count = invoiceRepository.count() + 1;
        String id = (req.getId() != null && !req.getId().trim().isEmpty()) ? req.getId() : "inv-" + System.currentTimeMillis();
        String invNo = (req.getInvoiceNumber() != null && !req.getInvoiceNumber().trim().isEmpty()) ? req.getInvoiceNumber() : String.format("INV-2026-%03d", count);

        Invoice inv = new Invoice();
        inv.setId(id);
        inv.setInvoiceNumber(invNo);

        // Customer binding / creation
        Customer cust = req.getCustomer();
        if (cust != null) {
            cust = customerService.createCustomer(cust);
            inv.setCustomer(cust);
        }

        inv.setItems(req.getItems() != null ? req.getItems() : new ArrayList<>());
        inv.setSubtotal(req.getSubtotal());
        inv.setDiscountPercentage(req.getDiscountPercentage());
        inv.setDiscountAmount(req.getDiscountAmount());
        inv.setTaxPercentage(req.getTaxPercentage());
        inv.setTaxAmount(req.getTaxAmount());
        inv.setTotalAmount(req.getTotalAmount());

        double dp = req.getInitialDpPaid();
        inv.setTotalPaid(dp);
        double remaining = req.getTotalAmount() - dp;
        inv.setRemainingAmount(Math.max(0, remaining));

        PaymentStatus status = req.getStatus();
        if (status == null) {
            if (remaining <= 0) {
                status = PaymentStatus.LUNAS;
            } else if (dp > 0) {
                status = PaymentStatus.CICILAN;
            } else {
                status = PaymentStatus.PENDING;
            }
        }
        inv.setStatus(status);

        String now = Instant.now().toString();
        inv.setIssueDate(now);
        inv.setDueDate(req.getDueDate() != null ? req.getDueDate() : now);

        if (dp > 0) {
            PaymentRecord dpRecord = new PaymentRecord(
                    "pay-" + System.currentTimeMillis(),
                    id,
                    dp,
                    now,
                    "Pembayaran DP Awal"
            );
            inv.getPaymentHistory().add(dpRecord);
        }

        return invoiceRepository.save(inv);
    }

    public Invoice updateInvoice(String id, Invoice updateData) {
        return invoiceRepository.findById(id).map(existing -> {
            if (updateData.getStatus() != null) existing.setStatus(updateData.getStatus());
            if (updateData.getDueDate() != null) existing.setDueDate(updateData.getDueDate());
            if (updateData.getTotalAmount() > 0) existing.setTotalAmount(updateData.getTotalAmount());
            return invoiceRepository.save(existing);
        }).orElse(null);
    }

    public boolean deleteInvoice(String id) {
        if (invoiceRepository.existsById(id)) {
            invoiceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
