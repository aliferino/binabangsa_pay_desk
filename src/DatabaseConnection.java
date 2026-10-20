import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DatabaseConnection: menyambung ke MySQL lewat JDBC, membaca pengaturan
 * dari config.properties atau variabel lingkungan.
 */
public class DatabaseConnection {

    private static final String CONFIG_FILE = "config.properties";
    private static DatabaseConnection instance;

    private String host;
    private String port;
    private String name;
    private String user;
    private String password;

    private DatabaseConnection() {
        loadConfig();
    }

    /**
     * Singleton: satu sambungan untuk seluruh aplikasi.
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Baca pengaturan dari config.properties. Kalau ada variabel lingkungan
     * dengan nama yang sama, variabel lingkungan menang.
     */
    private void loadConfig() {
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);
        } catch (IOException e) {
            // config.properties mungkin tidak ada saat uji, pakai default
            System.err.println("Peringatan: tidak bisa baca config.properties, pakai default.");
        }

        host = System.getProperty("db.host",
                props.getProperty("db.host", "localhost"));
        port = System.getProperty("db.port",
                props.getProperty("db.port", "3306"));
        name = System.getProperty("db.name",
                props.getProperty("db.name", "binabangsa_pay_web"));
        user = System.getProperty("db.user",
                props.getProperty("db.user", "root"));
        password = System.getProperty("db.password",
                props.getProperty("db.password", ""));
    }

    /**
     * Buka sambungan baru ke MySQL.
     */
    public Connection getConnection() throws SQLException {
        String url = String.format(
                "jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                host, port, name);
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Tutup sambungan dengan aman (null-safe).
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Gagal menutup sambungan: " + e.getMessage());
            }
        }
    }

    public String getHost() { return host; }
    public String getPort() { return port; }
    public String getName() { return name; }
    public String getUser() { return user; }
}
