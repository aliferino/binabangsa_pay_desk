import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk tabel jurusan.
 */
public class MajorDAO extends BaseDAO {

    private static final String TABLE = "jurusan";

    public List<Major> findAll() {
        List<Major> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE + " ORDER BY id_jurusan";
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
            System.err.println("Gagal findAll jurusan: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return list;
    }

    public Major findById(long idJurusan) {
        String sql = "SELECT * FROM " + TABLE + " WHERE id_jurusan = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, idJurusan);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("Gagal findById jurusan: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return null;
    }

    public boolean save(Major major) {
        String sql = "INSERT INTO " + TABLE
                + " (kode_jurusan, nama_jurusan, keterangan) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet keys = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, major.getKodeJurusan());
            ps.setString(2, major.getNamaJurusan());
            ps.setString(3, major.getKeterangan());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    major.setIdJurusan(keys.getLong(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Gagal save jurusan: " + e.getMessage());
        } finally {
            closeQuietly(keys, ps, conn);
        }
        return false;
    }

    public boolean update(Major major) {
        String sql = "UPDATE " + TABLE
                + " SET kode_jurusan=?, nama_jurusan=?, keterangan=? WHERE id_jurusan=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, major.getKodeJurusan());
            ps.setString(2, major.getNamaJurusan());
            ps.setString(3, major.getKeterangan());
            ps.setLong(4, major.getIdJurusan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal update jurusan: " + e.getMessage());
        } finally {
            closeQuietly(null, ps, conn);
        }
        return false;
    }

    private Major mapRow(ResultSet rs) throws SQLException {
        Major m = new Major();
        m.setIdJurusan(rs.getLong("id_jurusan"));
        m.setKodeJurusan(rs.getString("kode_jurusan"));
        m.setNamaJurusan(rs.getString("nama_jurusan"));
        m.setKeterangan(rs.getString("keterangan"));
        return m;
    }
}
