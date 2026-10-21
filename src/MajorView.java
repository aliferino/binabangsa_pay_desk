import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * View data jurusan: tabel, tambah, ubah.
 */
public class MajorView extends JPanel {

    private final MajorPresenter presenter = new MajorPresenter();
    private JTable table;
    private DefaultTableModel tableModel;

    public MajorView() {
        initUI();
        refreshData();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JLabel("Data Jurusan"), BorderLayout.NORTH);

        String[] cols = {"ID", "Kode", "Nama", "Keterangan"};
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
        for (Major m : presenter.getAllMajors()) {
            tableModel.addRow(new Object[]{
                    m.getIdJurusan(), m.getKodeJurusan(),
                    m.getNamaJurusan(), m.getKeterangan()
            });
        }
    }

    private void onAdd(ActionEvent e) {
        JTextField kodeField = new JTextField(10);
        JTextField namaField = new JTextField(20);
        JTextField ketField = new JTextField(20);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0; gbc.gridx = 0; panel.add(new JLabel("Kode:"), gbc);
        gbc.gridx = 1; panel.add(kodeField, gbc);
        gbc.gridy = 1; gbc.gridx = 0; panel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1; panel.add(namaField, gbc);
        gbc.gridy = 2; gbc.gridx = 0; panel.add(new JLabel("Keterangan:"), gbc);
        gbc.gridx = 1; panel.add(ketField, gbc);

        if (JOptionPane.showConfirmDialog(this, panel, "Tambah Jurusan",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            Major m = new Major();
            m.setKodeJurusan(kodeField.getText().trim());
            m.setNamaJurusan(namaField.getText().trim());
            m.setKeterangan(ketField.getText().trim());
            presenter.save(m);
            refreshData();
        }
    }
}
