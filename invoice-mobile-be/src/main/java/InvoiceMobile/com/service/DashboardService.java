package InvoiceMobile.com.service;

import InvoiceMobile.com.dto.DashboardStatsResponse;
import InvoiceMobile.com.model.Invoice;
import InvoiceMobile.com.model.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private InvoiceService invoiceService;

    public DashboardStatsResponse getDashboardStats() {
        List<Invoice> invoices = invoiceService.getInvoices(null, null);

        double totalPending = 0;
        double totalCicilan = 0;
        double totalLunas = 0;
        int overdueCount = 0;
        int lunasCount = 0;

        String nowStr = Instant.now().toString();

        for (Invoice inv : invoices) {
            if (inv.getStatus() == PaymentStatus.PENDING) {
                totalPending += inv.getRemainingAmount();
            } else if (inv.getStatus() == PaymentStatus.CICILAN) {
                totalCicilan += inv.getRemainingAmount();
            } else if (inv.getStatus() == PaymentStatus.LUNAS) {
                totalLunas += inv.getTotalAmount();
                lunasCount++;
            }

            if (inv.getStatus() != PaymentStatus.LUNAS && inv.getDueDate() != null && inv.getDueDate().compareTo(nowStr) < 0) {
                overdueCount++;
            }
        }

        int totalInvoices = invoices.size();
        int lunasRate = totalInvoices > 0 ? (int) Math.round((double) lunasCount / totalInvoices * 100) : 0;

        return new DashboardStatsResponse(totalPending, totalCicilan, totalLunas, overdueCount, lunasRate);
    }
}
