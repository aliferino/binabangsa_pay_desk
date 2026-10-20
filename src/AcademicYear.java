import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Model untuk tabel tahun_ajaran.
 */
public class AcademicYear {

    private long idTahunAjaran;
    private String tahunAjaran;
    private LocalDate tanggalMulai;
    private LocalDate tanggalSelesai;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AcademicYear() {
    }

    public AcademicYear(long idTahunAjaran, String tahunAjaran,
                       LocalDate tanggalMulai, LocalDate tanggalSelesai) {
        this.idTahunAjaran = idTahunAjaran;
        this.tahunAjaran = tahunAjaran;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
    }

    public long getIdTahunAjaran() { return idTahunAjaran; }
    public void setIdTahunAjaran(long idTahunAjaran) { this.idTahunAjaran = idTahunAjaran; }

    public String getTahunAjaran() { return tahunAjaran; }
    public void setTahunAjaran(String tahunAjaran) { this.tahunAjaran = tahunAjaran; }

    public LocalDate getTanggalMulai() { return tanggalMulai; }
    public void setTanggalMulai(LocalDate tanggalMulai) { this.tanggalMulai = tanggalMulai; }

    public LocalDate getTanggalSelesai() { return tanggalSelesai; }
    public void setTanggalSelesai(LocalDate tanggalSelesai) { this.tanggalSelesai = tanggalSelesai; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "AcademicYear{idTahunAjaran=" + idTahunAjaran
                + ", tahunAjaran='" + tahunAjaran + "', status='" + status + "'}";
    }
}
