import java.sql.*;

/**
 * Kelas uji: menyambung ke database dan membaca satu baris dari tabel siswa.
 * Tidak mengubah data, hanya membaca. Jalankan dengan:
 *   java -cp "build/out;lib/*" DatabaseTest
 */
public class DatabaseTest {

    public static void main(String[] args) {
        System.out.println("=== Uji Sambungan Database ===");
        System.out.println();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getInstance().getConnection();
            System.out.println("Sambungan berhasil ke: "
                    + DatabaseConnection.getInstance().getName());

            String sql = "SELECT * FROM siswa ORDER BY id_siswa LIMIT 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            System.out.println();
            System.out.println("=== Baris pertama dari tabel siswa ===");

            if (rs.next()) {
                ResultSetMetaData meta = rs.getMetaData();
                int colCount = meta.getColumnCount();

                for (int i = 1; i <= colCount; i++) {
                    String colName = meta.getColumnName(i);
                    String value = rs.getString(i);
                    System.out.printf("  %-20s : %s%n", colName,
                            value != null ? value : "(NULL)");
                }
            } else {
                System.out.println("  Tabel siswa kosong, tidak ada baris.");
            }

            System.out.println();
            System.out.println("=== Uji selesai, tidak ada data diubah ===");

        } catch (SQLException e) {
            System.err.println("GALAT: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(conn);
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* diam */ }
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* diam */ }
        }
    }
}
