package InvoiceMobile.com.service;

import InvoiceMobile.com.model.TemplateSettings;
import InvoiceMobile.com.repository.TemplateSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    @Autowired
    private TemplateSettingsRepository templateSettingsRepository;

    public TemplateSettings getTemplateSettings() {
        return templateSettingsRepository.findById("main").orElseGet(TemplateSettings::new);
    }

    public TemplateSettings saveTemplateSettings(TemplateSettings settings) {
        settings.setId("main");
        return templateSettingsRepository.save(settings);
    }
}
