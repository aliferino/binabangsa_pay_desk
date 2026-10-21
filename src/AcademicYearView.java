import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * View data tahun ajaran: tabel, tambah, ubah.
 */
public class AcademicYearView extends JPanel {

    private final AcademicYearPresenter presenter = new AcademicYearPresenter();
    private JTable table;
    private DefaultTableModel tableModel;

    public AcademicYearView() {
        initUI();
        refreshData();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JLabel("Data Tahun Ajaran"), BorderLayout.NORTH);

        String[] cols = {"ID", "Tahun Ajaran", "Mulai", "Selesai", "Status"};
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
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (AcademicYear a : presenter.getAllYears()) {
            tableModel.addRow(new Object[]{
                    a.getIdTahunAjaran(), a.getTahunAjaran(),
                    a.getTanggalMulai().format(fmt),
                    a.getTanggalSelesai().format(fmt),
                    a.getStatus()
            });
        }
    }

    private void onAdd(ActionEvent e) {
        JTextField tahunField = new JTextField(15);
        JTextField mulaiField = new JTextField(10);
        mulaiField.setText("2026-07-01");
        JTextField selesaiField = new JTextField(10);
        selesaiField.setText("2027-06-30");
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"aktif", "nonaktif"});

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0; gbc.gridx = 0; panel.add(new JLabel("Tahun Ajaran:"), gbc);
        gbc.gridx = 1; panel.add(tahunField, gbc);
        gbc.gridy = 1; gbc.gridx = 0; panel.add(new JLabel("Mulai (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; panel.add(mulaiField, gbc);
        gbc.gridy = 2; gbc.gridx = 0; panel.add(new JLabel("Selesai (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; panel.add(selesaiField, gbc);
        gbc.gridy = 3; gbc.gridx = 0; panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; panel.add(statusCombo, gbc);

        if (JOptionPane.showConfirmDialog(this, panel, "Tambah Tahun Ajaran",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                AcademicYear a = new AcademicYear();
                a.setTahunAjaran(tahunField.getText().trim());
                a.setTanggalMulai(LocalDate.parse(mulaiField.getText().trim()));
                a.setTanggalSelesai(LocalDate.parse(selesaiField.getText().trim()));
                a.setStatus((String) statusCombo.getSelectedItem());
                presenter.save(a);
                refreshData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Format tanggal salah: " + ex.getMessage(),
                        "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
