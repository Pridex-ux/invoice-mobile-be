package InvoiceMobile.com.service;

import InvoiceMobile.com.dto.SendReminderRequest;
import InvoiceMobile.com.model.Invoice;
import InvoiceMobile.com.model.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class NotificationService {

    @Autowired
    private InvoiceService invoiceService;

    public List<Map<String, Object>> getNotifications() {
        List<Invoice> invoices = invoiceService.getInvoices(null, null);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Invoice inv : invoices) {
            if (inv.getStatus() != PaymentStatus.LUNAS) {
                Map<String, Object> notif = new HashMap<>();
                notif.put("id", "notif-" + inv.getId());
                notif.put("invoiceId", inv.getId());
                notif.put("invoiceNumber", inv.getInvoiceNumber());
                notif.put("customerName", inv.getCustomer() != null ? inv.getCustomer().getName() : "Pelanggan");
                notif.put("amount", inv.getRemainingAmount());
                notif.put("status", inv.getStatus().name());

                if (inv.getStatus() == PaymentStatus.PENDING) {
                    notif.put("title", "Jatuh Tempo Pembayaran");
                    notif.put("message", "Faktur " + inv.getInvoiceNumber() + " senilai Rp " + (long)inv.getRemainingAmount() + " membutuhkan perhatian Anda.");
                } else {
                    notif.put("title", "Pengingat Cicilan Faktur");
                    notif.put("message", "Sisa cicilan " + inv.getInvoiceNumber() + " sebesar Rp " + (long)inv.getRemainingAmount() + " belum lunas.");
                }
                result.add(notif);
            }
        }
        return result;
    }

    public Map<String, Object> buildWhatsAppUrl(SendReminderRequest req) {
        String phone = req.getCustomerPhone() != null ? req.getCustomerPhone().replaceAll("[^0-9]", "") : "";
        if (phone.startsWith("0")) {
            phone = "62" + phone.substring(1);
        }

        String encodedText = URLEncoder.encode(req.getMessageText() != null ? req.getMessageText() : "Pengingat Faktur Smart Invoice", StandardCharsets.UTF_8);
        String waUrl = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + encodedText;

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("waUrl", waUrl);
        return response;
    }
}
