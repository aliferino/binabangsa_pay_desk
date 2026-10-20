import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Model untuk tabel transaksi_payment_gateway.
 * Tidak dipakai di aplikasi desktop (payment gateway dilarang), dibuat
 * supaya lengkap sepuluh model sesuai brief.
 */
public class PaymentGatewayTransaction {

    private long id;
    private String transactionId;
    private Long virtualAccountId;
    private long siswaId;
    private BigDecimal nominal;
    private String statusTransaksi;
    private String paymentChannel;
    private String referenceNumber;
    private String rawResponse;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String xenditPaymentId;
    private LocalDateTime paidAt;

    public PaymentGatewayTransaction() {
    }

    public PaymentGatewayTransaction(long id, String transactionId,
                                     long siswaId, BigDecimal nominal) {
        this.id = id;
        this.transactionId = transactionId;
        this.siswaId = siswaId;
        this.nominal = nominal;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public Long getVirtualAccountId() { return virtualAccountId; }
    public void setVirtualAccountId(Long virtualAccountId) { this.virtualAccountId = virtualAccountId; }

    public long getSiswaId() { return siswaId; }
    public void setSiswaId(long siswaId) { this.siswaId = siswaId; }

    public BigDecimal getNominal() { return nominal; }
    public void setNominal(BigDecimal nominal) { this.nominal = nominal; }

    public String getStatusTransaksi() { return statusTransaksi; }
    public void setStatusTransaksi(String statusTransaksi) { this.statusTransaksi = statusTransaksi; }

    public String getPaymentChannel() { return paymentChannel; }
    public void setPaymentChannel(String paymentChannel) { this.paymentChannel = paymentChannel; }

    public String getReferenceNumber() { return referenceNumber; }
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }

    public String getRawResponse() { return rawResponse; }
    public void setRawResponse(String rawResponse) { this.rawResponse = rawResponse; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getXenditPaymentId() { return xenditPaymentId; }
    public void setXenditPaymentId(String xenditPaymentId) { this.xenditPaymentId = xenditPaymentId; }

    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }

    @Override
    public String toString() {
        return "PaymentGatewayTransaction{id=" + id + ", transactionId='" + transactionId
                + "', siswaId=" + siswaId + ", nominal=" + nominal
                + ", statusTransaksi='" + statusTransaksi + "'}";
    }
}
