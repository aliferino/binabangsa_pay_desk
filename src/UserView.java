import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * View data pengguna: tabel, tambah, ubah.
 */
public class UserView extends JPanel {

    private final UserPresenter presenter = new UserPresenter();
    private JTable table;
    private DefaultTableModel tableModel;

    public UserView() {
        initUI();
        refreshData();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JLabel("Data Pengguna"), BorderLayout.NORTH);

        String[] cols = {"ID", "Nama", "Username", "Role", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addBtn = new JButton("Tambah");
        addBtn.addActionListener(this::onAdd);
        btnPanel.add(addBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void refreshData() {
        tableModel.setRowCount(0);
        for (User u : presenter.getAllUsers()) {
            tableModel.addRow(new Object[]{
                    u.getIdUser(), u.getNama(), u.getUsername(),
                    u.getRole(), u.getStatus()
            });
        }
    }

    private void onAdd(ActionEvent e) {
        JTextField namaField = new JTextField(20);
        JTextField usernameField = new JTextField(15);
        JPasswordField sandiField = new JPasswordField(15);
        JComboBox<String> roleCombo = new JComboBox<>(
                new String[]{"admin", "teller", "kepala_sekolah"});
        JComboBox<String> statusCombo = new JComboBox<>(
                new String[]{"aktif", "nonaktif"});

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0; gbc.gridx = 0; panel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1; panel.add(namaField, gbc);
        gbc.gridy = 1; gbc.gridx = 0; panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; panel.add(usernameField, gbc);
        gbc.gridy = 2; gbc.gridx = 0; panel.add(new JLabel("Sandi:"), gbc);
        gbc.gridx = 1; panel.add(sandiField, gbc);
        gbc.gridy = 3; gbc.gridx = 0; panel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1; panel.add(roleCombo, gbc);
        gbc.gridy = 4; gbc.gridx = 0; panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; panel.add(statusCombo, gbc);

        if (JOptionPane.showConfirmDialog(this, panel, "Tambah Pengguna",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            User u = new User();
            u.setNama(namaField.getText().trim());
            u.setUsername(usernameField.getText().trim());
            u.setPassword(new String(sandiField.getPassword()));
            u.setRole((String) roleCombo.getSelectedItem());
            u.setStatus((String) statusCombo.getSelectedItem());
            presenter.save(u);
            refreshData();
        }
    }
}
