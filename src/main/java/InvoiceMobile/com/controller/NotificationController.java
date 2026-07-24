package InvoiceMobile.com.controller;

import InvoiceMobile.com.dto.ApiResponse;
import InvoiceMobile.com.dto.SendReminderRequest;
import InvoiceMobile.com.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * GET /api/notifications
     */
    @GetMapping("/notifications")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getNotifications() {
        List<Map<String, Object>> notifs = notificationService.getNotifications();
        return ResponseEntity.ok(ApiResponse.success(notifs));
    }

    /**
     * POST /api/reminders/send-whatsapp
     */
    @PostMapping("/reminders/send-whatsapp")
    public ResponseEntity<ApiResponse<Map<String, Object>>> sendWhatsAppReminder(@RequestBody SendReminderRequest request) {
        Map<String, Object> result = notificationService.buildWhatsAppUrl(request);
        return ResponseEntity.ok(ApiResponse.success("URL pengingat WhatsApp berhasil dibuat", result));
    }
}
