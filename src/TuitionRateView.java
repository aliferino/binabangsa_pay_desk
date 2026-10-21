import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;

/**
 * View tarif SPP: tabel, tambah, ubah.
 */
public class TuitionRateView extends JPanel {

    private final TuitionRatePresenter presenter = new TuitionRatePresenter();
    private final AcademicYearDAO yearDAO = new AcademicYearDAO();
    private JTable table;
    private DefaultTableModel tableModel;

    public TuitionRateView() {
        initUI();
        refreshData();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JLabel("Tarif SPP"), BorderLayout.NORTH);

        String[] cols = {"ID", "ID Tahun", "Nominal", "Keterangan", "Status"};
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
        for (TuitionRate t : presenter.getAllRates()) {
            tableModel.addRow(new Object[]{
                    t.getIdTarif(), t.getIdTahunAjaran(),
                    t.getNominal(), t.getKeterangan(), t.getStatus()
            });
        }
    }

    private void onAdd(ActionEvent e) {
        JComboBox<String> tahunCombo = new JComboBox<>();
        for (AcademicYear a : yearDAO.findAll()) {
            tahunCombo.addItem(a.getIdTahunAjaran() + " - " + a.getTahunAjaran());
        }
        JTextField nominalField = new JTextField(10);
        JTextField ketField = new JTextField(20);
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"aktif", "nonaktif"});

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0; gbc.gridx = 0; panel.add(new JLabel("Tahun Ajaran:"), gbc);
        gbc.gridx = 1; panel.add(tahunCombo, gbc);
        gbc.gridy = 1; gbc.gridx = 0; panel.add(new JLabel("Nominal:"), gbc);
        gbc.gridx = 1; panel.add(nominalField, gbc);
        gbc.gridy = 2; gbc.gridx = 0; panel.add(new JLabel("Keterangan:"), gbc);
        gbc.gridx = 1; panel.add(ketField, gbc);
        gbc.gridy = 3; gbc.gridx = 0; panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; panel.add(statusCombo, gbc);

        if (JOptionPane.showConfirmDialog(this, panel, "Tambah Tarif SPP",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                TuitionRate t = new TuitionRate();
                String tahunStr = (String) tahunCombo.getSelectedItem();
                if (tahunStr != null) {
                    t.setIdTahunAjaran(Long.parseLong(tahunStr.split(" - ")[0]));
                }
                t.setNominal(new BigDecimal(nominalField.getText().trim()));
                t.setKeterangan(ketField.getText().trim());
                t.setStatus((String) statusCombo.getSelectedItem());
                presenter.save(t);
                refreshData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Input salah: " + ex.getMessage(),
                        "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
