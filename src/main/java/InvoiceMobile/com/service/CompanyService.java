package InvoiceMobile.com.service;

import InvoiceMobile.com.model.CompanyIdentity;
import InvoiceMobile.com.repository.CompanyIdentityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyIdentityRepository companyIdentityRepository;

    public CompanyIdentity getCompanyIdentity() {
        return companyIdentityRepository.findById("main").orElse(null);
    }

    public CompanyIdentity saveCompanyIdentity(CompanyIdentity identity) {
        identity.setId("main");
        return companyIdentityRepository.save(identity);
    }
}
