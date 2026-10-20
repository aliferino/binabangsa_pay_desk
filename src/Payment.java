import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Model untuk tabel pembayaran.
 * Catatan: kunci utama tabel pembayaran adalah kolom "id", bukan "id_pembayaran".
 * Kolom user di tabel ini adalah "user_id", bukan "id_user".
 */
public class Payment {

    private long id;
    private String nomorTransaksi;
    private Long transaksiId;
    private long tagihanSppId;
    private long siswaId;
    private BigDecimal nominalPembayaran;
    private String metodePembayaran;
    private String status;
    private Long userId;
    private String keterangan;
    private String nomorReferensi;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Payment() {
    }

    public Payment(long id, String nomorTransaksi, long tagihanSppId,
                   long siswaId, BigDecimal nominalPembayaran,
                   String metodePembayaran) {
        this.id = id;
        this.nomorTransaksi = nomorTransaksi;
        this.tagihanSppId = tagihanSppId;
        this.siswaId = siswaId;
        this.nominalPembayaran = nominalPembayaran;
        this.metodePembayaran = metodePembayaran;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNomorTransaksi() { return nomorTransaksi; }
    public void setNomorTransaksi(String nomorTransaksi) { this.nomorTransaksi = nomorTransaksi; }

    public Long getTransaksiId() { return transaksiId; }
    public void setTransaksiId(Long transaksiId) { this.transaksiId = transaksiId; }

    public long getTagihanSppId() { return tagihanSppId; }
    public void setTagihanSppId(long tagihanSppId) { this.tagihanSppId = tagihanSppId; }

    public long getSiswaId() { return siswaId; }
    public void setSiswaId(long siswaId) { this.siswaId = siswaId; }

    public BigDecimal getNominalPembayaran() { return nominalPembayaran; }
    public void setNominalPembayaran(BigDecimal nominalPembayaran) { this.nominalPembayaran = nominalPembayaran; }

    public String getMetodePembayaran() { return metodePembayaran; }
    public void setMetodePembayaran(String metodePembayaran) { this.metodePembayaran = metodePembayaran; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }

    public String getNomorReferensi() { return nomorReferensi; }
    public void setNomorReferensi(String nomorReferensi) { this.nomorReferensi = nomorReferensi; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Payment{id=" + id + ", nomorTransaksi='" + nomorTransaksi
                + "', tagihanSppId=" + tagihanSppId + ", siswaId=" + siswaId
                + ", metodePembayaran='" + metodePembayaran + "', status='" + status + "'}";
    }
}
