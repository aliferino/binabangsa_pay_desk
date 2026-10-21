import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

/**
 * View data siswa: tabel, tambah, ubah, hapus, cari.
 */
public class StudentView extends JPanel {

    private final StudentPresenter presenter;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public StudentView() {
        this.presenter = new StudentPresenter();
        initUI();
        refreshData();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Data Siswa");
        title.setFont(title.getFont().deriveFont(18f).deriveFont(Font.BOLD));
        add(title, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Cari:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchBtn = new JButton("Cari");
        searchBtn.addActionListener(e -> refreshData());
        searchPanel.add(searchBtn);
        topPanel.add(searchPanel, BorderLayout.WEST);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addBtn = new JButton("Tambah");
        addBtn.addActionListener(this::onAdd);
        JButton editBtn = new JButton("Ubah");
        editBtn.addActionListener(this::onEdit);
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        topPanel.add(btnPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.SOUTH);

        String[] cols = {"ID", "NIS", "NISN", "Nama", "L/P", "Kelas", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void refreshData() {
        tableModel.setRowCount(0);
        String keyword = searchField.getText().trim();
        List<Student> students;
        if (keyword.isEmpty()) {
            students = presenter.getAllStudents();
        } else {
            students = presenter.searchStudents(keyword);
        }
        for (Student s : students) {
            tableModel.addRow(new Object[]{
                    s.getIdSiswa(), s.getNis(), s.getNisn(),
                    s.getNama(), s.getJenisKelamin(),
                    s.getIdKelas(), s.getStatus()
            });
        }
    }

    private void onAdd(ActionEvent e) {
        StudentFormDialog dialog = new StudentFormDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this), "Tambah Siswa", null);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            refreshData();
        }
    }

    private void onEdit(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih baris dulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        long id = (long) tableModel.getValueAt(row, 0);
        Student student = presenter.findById(id);
        if (student == null) {
            JOptionPane.showMessageDialog(this, "Siswa tidak ditemukan.",
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }
        StudentFormDialog dialog = new StudentFormDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this), "Ubah Siswa", student);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            refreshData();
        }
    }
}
