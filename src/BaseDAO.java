import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BaseDAO: metode bantu untuk semua DAO.
 * Menyediakan koneksi, pemetaan ResultSet, dan penutupan sumber daya.
 */
public abstract class BaseDAO {

    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    protected void closeQuietly(AutoCloseable... resources) {
        for (AutoCloseable r : resources) {
            if (r != null) {
                try {
                    r.close();
                } catch (Exception e) {
                    // diam
                }
            }
        }
    }

    protected int countRows(String tableName) {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Gagal menghitung baris " + tableName + ": " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return 0;
    }
}
