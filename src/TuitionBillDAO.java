import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk tabel tagihan_spp.
 */
public class TuitionBillDAO extends BaseDAO {

    private static final String TABLE = "tagihan_spp";

    public List<TuitionBill> findAll() {
        List<TuitionBill> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE + " ORDER BY id_tagihan";
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
            System.err.println("Gagal findAll tagihan_spp: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return list;
    }

    public TuitionBill findById(long idTagihan) {
        String sql = "SELECT * FROM " + TABLE + " WHERE id_tagihan = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, idTagihan);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("Gagal findById tagihan_spp: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return null;
    }

    public List<TuitionBill> findByStudent(long idSiswa) {
        List<TuitionBill> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE + " WHERE id_siswa = ? ORDER BY tahun, bulan";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, idSiswa);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Gagal findByStudent tagihan_spp: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return list;
    }

    public boolean save(TuitionBill bill) {
        String sql = "INSERT INTO " + TABLE
                + " (id_siswa, id_tarif, id_tahun_ajaran, bulan, tahun,"
                + " nominal, tanggal_jatuh_tempo, status)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet keys = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, bill.getIdSiswa());
            ps.setLong(2, bill.getIdTarif());
            ps.setLong(3, bill.getIdTahunAjaran());
            ps.setInt(4, bill.getBulan());
            ps.setInt(5, bill.getTahun());
            ps.setBigDecimal(6, bill.getNominal());
            ps.setDate(7, Date.valueOf(bill.getTanggalJatuhTempo()));
            ps.setString(8, bill.getStatus() != null ? bill.getStatus() : "belum_bayar");
            int affected = ps.executeUpdate();
            if (affected > 0) {
                keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    bill.setIdTagihan(keys.getLong(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Gagal save tagihan_spp: " + e.getMessage());
        } finally {
            closeQuietly(keys, ps, conn);
        }
        return false;
    }

    public boolean update(TuitionBill bill) {
        String sql = "UPDATE " + TABLE
                + " SET id_siswa=?, id_tarif=?, id_tahun_ajaran=?, bulan=?, tahun=?,"
                + " nominal=?, tanggal_jatuh_tempo=?, status=?"
                + " WHERE id_tagihan=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, bill.getIdSiswa());
            ps.setLong(2, bill.getIdTarif());
            ps.setLong(3, bill.getIdTahunAjaran());
            ps.setInt(4, bill.getBulan());
            ps.setInt(5, bill.getTahun());
            ps.setBigDecimal(6, bill.getNominal());
            ps.setDate(7, Date.valueOf(bill.getTanggalJatuhTempo()));
            ps.setString(8, bill.getStatus());
            ps.setLong(9, bill.getIdTagihan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal update tagihan_spp: " + e.getMessage());
        } finally {
            closeQuietly(null, ps, conn);
        }
        return false;
    }

    public boolean updateStatus(long idTagihan, String status) {
        String sql = "UPDATE " + TABLE + " SET status=? WHERE id_tagihan=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setLong(2, idTagihan);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal updateStatus tagihan_spp: " + e.getMessage());
        } finally {
            closeQuietly(null, ps, conn);
        }
        return false;
    }

    private TuitionBill mapRow(ResultSet rs) throws SQLException {
        TuitionBill t = new TuitionBill();
        t.setIdTagihan(rs.getLong("id_tagihan"));
        t.setIdSiswa(rs.getLong("id_siswa"));
        t.setIdTarif(rs.getLong("id_tarif"));
        t.setIdTahunAjaran(rs.getLong("id_tahun_ajaran"));
        t.setBulan(rs.getInt("bulan"));
        t.setTahun(rs.getInt("tahun"));
        t.setNominal(rs.getBigDecimal("nominal"));
        t.setTanggalJatuhTempo(rs.getDate("tanggal_jatuh_tempo").toLocalDate());
        t.setStatus(rs.getString("status"));
        return t;
    }
}
