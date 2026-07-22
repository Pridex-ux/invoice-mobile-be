package InvoiceMobile.com.controller;

import InvoiceMobile.com.dto.ApiResponse;
import InvoiceMobile.com.dto.RecordPaymentRequest;
import InvoiceMobile.com.model.PaymentRecord;
import InvoiceMobile.com.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/invoices")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * POST /api/invoices/{id}/payments
     * Catat pembayaran angsuran / pelunasan
     */
    @PostMapping("/{id}/payments")
    public ResponseEntity<ApiResponse<Map<String, Object>>> recordPayment(
            @PathVariable("id") String invoiceId,
            @RequestBody RecordPaymentRequest request) {
        try {
            Map<String, Object> data = paymentService.recordPayment(invoiceId, request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Pembayaran berhasil dicatat", data));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * GET /api/invoices/{id}/payments
     * Ambil histori cicilan faktur
     */
    @GetMapping("/{id}/payments")
    public ResponseEntity<ApiResponse<List<PaymentRecord>>> getPaymentHistory(@PathVariable("id") String invoiceId) {
        List<PaymentRecord> history = paymentService.getPaymentHistory(invoiceId);
        return ResponseEntity.ok(ApiResponse.success(history));
    }
}
