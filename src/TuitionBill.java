import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Model untuk tabel tagihan_spp.
 */
public class TuitionBill {

    private long idTagihan;
    private long idSiswa;
    private long idTarif;
    private long idTahunAjaran;
    private int bulan;
    private int tahun;
    private BigDecimal nominal;
    private LocalDate tanggalJatuhTempo;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TuitionBill() {
    }

    public TuitionBill(long idTagihan, long idSiswa, long idTarif,
                       int bulan, int tahun, BigDecimal nominal) {
        this.idTagihan = idTagihan;
        this.idSiswa = idSiswa;
        this.idTarif = idTarif;
        this.bulan = bulan;
        this.tahun = tahun;
        this.nominal = nominal;
    }

    public long getIdTagihan() { return idTagihan; }
    public void setIdTagihan(long idTagihan) { this.idTagihan = idTagihan; }

    public long getIdSiswa() { return idSiswa; }
    public void setIdSiswa(long idSiswa) { this.idSiswa = idSiswa; }

    public long getIdTarif() { return idTarif; }
    public void setIdTarif(long idTarif) { this.idTarif = idTarif; }

    public long getIdTahunAjaran() { return idTahunAjaran; }
    public void setIdTahunAjaran(long idTahunAjaran) { this.idTahunAjaran = idTahunAjaran; }

    public int getBulan() { return bulan; }
    public void setBulan(int bulan) { this.bulan = bulan; }

    public int getTahun() { return tahun; }
    public void setTahun(int tahun) { this.tahun = tahun; }

    public BigDecimal getNominal() { return nominal; }
    public void setNominal(BigDecimal nominal) { this.nominal = nominal; }

    public LocalDate getTanggalJatuhTempo() { return tanggalJatuhTempo; }
    public void setTanggalJatuhTempo(LocalDate tanggalJatuhTempo) { this.tanggalJatuhTempo = tanggalJatuhTempo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "TuitionBill{idTagihan=" + idTagihan + ", idSiswa=" + idSiswa
                + ", bulan=" + bulan + ", tahun=" + tahun
                + ", nominal=" + nominal + ", status='" + status + "'}";
    }
}
