package InvoiceMobile.com.controller;

import InvoiceMobile.com.dto.ApiResponse;
import InvoiceMobile.com.model.CompanyIdentity;
import InvoiceMobile.com.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<ApiResponse<CompanyIdentity>> getCompanyIdentity() {
        CompanyIdentity company = companyService.getCompanyIdentity();
        return ResponseEntity.ok(ApiResponse.success("Success", company));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CompanyIdentity>> saveCompanyIdentity(@RequestBody CompanyIdentity identity) {
        CompanyIdentity saved = companyService.saveCompanyIdentity(identity);
        return ResponseEntity.ok(ApiResponse.success("Identitas perusahaan berhasil disimpan", saved));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CompanyIdentity>> updateCompanyIdentity(@RequestBody CompanyIdentity identity) {
        CompanyIdentity saved = companyService.saveCompanyIdentity(identity);
        return ResponseEntity.ok(ApiResponse.success("Identitas perusahaan berhasil diperbarui", saved));
    }
}
