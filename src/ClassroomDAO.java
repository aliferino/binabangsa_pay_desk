import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk tabel kelas.
 */
public class ClassroomDAO extends BaseDAO {

    private static final String TABLE = "kelas";

    public List<Classroom> findAll() {
        List<Classroom> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE + " ORDER BY id_kelas";
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
            System.err.println("Gagal findAll kelas: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return list;
    }

    public Classroom findById(long idKelas) {
        String sql = "SELECT * FROM " + TABLE + " WHERE id_kelas = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, idKelas);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("Gagal findById kelas: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return null;
    }

    public boolean save(Classroom classroom) {
        String sql = "INSERT INTO " + TABLE
                + " (nama_kelas, tingkat, id_jurusan, id_tahun_ajaran) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet keys = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, classroom.getNamaKelas());
            ps.setString(2, classroom.getTingkat());
            ps.setLong(3, classroom.getIdJurusan());
            ps.setLong(4, classroom.getIdTahunAjaran());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    classroom.setIdKelas(keys.getLong(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Gagal save kelas: " + e.getMessage());
        } finally {
            closeQuietly(keys, ps, conn);
        }
        return false;
    }

    public boolean update(Classroom classroom) {
        String sql = "UPDATE " + TABLE
                + " SET nama_kelas=?, tingkat=?, id_jurusan=?, id_tahun_ajaran=?"
                + " WHERE id_kelas=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, classroom.getNamaKelas());
            ps.setString(2, classroom.getTingkat());
            ps.setLong(3, classroom.getIdJurusan());
            ps.setLong(4, classroom.getIdTahunAjaran());
            ps.setLong(5, classroom.getIdKelas());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal update kelas: " + e.getMessage());
        } finally {
            closeQuietly(null, ps, conn);
        }
        return false;
    }

    private Classroom mapRow(ResultSet rs) throws SQLException {
        Classroom c = new Classroom();
        c.setIdKelas(rs.getLong("id_kelas"));
        c.setNamaKelas(rs.getString("nama_kelas"));
        c.setTingkat(rs.getString("tingkat"));
        c.setIdJurusan(rs.getLong("id_jurusan"));
        c.setIdTahunAjaran(rs.getLong("id_tahun_ajaran"));
        return c;
    }
}
