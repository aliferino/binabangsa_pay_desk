import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;

/**
 * View dashboard teller: ringkasan pembayaran hari ini, cari siswa,
 * daftar tagihan siswa, catat pembayaran tunai dan QRIS, riwayat pembayaran.
 */
public class TellerDashboardView extends JPanel {

    private final TellerPresenter presenter = new TellerPresenter();
    private final long tellerUserId;

    private JTextField searchField;
    private JTable studentTable, billTable, paymentTable;
    private DefaultTableModel studentModel, billModel, paymentModel;
    private JLabel lblTodayCount, lblTodayTotal;

    public TellerDashboardView(long tellerUserId) {
        this.tellerUserId = tellerUserId;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Dashboard Teller");
        title.setFont(title.getFont().deriveFont(18f).deriveFont(Font.BOLD));
        add(title, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Cari Siswa", createSearchPanel());
        tabs.addTab("Catat Pembayaran", createPaymentPanel());
        tabs.addTab("Riwayat Pembayaran", createHistoryPanel());

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Cari (NIS, NISN, atau Nama):"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchBtn = new JButton("Cari");
        searchBtn.addActionListener(e -> searchStudents());
        searchPanel.add(searchBtn);
        panel.add(searchPanel, BorderLayout.NORTH);

        studentModel = new DefaultTableModel(
                new String[]{"ID", "NIS", "NISN", "Nama", "Kelas"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        studentTable = new JTable(studentModel);
        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showStudentBills();
                }
            }
        });
        panel.add(new JScrollPane(studentTable), BorderLayout.CENTER);

        JPanel billPanel = new JPanel(new BorderLayout(5, 5));
        billPanel.add(new JLabel("Tagihan Siswa (klik dua kali baris siswa di atas):"),
                BorderLayout.NORTH);
        billModel = new DefaultTableModel(
                new String[]{"ID Tagihan", "Bulan", "Tahun", "Nominal", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        billTable = new JTable(billModel);
        billPanel.add(new JScrollPane(billTable), BorderLayout.CENTER);
        panel.add(billPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;
        gbc.gridy = y; gbc.gridx = 0; panel.add(new JLabel("ID Tagihan:"), gbc);
        gbc.gridx = 1;
        JTextField idTagihanField = new JTextField(10);
        panel.add(idTagihanField, gbc);
        y++;
        gbc.gridy = y; gbc.gridx = 0; panel.add(new JLabel("ID Siswa:"), gbc);
        gbc.gridx = 1;
        JTextField idSiswaField = new JTextField(10);
        panel.add(idSiswaField, gbc);
        y++;
        gbc.gridy = y; gbc.gridx = 0; panel.add(new JLabel("Nominal:"), gbc);
        gbc.gridx = 1;
        JTextField nominalField = new JTextField(10);
        panel.add(nominalField, gbc);
        y++;
        gbc.gridy = y; gbc.gridx = 0; panel.add(new JLabel("Metode:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> metodeCombo = new JComboBox<>(new String[]{"tunai", "qris"});
        panel.add(metodeCombo, gbc);
        y++;
        gbc.gridy = y; gbc.gridx = 0; panel.add(new JLabel("Nomor Referensi (QRIS):"), gbc);
        gbc.gridx = 1;
        JTextField refField = new JTextField(15);
        panel.add(refField, gbc);
        y++;
        gbc.gridy = y; gbc.gridx = 0; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton tunaiBtn = new JButton("Catat Pembayaran TUNAI");
        tunaiBtn.addActionListener(e -> {
            metodeCombo.setSelectedItem("tunai");
            recordPayment(idTagihanField, idSiswaField, nominalField,
                    metodeCombo, refField);
        });
        JButton qrisBtn = new JButton("Catat Pembayaran QRIS");
        qrisBtn.addActionListener(e -> {
            metodeCombo.setSelectedItem("qris");
            recordPayment(idTagihanField, idSiswaField, nominalField,
                    metodeCombo, refField);
        });
        btnPanel.add(tunaiBtn);
        btnPanel.add(qrisBtn);
        panel.add(btnPanel, gbc);

        return panel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel summary = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblTodayCount = new JLabel("Pembayaran hari ini: 0");
        lblTodayTotal = new JLabel("");
        summary.add(lblTodayCount);
        summary.add(lblTodayTotal);
        panel.add(summary, BorderLayout.NORTH);

        paymentModel = new DefaultTableModel(
                new String[]{"ID", "Nomor Transaksi", "ID Siswa",
                        "Nominal", "Metode", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        paymentTable = new JTable(paymentModel);
        panel.add(new JScrollPane(paymentTable), BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Muat Ulang");
        refreshBtn.addActionListener(e -> refreshHistory());
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(refreshBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void searchStudents() {
        studentModel.setRowCount(0);
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) return;
        for (Student s : presenter.searchStudents(keyword)) {
            studentModel.addRow(new Object[]{
                    s.getIdSiswa(), s.getNis(), s.getNisn(),
                    s.getNama(), s.getIdKelas()
            });
        }
    }

    private void showStudentBills() {
        int row = studentTable.getSelectedRow();
        if (row < 0) return;
        long idSiswa = (long) studentModel.getValueAt(row, 0);
        billModel.setRowCount(0);
        for (TuitionBill b : presenter.getBillsByStudent(idSiswa)) {
            billModel.addRow(new Object[]{
                    b.getIdTagihan(), b.getBulan(), b.getTahun(),
                    b.getNominal(), b.getStatus()
            });
        }
    }

    private void recordPayment(JTextField idTagihanField, JTextField idSiswaField,
                               JTextField nominalField,
                               JComboBox<String> metodeCombo,
                               JTextField refField) {
        try {
            long tagihanId = Long.parseLong(idTagihanField.getText().trim());
            long siswaId = Long.parseLong(idSiswaField.getText().trim());
            BigDecimal nominal = new BigDecimal(nominalField.getText().trim());
            String metode = (String) metodeCombo.getSelectedItem();
            String ref = refField.getText().trim();
            if ("qris".equals(metode) && ref.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nomor referensi wajib diisi untuk QRIS.",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            TellerPresenter.PaymentResult result = presenter.recordPayment(
                    tagihanId, siswaId, nominal, metode, tellerUserId, ref);
            if (result.success()) {
                JOptionPane.showMessageDialog(this, result.message(),
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, result.message(),
                        "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Input angka tidak valid: " + ex.getMessage(),
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshHistory() {
        paymentModel.setRowCount(0);
        List<Payment> payments = presenter.getPaymentsByTeller(tellerUserId);
        for (Payment p : payments) {
            paymentModel.addRow(new Object[]{
                    p.getId(), p.getNomorTransaksi(), p.getSiswaId(),
                    p.getNominalPembayaran(), p.getMetodePembayaran(),
                    p.getStatus()
            });
        }
        lblTodayCount.setText("Pembayaran hari ini: " + payments.size());
    }
}
