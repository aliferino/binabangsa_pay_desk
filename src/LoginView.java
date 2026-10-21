import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * View login untuk admin.
 * Hanya menyusun komponen dan meneruskan aksi ke presenter.
 */
public class LoginView extends JDialog {

    private final LoginPresenter presenter;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginView(JFrame parent) {
        super(parent, "Login", true);
        this.presenter = new LoginPresenter();
        initUI();
    }

    private void initUI() {
        setSize(350, 200);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Sandi:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);

        add(formPanel, BorderLayout.CENTER);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this::onLogin);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(loginButton);
        add(btnPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(loginButton);
    }

    private void onLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        presenter.login(new LoginPresenter.LoginView() {
            @Override
            public void onLoginSuccess(User user) {
                JOptionPane.showMessageDialog(LoginView.this,
                        "Selamat datang, " + user.getNama()
                                + " (" + user.getRole() + ")",
                        "Login berhasil", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }

            @Override
            public void onLoginFailed(String message) {
                JOptionPane.showMessageDialog(LoginView.this,
                        message, "Login gagal", JOptionPane.ERROR_MESSAGE);
            }
        }, username, password);
    }
}
