import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Model untuk tabel siswa.
 */
public class Student {

    private long idSiswa;
    private String nis;
    private String nisn;
    private String nama;
    private String jenisKelamin;
    private String tempatLahir;
    private LocalDate tanggalLahir;
    private String alamat;
    private String noHp;
    private String namaOrangTua;
    private long idKelas;
    private long idTahunAjaran;
    private Long idUser;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Student() {
    }

    public Student(long idSiswa, String nis, String nisn, String nama,
                   String jenisKelamin, long idKelas, long idTahunAjaran) {
        this.idSiswa = idSiswa;
        this.nis = nis;
        this.nisn = nisn;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.idKelas = idKelas;
        this.idTahunAjaran = idTahunAjaran;
    }

    public long getIdSiswa() { return idSiswa; }
    public void setIdSiswa(long idSiswa) { this.idSiswa = idSiswa; }

    public String getNis() { return nis; }
    public void setNis(String nis) { this.nis = nis; }

    public String getNisn() { return nisn; }
    public void setNisn(String nisn) { this.nisn = nisn; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    public String getTempatLahir() { return tempatLahir; }
    public void setTempatLahir(String tempatLahir) { this.tempatLahir = tempatLahir; }

    public LocalDate getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(LocalDate tanggalLahir) { this.tanggalLahir = tanggalLahir; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }

    public String getNamaOrangTua() { return namaOrangTua; }
    public void setNamaOrangTua(String namaOrangTua) { this.namaOrangTua = namaOrangTua; }

    public long getIdKelas() { return idKelas; }
    public void setIdKelas(long idKelas) { this.idKelas = idKelas; }

    public long getIdTahunAjaran() { return idTahunAjaran; }
    public void setIdTahunAjaran(long idTahunAjaran) { this.idTahunAjaran = idTahunAjaran; }

    public Long getIdUser() { return idUser; }
    public void setIdUser(Long idUser) { this.idUser = idUser; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Student{idSiswa=" + idSiswa + ", nis='" + nis + "', nisn='" + nisn
                + "', nama='" + nama + "', status='" + status + "'}";
    }
}
