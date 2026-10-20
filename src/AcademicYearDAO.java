import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk tabel tahun_ajaran.
 */
public class AcademicYearDAO extends BaseDAO {

    private static final String TABLE = "tahun_ajaran";

    public List<AcademicYear> findAll() {
        List<AcademicYear> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE + " ORDER BY id_tahun_ajaran";
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
            System.err.println("Gagal findAll tahun_ajaran: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return list;
    }

    public AcademicYear findById(long idTahunAjaran) {
        String sql = "SELECT * FROM " + TABLE + " WHERE id_tahun_ajaran = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, idTahunAjaran);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("Gagal findById tahun_ajaran: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return null;
    }

    public boolean save(AcademicYear year) {
        String sql = "INSERT INTO " + TABLE
                + " (tahun_ajaran, tanggal_mulai, tanggal_selesai, status) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet keys = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, year.getTahunAjaran());
            ps.setDate(2, Date.valueOf(year.getTanggalMulai()));
            ps.setDate(3, Date.valueOf(year.getTanggalSelesai()));
            ps.setString(4, year.getStatus());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    year.setIdTahunAjaran(keys.getLong(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Gagal save tahun_ajaran: " + e.getMessage());
        } finally {
            closeQuietly(keys, ps, conn);
        }
        return false;
    }

    public boolean update(AcademicYear year) {
        String sql = "UPDATE " + TABLE
                + " SET tahun_ajaran=?, tanggal_mulai=?, tanggal_selesai=?, status=?"
                + " WHERE id_tahun_ajaran=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, year.getTahunAjaran());
            ps.setDate(2, Date.valueOf(year.getTanggalMulai()));
            ps.setDate(3, Date.valueOf(year.getTanggalSelesai()));
            ps.setString(4, year.getStatus());
            ps.setLong(5, year.getIdTahunAjaran());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal update tahun_ajaran: " + e.getMessage());
        } finally {
            closeQuietly(null, ps, conn);
        }
        return false;
    }

    private AcademicYear mapRow(ResultSet rs) throws SQLException {
        AcademicYear a = new AcademicYear();
        a.setIdTahunAjaran(rs.getLong("id_tahun_ajaran"));
        a.setTahunAjaran(rs.getString("tahun_ajaran"));
        a.setTanggalMulai(rs.getDate("tanggal_mulai").toLocalDate());
        a.setTanggalSelesai(rs.getDate("tanggal_selesai").toLocalDate());
        a.setStatus(rs.getString("status"));
        return a;
    }
}
