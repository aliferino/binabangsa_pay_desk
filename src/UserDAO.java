import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk tabel users.
 */
public class UserDAO extends BaseDAO {

    private static final String TABLE = "users";

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE + " ORDER BY id_user";
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
            System.err.println("Gagal findAll users: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return list;
    }

    public User findById(long idUser) {
        String sql = "SELECT * FROM " + TABLE + " WHERE id_user = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, idUser);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("Gagal findById users: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return null;
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM " + TABLE + " WHERE username = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("Gagal findByUsername: " + e.getMessage());
        } finally {
            closeQuietly(rs, ps, conn);
        }
        return null;
    }

    public boolean save(User user) {
        String sql = "INSERT INTO " + TABLE
                + " (nama, username, password, role, status) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet keys = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getNama());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getStatus());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    user.setIdUser(keys.getLong(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Gagal save user: " + e.getMessage());
        } finally {
            closeQuietly(keys, ps, conn);
        }
        return false;
    }

    public boolean update(User user) {
        String sql = "UPDATE " + TABLE
                + " SET nama=?, username=?, password=?, role=?, status=? WHERE id_user=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getNama());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getStatus());
            ps.setLong(6, user.getIdUser());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal update user: " + e.getMessage());
        } finally {
            closeQuietly(null, ps, conn);
        }
        return false;
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setIdUser(rs.getLong("id_user"));
        u.setNama(rs.getString("nama"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setRole(rs.getString("role"));
        u.setStatus(rs.getString("status"));
        return u;
    }
}
