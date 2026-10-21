import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * View data kelas: tabel, tambah, ubah.
 */
public class ClassroomView extends JPanel {

    private final ClassroomPresenter presenter = new ClassroomPresenter();
    private final MajorDAO majorDAO = new MajorDAO();
    private final AcademicYearDAO yearDAO = new AcademicYearDAO();
    private JTable table;
    private DefaultTableModel tableModel;

    public ClassroomView() {
        initUI();
        refreshData();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JLabel("Data Kelas"), BorderLayout.NORTH);

        String[] cols = {"ID", "Nama", "Tingkat", "ID Jurusan", "ID Tahun"};
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
        for (Classroom c : presenter.getAllClassrooms()) {
            tableModel.addRow(new Object[]{
                    c.getIdKelas(), c.getNamaKelas(), c.getTingkat(),
                    c.getIdJurusan(), c.getIdTahunAjaran()
            });
        }
    }

    private void onAdd(ActionEvent e) {
        JTextField namaField = new JTextField(10);
        JTextField tingkatField = new JTextField(5);
        JComboBox<String> jurusanCombo = new JComboBox<>();
        for (Major m : majorDAO.findAll()) {
            jurusanCombo.addItem(m.getIdJurusan() + " - " + m.getNamaJurusan());
        }
        JComboBox<String> tahunCombo = new JComboBox<>();
        for (AcademicYear a : yearDAO.findAll()) {
            tahunCombo.addItem(a.getIdTahunAjaran() + " - " + a.getTahunAjaran());
        }

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0; gbc.gridx = 0; panel.add(new JLabel("Nama Kelas:"), gbc);
        gbc.gridx = 1; panel.add(namaField, gbc);
        gbc.gridy = 1; gbc.gridx = 0; panel.add(new JLabel("Tingkat:"), gbc);
        gbc.gridx = 1; panel.add(tingkatField, gbc);
        gbc.gridy = 2; gbc.gridx = 0; panel.add(new JLabel("Jurusan:"), gbc);
        gbc.gridx = 1; panel.add(jurusanCombo, gbc);
        gbc.gridy = 3; gbc.gridx = 0; panel.add(new JLabel("Tahun Ajaran:"), gbc);
        gbc.gridx = 1; panel.add(tahunCombo, gbc);

        if (JOptionPane.showConfirmDialog(this, panel, "Tambah Kelas",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            Classroom c = new Classroom();
            c.setNamaKelas(namaField.getText().trim());
            c.setTingkat(tingkatField.getText().trim());
            String j = (String) jurusanCombo.getSelectedItem();
            if (j != null) c.setIdJurusan(Long.parseLong(j.split(" - ")[0]));
            String t = (String) tahunCombo.getSelectedItem();
            if (t != null) c.setIdTahunAjaran(Long.parseLong(t.split(" - ")[0]));
            presenter.save(c);
            refreshData();
        }
    }
}
