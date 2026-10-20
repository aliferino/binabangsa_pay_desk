import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk tabel tarif_spp.
 */
public class TuitionRateDAO extends BaseDAO {

    private static final String TABLE = "tarif_spp";

    public List<TuitionRate> findAll() {
        List<TuitionRate> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE + " ORDER BY id_tarif";
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
            System.err.println("Gagal findAll tarif_spp: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return list;
    }

    public TuitionRate findById(long idTarif) {
        String sql = "SELECT * FROM " + TABLE + " WHERE id_tarif = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, idTarif);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("Gagal findById tarif_spp: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return null;
    }

    public boolean save(TuitionRate rate) {
        String sql = "INSERT INTO " + TABLE
                + " (id_tahun_ajaran, nominal, keterangan, status) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet keys = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, rate.getIdTahunAjaran());
            ps.setBigDecimal(2, rate.getNominal());
            ps.setString(3, rate.getKeterangan());
            ps.setString(4, rate.getStatus() != null ? rate.getStatus() : "aktif");
            int affected = ps.executeUpdate();
            if (affected > 0) {
                keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    rate.setIdTarif(keys.getLong(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Gagal save tarif_spp: " + e.getMessage());
        } finally {
            closeQuietly(keys, ps, conn);
        }
        return false;
    }

    public boolean update(TuitionRate rate) {
        String sql = "UPDATE " + TABLE
                + " SET id_tahun_ajaran=?, nominal=?, keterangan=?, status=?"
                + " WHERE id_tarif=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, rate.getIdTahunAjaran());
            ps.setBigDecimal(2, rate.getNominal());
            ps.setString(3, rate.getKeterangan());
            ps.setString(4, rate.getStatus());
            ps.setLong(5, rate.getIdTarif());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal update tarif_spp: " + e.getMessage());
        } finally {
            closeQuietly(null, ps, conn);
        }
        return false;
    }

    private TuitionRate mapRow(ResultSet rs) throws SQLException {
        TuitionRate t = new TuitionRate();
        t.setIdTarif(rs.getLong("id_tarif"));
        t.setIdTahunAjaran(rs.getLong("id_tahun_ajaran"));
        t.setNominal(rs.getBigDecimal("nominal"));
        t.setKeterangan(rs.getString("keterangan"));
        t.setStatus(rs.getString("status"));
        return t;
    }
}
