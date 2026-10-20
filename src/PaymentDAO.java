import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk tabel pembayaran.
 * Catatan: kunci utama tabel pembayaran adalah "id", bukan "id_pembayaran".
 * Kolom user di tabel ini adalah "user_id", bukan "id_user".
 */
public class PaymentDAO extends BaseDAO {

    private static final String TABLE = "pembayaran";

    public List<Payment> findAll() {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE + " ORDER BY id";
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
            System.err.println("Gagal findAll pembayaran: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return list;
    }

    public Payment findById(long id) {
        String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("Gagal findById pembayaran: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return null;
    }

    public List<Payment> findByTeller(long userId) {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE + " WHERE user_id = ? ORDER BY id";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Gagal findByTeller pembayaran: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return list;
    }

    public List<Payment> findByTagihan(long tagihanSppId) {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE + " WHERE tagihan_spp_id = ? ORDER BY id";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, tagihanSppId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Gagal findByTagihan pembayaran: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return list;
    }

    public boolean save(Payment payment) {
        String sql = "INSERT INTO " + TABLE
                + " (nomor_transaksi, transaksi_id, tagihan_spp_id, siswa_id,"
                + " nominal_pembayaran, metode_pembayaran, status, user_id,"
                + " keterangan, nomor_referensi)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet keys = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, payment.getNomorTransaksi());
            if (payment.getTransaksiId() != null) {
                ps.setLong(2, payment.getTransaksiId());
            } else {
                ps.setNull(2, Types.BIGINT);
            }
            ps.setLong(3, payment.getTagihanSppId());
            ps.setLong(4, payment.getSiswaId());
            ps.setBigDecimal(5, payment.getNominalPembayaran());
            ps.setString(6, payment.getMetodePembayaran());
            ps.setString(7, payment.getStatus() != null ? payment.getStatus() : "berhasil");
            if (payment.getUserId() != null) {
                ps.setLong(8, payment.getUserId());
            } else {
                ps.setNull(8, Types.BIGINT);
            }
            ps.setString(9, payment.getKeterangan());
            ps.setString(10, payment.getNomorReferensi());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    payment.setId(keys.getLong(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Gagal save pembayaran: " + e.getMessage());
        } finally {
            closeQuietly(keys, ps, conn);
        }
        return false;
    }

    public boolean isNomorTransaksiExists(String nomorTransaksi) {
        String sql = "SELECT COUNT(*) FROM " + TABLE + " WHERE nomor_transaksi = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nomorTransaksi);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Gagal cek nomor_transaksi: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return false;
    }

    private Payment mapRow(ResultSet rs) throws SQLException {
        Payment p = new Payment();
        p.setId(rs.getLong("id"));
        p.setNomorTransaksi(rs.getString("nomor_transaksi"));
        long transaksiId = rs.getLong("transaksi_id");
        p.setTransaksiId(rs.wasNull() ? null : transaksiId);
        p.setTagihanSppId(rs.getLong("tagihan_spp_id"));
        p.setSiswaId(rs.getLong("siswa_id"));
        p.setNominalPembayaran(rs.getBigDecimal("nominal_pembayaran"));
        p.setMetodePembayaran(rs.getString("metode_pembayaran"));
        p.setStatus(rs.getString("status"));
        long userId = rs.getLong("user_id");
        p.setUserId(rs.wasNull() ? null : userId);
        p.setKeterangan(rs.getString("keterangan"));
        p.setNomorReferensi(rs.getString("nomor_referensi"));
        return p;
    }
}
