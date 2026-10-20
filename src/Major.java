import java.time.LocalDateTime;

/**
 * Model untuk tabel jurusan.
 */
public class Major {

    private long idJurusan;
    private String kodeJurusan;
    private String namaJurusan;
    private String keterangan;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Major() {
    }

    public Major(long idJurusan, String kodeJurusan, String namaJurusan) {
        this.idJurusan = idJurusan;
        this.kodeJurusan = kodeJurusan;
        this.namaJurusan = namaJurusan;
    }

    public long getIdJurusan() { return idJurusan; }
    public void setIdJurusan(long idJurusan) { this.idJurusan = idJurusan; }

    public String getKodeJurusan() { return kodeJurusan; }
    public void setKodeJurusan(String kodeJurusan) { this.kodeJurusan = kodeJurusan; }

    public String getNamaJurusan() { return namaJurusan; }
    public void setNamaJurusan(String namaJurusan) { this.namaJurusan = namaJurusan; }

    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Major{idJurusan=" + idJurusan + ", kodeJurusan='" + kodeJurusan
                + "', namaJurusan='" + namaJurusan + "'}";
    }
}
