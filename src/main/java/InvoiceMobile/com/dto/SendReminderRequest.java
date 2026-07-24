package InvoiceMobile.com.dto;

public class SendReminderRequest {
    private String invoiceId;
    private String customerPhone;
    private String messageText;

    public SendReminderRequest() {}

    public String getInvoiceId() { return invoiceId; }
    public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
}
