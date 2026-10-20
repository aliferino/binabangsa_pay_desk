import java.time.LocalDateTime;

/**
 * Model untuk tabel virtual_account.
 * Tidak dipakai di aplikasi desktop (VA dilarang), dibuat supaya lengkap
 * sepuluh model sesuai brief.
 */
public class VirtualAccount {

    private long id;
    private long siswaId;
    private String nomorVa;
    private String prefix;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String xenditId;
    private String accountNumber;
    private String bankCode;
    private String externalId;
    private LocalDateTime expiredAt;

    public VirtualAccount() {
    }

    public VirtualAccount(long id, long siswaId, String nomorVa) {
        this.id = id;
        this.siswaId = siswaId;
        this.nomorVa = nomorVa;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getSiswaId() { return siswaId; }
    public void setSiswaId(long siswaId) { this.siswaId = siswaId; }

    public String getNomorVa() { return nomorVa; }
    public void setNomorVa(String nomorVa) { this.nomorVa = nomorVa; }

    public String getPrefix() { return prefix; }
    public void setPrefix(String prefix) { this.prefix = prefix; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getXenditId() { return xenditId; }
    public void setXenditId(String xenditId) { this.xenditId = xenditId; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getBankCode() { return bankCode; }
    public void setBankCode(String bankCode) { this.bankCode = bankCode; }

    public String getExternalId() { return externalId; }
    public void setExternalId(String externalId) { this.externalId = externalId; }

    public LocalDateTime getExpiredAt() { return expiredAt; }
    public void setExpiredAt(LocalDateTime expiredAt) { this.expiredAt = expiredAt; }

    @Override
    public String toString() {
        return "VirtualAccount{id=" + id + ", siswaId=" + siswaId
                + ", nomorVa='" + nomorVa + "', status='" + status + "'}";
    }
}
