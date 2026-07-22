package InvoiceMobile.com.dto;

public class RecordPaymentRequest {
    private double amountPaid;
    private String method;
    private String paymentDate;

    public RecordPaymentRequest() {}

    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }
}
