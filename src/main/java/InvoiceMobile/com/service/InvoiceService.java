package InvoiceMobile.com.service;

import InvoiceMobile.com.dto.CreateInvoiceRequest;
import InvoiceMobile.com.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final Map<String, Invoice> invoiceRepository = new ConcurrentHashMap<>();
    private final AtomicLong invoiceCounter = new AtomicLong(3);

    @Autowired
    private CustomerService customerService;

    public InvoiceService() {
        // Seed Initial Invoices (inv-101, inv-102, inv-103)
        initSeedData();
    }

    private void initSeedData() {
        // Empty - no mock data
    }

    public List<Invoice> getInvoices(PaymentStatus status, String search) {
        return invoiceRepository.values().stream()
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
        return Optional.ofNullable(invoiceRepository.get(id));
    }

    public Invoice createInvoice(CreateInvoiceRequest req) {
        long count = invoiceCounter.incrementAndGet();
        String id = (req.getId() != null && !req.getId().trim().isEmpty()) ? req.getId() : "inv-" + System.currentTimeMillis();
        String invNo = (req.getInvoiceNumber() != null && !req.getInvoiceNumber().trim().isEmpty()) ? req.getInvoiceNumber() : String.format("INV-2026-%03d", count);

        Invoice inv = new Invoice();
        inv.setId(id);
        inv.setInvoiceNumber(invNo);

        // Customer binding / creation
        Customer cust = req.getCustomer();
        if (cust != null) {
            if (cust.getId() == null || cust.getId().isEmpty()) {
                cust = customerService.createCustomer(cust);
            }
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

        invoiceRepository.put(id, inv);
        return inv;
    }

    public Invoice updateInvoice(String id, Invoice updateData) {
        Invoice existing = invoiceRepository.get(id);
        if (existing != null) {
            if (updateData.getStatus() != null) existing.setStatus(updateData.getStatus());
            if (updateData.getDueDate() != null) existing.setDueDate(updateData.getDueDate());
            if (updateData.getTotalAmount() > 0) existing.setTotalAmount(updateData.getTotalAmount());
            return existing;
        }
        return null;
    }

    public boolean deleteInvoice(String id) {
        return invoiceRepository.remove(id) != null;
    }
}
