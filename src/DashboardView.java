import javax.swing.*;
import java.awt.*;

/**
 * View dashboard admin.
 * Menampilkan ringkasan jumlah siswa, tagihan belum bayar, pembayaran hari ini.
 */
public class DashboardView extends JPanel {

    private final DashboardPresenter presenter;
    private JLabel lblSiswa, lblTagihan, lblPembayaran;

    public DashboardView() {
        this.presenter = new DashboardPresenter();
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Dashboard Admin", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(22f).deriveFont(Font.BOLD));
        add(title, BorderLayout.NORTH);

        JPanel stats = new JPanel(new GridLayout(1, 3, 20, 0));

        lblSiswa = createStatCard("Total Siswa", "0");
        lblTagihan = createStatCard("Tagihan Belum Bayar", "0");
        lblPembayaran = createStatCard("Pembayaran Hari Ini", "0");

        stats.add(wrapCard(lblSiswa));
        stats.add(wrapCard(lblTagihan));
        stats.add(wrapCard(lblPembayaran));

        add(stats, BorderLayout.CENTER);
    }

    private JLabel createStatCard(String label, String value) {
        JLabel l = new JLabel("<html><div style='text-align:center;'>"
                + "<b>" + label + "</b><br><br>"
                + "<font size='6'>" + value + "</font>"
                + "</div></html>", SwingConstants.CENTER);
        l.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        return l;
    }

    private JPanel wrapCard(JLabel label) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        p.add(label, BorderLayout.CENTER);
        return p;
    }

    public void refresh() {
        presenter.loadSummary(new DashboardPresenter.DashboardView() {
            @Override
            public void showSummary(int totalSiswa, int tagihanBelumBayar, int pembayaranHariIni) {
                SwingUtilities.invokeLater(() -> {
                    lblSiswa.setText("<html><div style='text-align:center;'>"
                            + "<b>Total Siswa</b><br><br>"
                            + "<font size='6'>" + totalSiswa + "</font>"
                            + "</div></html>");
                    lblTagihan.setText("<html><div style='text-align:center;'>"
                            + "<b>Tagihan Belum Bayar</b><br><br>"
                            + "<font size='6'>" + tagihanBelumBayar + "</font>"
                            + "</div></html>");
                    lblPembayaran.setText("<html><div style='text-align:center;'>"
                            + "<b>Pembayaran Hari Ini</b><br><br>"
                            + "<font size='6'>" + pembayaranHariIni + "</font>"
                            + "</div></html>");
                });
            }
        });
    }
}
