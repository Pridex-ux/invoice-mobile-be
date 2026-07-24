package InvoiceMobile.com.service;

import InvoiceMobile.com.model.TemplateSettings;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class TemplateService {
    private final AtomicReference<TemplateSettings> templateStore = new AtomicReference<>(new TemplateSettings());

    public TemplateSettings getTemplateSettings() {
        return templateStore.get();
    }

    public TemplateSettings saveTemplateSettings(TemplateSettings settings) {
        templateStore.set(settings);
        return settings;
    }
}
