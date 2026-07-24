package InvoiceMobile.com.controller;

import InvoiceMobile.com.dto.ApiResponse;
import InvoiceMobile.com.dto.CreateInvoiceRequest;
import InvoiceMobile.com.model.Invoice;
import InvoiceMobile.com.model.PaymentStatus;
import InvoiceMobile.com.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    /**
     * GET /api/invoices
     * Query: status (optional: PENDING, CICILAN, LUNAS), search (optional)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Invoice>>> getInvoices(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search) {

        PaymentStatus pStatus = null;
        if (status != null && !status.equalsIgnoreCase("ALL")) {
            try {
                pStatus = PaymentStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Ignore invalid status enum
            }
        }

        List<Invoice> list = invoiceService.getInvoices(pStatus, search);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    /**
     * GET /api/invoices/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Invoice>> getInvoiceById(@PathVariable String id) {
        Optional<Invoice> inv = invoiceService.getInvoiceById(id);
        return inv.map(invoice -> ResponseEntity.ok(ApiResponse.success(invoice)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Faktur dengan ID " + id + " tidak ditemukan.")));
    }

    /**
     * POST /api/invoices
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Invoice>> createInvoice(@RequestBody CreateInvoiceRequest request) {
        Invoice created = invoiceService.createInvoice(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Faktur berhasil diterbitkan", created));
    }

    /**
     * PUT /api/invoices/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Invoice>> updateInvoice(
            @PathVariable String id,
            @RequestBody Invoice updateData) {
        Invoice updated = invoiceService.updateInvoice(id, updateData);
        if (updated != null) {
            return ResponseEntity.ok(ApiResponse.success("Faktur berhasil diperbarui", updated));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Faktur tidak ditemukan untuk diperbarui."));
    }

    /**
     * DELETE /api/invoices/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInvoice(@PathVariable String id) {
        boolean deleted = invoiceService.deleteInvoice(id);
        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success("Faktur berhasil dihapus", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Faktur tidak ditemukan."));
    }
}
