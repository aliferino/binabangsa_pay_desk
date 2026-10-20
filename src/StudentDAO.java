import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk tabel siswa.
 */
public class StudentDAO extends BaseDAO {

    private static final String TABLE = "siswa";

    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE + " ORDER BY id_siswa";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Gagal findAll siswa: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return list;
    }

    public Student findById(long idSiswa) {
        String sql = "SELECT * FROM " + TABLE + " WHERE id_siswa = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, idSiswa);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("Gagal findById siswa: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return null;
    }

    public List<Student> search(String keyword) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE
                + " WHERE nis LIKE ? OR nisn LIKE ? OR nama LIKE ? ORDER BY nama";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Gagal search siswa: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return list;
    }

    public boolean save(Student student) {
        String sql = "INSERT INTO " + TABLE
                + " (nis, nisn, nama, jenis_kelamin, tempat_lahir, tanggal_lahir,"
                + " alamat, no_hp, nama_orang_tua, id_kelas, id_tahun_ajaran, status)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet keys = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, student.getNis());
            ps.setString(2, student.getNisn());
            ps.setString(3, student.getNama());
            ps.setString(4, student.getJenisKelamin());
            ps.setString(5, student.getTempatLahir());
            ps.setDate(6, Date.valueOf(student.getTanggalLahir()));
            ps.setString(7, student.getAlamat());
            ps.setString(8, student.getNoHp());
            ps.setString(9, student.getNamaOrangTua());
            ps.setLong(10, student.getIdKelas());
            ps.setLong(11, student.getIdTahunAjaran());
            ps.setString(12, student.getStatus() != null ? student.getStatus() : "aktif");
            int affected = ps.executeUpdate();
            if (affected > 0) {
                keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    student.setIdSiswa(keys.getLong(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Gagal save siswa: " + e.getMessage());
        } finally {
            closeQuietly(keys, ps, conn);
        }
        return false;
    }

    public boolean update(Student student) {
        String sql = "UPDATE " + TABLE
                + " SET nis=?, nisn=?, nama=?, jenis_kelamin=?, tempat_lahir=?,"
                + " tanggal_lahir=?, alamat=?, no_hp=?, nama_orang_tua=?,"
                + " id_kelas=?, id_tahun_ajaran=?, status=?"
                + " WHERE id_siswa=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, student.getNis());
            ps.setString(2, student.getNisn());
            ps.setString(3, student.getNama());
            ps.setString(4, student.getJenisKelamin());
            ps.setString(5, student.getTempatLahir());
            ps.setDate(6, Date.valueOf(student.getTanggalLahir()));
            ps.setString(7, student.getAlamat());
            ps.setString(8, student.getNoHp());
            ps.setString(9, student.getNamaOrangTua());
            ps.setLong(10, student.getIdKelas());
            ps.setLong(11, student.getIdTahunAjaran());
            ps.setString(12, student.getStatus());
            ps.setLong(13, student.getIdSiswa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal update siswa: " + e.getMessage());
        } finally {
            closeQuietly(null, ps, conn);
        }
        return false;
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setIdSiswa(rs.getLong("id_siswa"));
        s.setNis(rs.getString("nis"));
        s.setNisn(rs.getString("nisn"));
        s.setNama(rs.getString("nama"));
        s.setJenisKelamin(rs.getString("jenis_kelamin"));
        s.setTempatLahir(rs.getString("tempat_lahir"));
        s.setTanggalLahir(rs.getDate("tanggal_lahir").toLocalDate());
        s.setAlamat(rs.getString("alamat"));
        s.setNoHp(rs.getString("no_hp"));
        s.setNamaOrangTua(rs.getString("nama_orang_tua"));
        s.setIdKelas(rs.getLong("id_kelas"));
        s.setIdTahunAjaran(rs.getLong("id_tahun_ajaran"));
        s.setStatus(rs.getString("status"));
        return s;
    }
}
