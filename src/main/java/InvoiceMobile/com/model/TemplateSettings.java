package InvoiceMobile.com.model;

public class TemplateSettings {
    private String selectedTheme;
    private boolean showStamp;
    private boolean showQrCode;
    private boolean showSignature;
    private String footerNotes;
    private String paymentTerms;

    public TemplateSettings() {
        this.selectedTheme = "modern";
        this.showStamp = false;
        this.showQrCode = false;
        this.showSignature = true;
        this.footerNotes = "";
        this.paymentTerms = "Pembayaran dianggap sah setelah dana masuk ke rekening bank yang tercantum.";
    }

    public String getSelectedTheme() { return selectedTheme; }
    public void setSelectedTheme(String selectedTheme) { this.selectedTheme = selectedTheme; }

    public boolean isShowStamp() { return showStamp; }
    public void setShowStamp(boolean showStamp) { this.showStamp = showStamp; }

    public boolean isShowQrCode() { return showQrCode; }
    public void setShowQrCode(boolean showQrCode) { this.showQrCode = showQrCode; }

    public boolean isShowSignature() { return showSignature; }
    public void setShowSignature(boolean showSignature) { this.showSignature = showSignature; }

    public String getFooterNotes() { return footerNotes; }
    public void setFooterNotes(String footerNotes) { this.footerNotes = footerNotes; }

    public String getPaymentTerms() { return paymentTerms; }
    public void setPaymentTerms(String paymentTerms) { this.paymentTerms = paymentTerms; }
}
