import java.time.LocalDateTime;

/**
 * Model untuk tabel kelas.
 */
public class Classroom {

    private long idKelas;
    private String namaKelas;
    private String tingkat;
    private long idJurusan;
    private long idTahunAjaran;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Classroom() {
    }

    public Classroom(long idKelas, String namaKelas, String tingkat,
                     long idJurusan, long idTahunAjaran) {
        this.idKelas = idKelas;
        this.namaKelas = namaKelas;
        this.tingkat = tingkat;
        this.idJurusan = idJurusan;
        this.idTahunAjaran = idTahunAjaran;
    }

    public long getIdKelas() { return idKelas; }
    public void setIdKelas(long idKelas) { this.idKelas = idKelas; }

    public String getNamaKelas() { return namaKelas; }
    public void setNamaKelas(String namaKelas) { this.namaKelas = namaKelas; }

    public String getTingkat() { return tingkat; }
    public void setTingkat(String tingkat) { this.tingkat = tingkat; }

    public long getIdJurusan() { return idJurusan; }
    public void setIdJurusan(long idJurusan) { this.idJurusan = idJurusan; }

    public long getIdTahunAjaran() { return idTahunAjaran; }
    public void setIdTahunAjaran(long idTahunAjaran) { this.idTahunAjaran = idTahunAjaran; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Classroom{idKelas=" + idKelas + ", namaKelas='" + namaKelas
                + "', tingkat='" + tingkat + "'}";
    }
}
