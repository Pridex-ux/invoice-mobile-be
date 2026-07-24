package InvoiceMobile.com.repository;

import InvoiceMobile.com.model.TemplateSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateSettingsRepository extends JpaRepository<TemplateSettings, String> {
}
