package InvoiceMobile.com.repository;

import InvoiceMobile.com.model.CompanyIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyIdentityRepository extends JpaRepository<CompanyIdentity, String> {
}
