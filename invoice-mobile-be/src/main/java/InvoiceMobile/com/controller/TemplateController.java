package InvoiceMobile.com.controller;

import InvoiceMobile.com.dto.ApiResponse;
import InvoiceMobile.com.model.TemplateSettings;
import InvoiceMobile.com.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/template-settings")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @GetMapping
    public ResponseEntity<ApiResponse<TemplateSettings>> getTemplateSettings() {
        TemplateSettings settings = templateService.getTemplateSettings();
        return ResponseEntity.ok(ApiResponse.success("Success", settings));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TemplateSettings>> saveTemplateSettings(@RequestBody TemplateSettings settings) {
        TemplateSettings saved = templateService.saveTemplateSettings(settings);
        return ResponseEntity.ok(ApiResponse.success("Pengaturan template berhasil disimpan", saved));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<TemplateSettings>> updateTemplateSettings(@RequestBody TemplateSettings settings) {
        TemplateSettings saved = templateService.saveTemplateSettings(settings);
        return ResponseEntity.ok(ApiResponse.success("Pengaturan template berhasil diperbarui", saved));
    }
}
