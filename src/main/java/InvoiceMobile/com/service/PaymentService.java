package InvoiceMobile.com.service;

import InvoiceMobile.com.dto.RecordPaymentRequest;
import InvoiceMobile.com.model.Invoice;
import InvoiceMobile.com.model.PaymentRecord;
import InvoiceMobile.com.model.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class PaymentService {

    @Autowired
    private InvoiceService invoiceService;

    public Map<String, Object> recordPayment(String invoiceId, RecordPaymentRequest req) {
        Optional<Invoice> invOpt = invoiceService.getInvoiceById(invoiceId);
        Invoice inv;
        if (invOpt.isEmpty()) {
            // Auto-register invoice if created locally before backend restart
            inv = new Invoice();
            inv.setId(invoiceId);
            inv.setInvoiceNumber("INV-" + (invoiceId.length() > 8 ? invoiceId.substring(invoiceId.length() - 6) : invoiceId));
            inv.setCustomer(new InvoiceMobile.com.model.Customer("cust-auto", "Pelanggan Mobile", "081234567890", "", ""));
            inv.setTotalAmount(req.getAmountPaid() * 2);
            inv.setTotalPaid(0);
            inv.setRemainingAmount(req.getAmountPaid() * 2);
            inv.setStatus(PaymentStatus.PENDING);
            inv.setIssueDate(Instant.now().toString());
            inv.setDueDate(Instant.now().toString());
            invoiceService.getInvoices(null, null).add(inv);
        } else {
            inv = invOpt.get();
        }
        double amountPaid = req.getAmountPaid();
        String method = req.getMethod() != null ? req.getMethod() : "Transfer Bank";
        String date = req.getPaymentDate() != null ? req.getPaymentDate() : Instant.now().toString();

        PaymentRecord record = new PaymentRecord(
                "pay-" + System.currentTimeMillis(),
                invoiceId,
                amountPaid,
                date,
                method
        );

        inv.getPaymentHistory().add(record);

        double newTotalPaid = inv.getTotalPaid() + amountPaid;
        inv.setTotalPaid(newTotalPaid);

        double newRemaining = inv.getTotalAmount() - newTotalPaid;
        inv.setRemainingAmount(Math.max(0, newRemaining));

        if (newRemaining <= 0) {
            inv.setStatus(PaymentStatus.LUNAS);
        } else {
            inv.setStatus(PaymentStatus.CICILAN);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("payment", record);
        result.put("updatedInvoice", inv);
        return result;
    }

    public List<PaymentRecord> getPaymentHistory(String invoiceId) {
        Optional<Invoice> invOpt = invoiceService.getInvoiceById(invoiceId);
        return invOpt.map(Invoice::getPaymentHistory).orElse(Collections.emptyList());
    }
}
