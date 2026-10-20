import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Model untuk tabel tarif_spp.
 */
public class TuitionRate {

    private long idTarif;
    private long idTahunAjaran;
    private BigDecimal nominal;
    private String keterangan;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TuitionRate() {
    }

    public TuitionRate(long idTarif, long idTahunAjaran, BigDecimal nominal) {
        this.idTarif = idTarif;
        this.idTahunAjaran = idTahunAjaran;
        this.nominal = nominal;
    }

    public long getIdTarif() { return idTarif; }
    public void setIdTarif(long idTarif) { this.idTarif = idTarif; }

    public long getIdTahunAjaran() { return idTahunAjaran; }
    public void setIdTahunAjaran(long idTahunAjaran) { this.idTahunAjaran = idTahunAjaran; }

    public BigDecimal getNominal() { return nominal; }
    public void setNominal(BigDecimal nominal) { this.nominal = nominal; }

    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "TuitionRate{idTarif=" + idTarif + ", idTahunAjaran=" + idTahunAjaran
                + ", nominal=" + nominal + ", status='" + status + "'}";
    }
}
