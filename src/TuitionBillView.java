import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * View tagihan SPP: tabel, tandai tunggakan.
 */
public class TuitionBillView extends JPanel {

    private final TuitionBillPresenter presenter = new TuitionBillPresenter();
    private JTable table;
    private DefaultTableModel tableModel;

    public TuitionBillView() {
        initUI();
        refreshData();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JLabel("Tagihan SPP"), BorderLayout.NORTH);

        String[] cols = {"ID", "ID Siswa", "Bulan", "Tahun", "Nominal", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton tunggakanBtn = new JButton("Tandai Tunggakan");
        tunggakanBtn.addActionListener(this::onMarkTunggakan);
        btnPanel.add(tunggakanBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void refreshData() {
        tableModel.setRowCount(0);
        for (TuitionBill b : presenter.getAllBills()) {
            tableModel.addRow(new Object[]{
                    b.getIdTagihan(), b.getIdSiswa(),
                    b.getBulan(), b.getTahun(),
                    b.getNominal(), b.getStatus()
            });
        }
    }

    private void onMarkTunggakan(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih baris dulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        long id = (long) tableModel.getValueAt(row, 0);
        presenter.updateStatus(id, "belum_bayar");
        JOptionPane.showMessageDialog(this, "Tagihan ditandai tunggakan.",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
        refreshData();
    }
}
