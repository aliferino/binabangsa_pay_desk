import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * View kepala sekolah: dashboard ringkasan, laporan pembayaran per bulan,
 * laporan tunggakan, grafik sederhana pemasukan per bulan.
 * Tidak ada tombol tambah, ubah, atau hapus.
 */
public class HeadmasterDashboardView extends JPanel {

    private final HeadmasterPresenter presenter = new HeadmasterPresenter();

    public HeadmasterDashboardView() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Dashboard Kepala Sekolah");
        title.setFont(title.getFont().deriveFont(18f).deriveFont(Font.BOLD));
        add(title, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Ringkasan", createSummaryPanel());
        tabs.addTab("Laporan Pembayaran", createMonthlyReportPanel());
        tabs.addTab("Laporan Tunggakan", createTunggakanPanel());
        tabs.addTab("Grafik Pemasukan", createChartPanel());

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        int totalSiswa = presenter.getTotalSiswa();
        int totalTunggakan = presenter.getTotalTunggakan();
        int pembayaranBulan = presenter.getTotalPembayaranBulanIni();
        BigDecimal pemasukan = presenter.getTotalPemasukanBulanIni();

        panel.add(createInfoLabel("Total Siswa", String.valueOf(totalSiswa)));
        panel.add(createInfoLabel("Total Tunggakan", String.valueOf(totalTunggakan)));
        panel.add(createInfoLabel("Pembayaran Bulan Ini",
                pembayaranBulan + " (Rp " + pemasukan + ")"));

        return panel;
    }

    private JPanel createMonthlyReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Tahun:"));
        JTextField yearField = new JTextField(
                String.valueOf(java.time.LocalDate.now().getYear()), 5);
        topPanel.add(yearField);
        JButton loadBtn = new JButton("Muat");
        topPanel.add(loadBtn);
        panel.add(topPanel, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Bulan", "Jumlah Pembayaran", "Total Pemasukan"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        loadBtn.addActionListener(e -> {
            model.setRowCount(0);
            int year = Integer.parseInt(yearField.getText().trim());
            for (HeadmasterPresenter.MonthlyReport r : presenter.getMonthlyReport(year)) {
                model.addRow(new Object[]{
                        r.monthName(), r.count(), "Rp " + r.total()
                });
            }
        });

        return panel;
    }

    private JPanel createTunggakanPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(new JLabel("Daftar Tunggakan"), BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID Tagihan", "ID Siswa", "Bulan", "Tahun",
                        "Nominal", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        for (TuitionBill b : presenter.getTunggakan()) {
            model.addRow(new Object[]{
                    b.getIdTagihan(), b.getIdSiswa(),
                    b.getBulan(), b.getTahun(),
                    b.getNominal(), b.getStatus()
            });
        }
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createChartPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(new JLabel("Grafik Pemasukan per Bulan"), BorderLayout.NORTH);

        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(
                        java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();
                int margin = 50;
                int barWidth = (w - 2 * margin) / 12;

                int year = java.time.LocalDate.now().getYear();
                List<HeadmasterPresenter.MonthlyReport> reports =
                        presenter.getMonthlyReport(year);

                BigDecimal max = reports.stream()
                        .map(HeadmasterPresenter.MonthlyReport::total)
                        .max(BigDecimal::compareTo)
                        .orElse(BigDecimal.ONE);

                g2d.setColor(Color.BLACK);
                g2d.drawLine(margin, h - margin, w - margin, h - margin);
                g2d.drawLine(margin, margin, margin, h - margin);

                for (int i = 0; i < reports.size(); i++) {
                    HeadmasterPresenter.MonthlyReport r = reports.get(i);
                    int barHeight = 0;
                    if (max.compareTo(BigDecimal.ZERO) > 0) {
                        barHeight = (int) (r.total().doubleValue()
                                / max.doubleValue()
                                * (h - 2 * margin));
                    }
                    int x = margin + i * barWidth + 5;
                    int y = h - margin - barHeight;
                    g2d.setColor(new Color(70, 130, 180));
                    g2d.fillRect(x, y, barWidth - 10, barHeight);
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(String.valueOf(i + 1), x + 5, h - margin + 15);
                }
            }
        };
        chartPanel.setPreferredSize(new Dimension(600, 300));
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }

    private JLabel createInfoLabel(String label, String value) {
        JLabel l = new JLabel("<html><b>" + label + "</b>: "
                + "<font size='5'>" + value + "</font></html>");
        l.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        l.setHorizontalAlignment(SwingConstants.CENTER);
        return l;
    }
}
