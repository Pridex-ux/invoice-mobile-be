package InvoiceMobile.com.dto;

import InvoiceMobile.com.model.Customer;
import InvoiceMobile.com.model.InvoiceItem;
import InvoiceMobile.com.model.PaymentStatus;

import java.util.List;

public class CreateInvoiceRequest {
    private String id;
    private String invoiceNumber;
    private Customer customer;
    private List<InvoiceItem> items;
    private double subtotal;
    private double discountPercentage;
    private double discountAmount;
    private double taxPercentage;
    private double taxAmount;
    private double totalAmount;
    private PaymentStatus status;
    private double initialDpPaid;
    private String dueDate;
    private String notes;

    public CreateInvoiceRequest() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public List<InvoiceItem> getItems() { return items; }
    public void setItems(List<InvoiceItem> items) { this.items = items; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(double discountPercentage) { this.discountPercentage = discountPercentage; }

    public double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }

    public double getTaxPercentage() { return taxPercentage; }
    public void setTaxPercentage(double taxPercentage) { this.taxPercentage = taxPercentage; }

    public double getTaxAmount() { return taxAmount; }
    public void setTaxAmount(double taxAmount) { this.taxAmount = taxAmount; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public double getInitialDpPaid() { return initialDpPaid; }
    public void setInitialDpPaid(double initialDpPaid) { this.initialDpPaid = initialDpPaid; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
