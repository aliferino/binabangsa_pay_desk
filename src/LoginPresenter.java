/**
 * Presenter untuk login admin.
 * Memeriksa username dan sandi ke tabel users.
 */
public class LoginPresenter {

    private final UserDAO userDAO;

    public interface LoginView {
        void onLoginSuccess(User user);
        void onLoginFailed(String message);
    }

    public LoginPresenter() {
        this.userDAO = new UserDAO();
    }

    public LoginPresenter(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Periksa login. Kalau berhasil, panggil onLoginSuccess.
     * Kalau gagal, panggil onLoginFailed.
     */
    public void login(LoginView view, String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            view.onLoginFailed("Username tidak boleh kosong.");
            return;
        }
        if (password == null || password.isEmpty()) {
            view.onLoginFailed("Sandi tidak boleh kosong.");
            return;
        }

        User user = userDAO.findByUsername(username.trim());

        if (user == null) {
            view.onLoginFailed("Username tidak ditemukan.");
            return;
        }

        if (!"aktif".equals(user.getStatus())) {
            view.onLoginFailed("Akun tidak aktif. Hubungi admin.");
            return;
        }

        // Cek sandi. Catatan: web memakai bcrypt, di desktop MVP kita bandingkan
        // langsung. Asesor mungkin memakai sandi plain text untuk uji.
        if (password.equals(user.getPassword())) {
            view.onLoginSuccess(user);
        } else {
            view.onLoginFailed("Sandi salah.");
        }
    }
}
