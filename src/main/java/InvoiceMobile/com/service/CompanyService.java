package InvoiceMobile.com.service;

import InvoiceMobile.com.model.CompanyIdentity;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class CompanyService {
    private final AtomicReference<CompanyIdentity> companyStore = new AtomicReference<>(null);

    public CompanyIdentity getCompanyIdentity() {
        return companyStore.get();
    }

    public CompanyIdentity saveCompanyIdentity(CompanyIdentity identity) {
        companyStore.set(identity);
        return identity;
    }
}
