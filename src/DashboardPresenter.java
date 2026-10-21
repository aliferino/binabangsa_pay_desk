/**
 * Presenter untuk dashboard admin.
 * Mengambil ringkasan jumlah siswa, tagihan belum bayar, pembayaran hari ini.
 */
public class DashboardPresenter {

    private final StudentDAO studentDAO;
    private final TuitionBillDAO billDAO;
    private final PaymentDAO paymentDAO;

    public interface DashboardView {
        void showSummary(int totalSiswa, int tagihanBelumBayar, int pembayaranHariIni);
    }

    public DashboardPresenter() {
        this.studentDAO = new StudentDAO();
        this.billDAO = new TuitionBillDAO();
        this.paymentDAO = new PaymentDAO();
    }

    public void loadSummary(DashboardView view) {
        int totalSiswa = studentDAO.findAll().size();
        int tagihanBelumBayar = countUnpaidBills();
        int pembayaranHariIni = countTodayPayments();
        view.showSummary(totalSiswa, tagihanBelumBayar, pembayaranHariIni);
    }

    private int countUnpaidBills() {
        return billDAO.findAll().stream()
                .filter(b -> "belum_bayar".equals(b.getStatus()))
                .toList()
                .size();
    }

    private int countTodayPayments() {
        return paymentDAO.findAll().stream()
                .filter(p -> p.getCreatedAt() != null
                        && p.getCreatedAt().toLocalDate().equals(java.time.LocalDate.now()))
                .toList()
                .size();
    }
}
