package InvoiceMobile.com.dto;

public class DashboardStatsResponse {
    private double totalPendingAmount;
    private double totalCicilanAmount;
    private double totalLunasAmount;
    private int overdueCount;
    private int lunasRate;

    public DashboardStatsResponse() {}

    public DashboardStatsResponse(double totalPendingAmount, double totalCicilanAmount, double totalLunasAmount, int overdueCount, int lunasRate) {
        this.totalPendingAmount = totalPendingAmount;
        this.totalCicilanAmount = totalCicilanAmount;
        this.totalLunasAmount = totalLunasAmount;
        this.overdueCount = overdueCount;
        this.lunasRate = lunasRate;
    }

    public double getTotalPendingAmount() { return totalPendingAmount; }
    public void setTotalPendingAmount(double totalPendingAmount) { this.totalPendingAmount = totalPendingAmount; }

    public double getTotalCicilanAmount() { return totalCicilanAmount; }
    public void setTotalCicilanAmount(double totalCicilanAmount) { this.totalCicilanAmount = totalCicilanAmount; }

    public double getTotalLunasAmount() { return totalLunasAmount; }
    public void setTotalLunasAmount(double totalLunasAmount) { this.totalLunasAmount = totalLunasAmount; }

    public int getOverdueCount() { return overdueCount; }
    public void setOverdueCount(int overdueCount) { this.overdueCount = overdueCount; }

    public int getLunasRate() { return lunasRate; }
    public void setLunasRate(int lunasRate) { this.lunasRate = lunasRate; }
}
