package InvoiceMobile.com.model;

public class TermOption {
    private boolean allowInstallment;
    private double minimumDpPercent;
    private int maxInstallments;

    public TermOption() {
        this.allowInstallment = true;
        this.minimumDpPercent = 0;
        this.maxInstallments = 3;
    }

    public TermOption(boolean allowInstallment, double minimumDpPercent, int maxInstallments) {
        this.allowInstallment = allowInstallment;
        this.minimumDpPercent = minimumDpPercent;
        this.maxInstallments = maxInstallments;
    }

    public boolean isAllowInstallment() { return allowInstallment; }
    public void setAllowInstallment(boolean allowInstallment) { this.allowInstallment = allowInstallment; }

    public double getMinimumDpPercent() { return minimumDpPercent; }
    public void setMinimumDpPercent(double minimumDpPercent) { this.minimumDpPercent = minimumDpPercent; }

    public int getMaxInstallments() { return maxInstallments; }
    public void setMaxInstallments(int maxInstallments) { this.maxInstallments = maxInstallments; }
}
