import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * Presenter untuk kepala sekolah.
 * Hanya membaca, tidak menyimpan.
 */
public class HeadmasterPresenter {

    private final StudentDAO studentDAO = new StudentDAO();
    private final TuitionBillDAO billDAO = new TuitionBillDAO();
    private final PaymentDAO paymentDAO = new PaymentDAO();

    public int getTotalSiswa() {
        return studentDAO.findAll().size();
    }

    public int getTotalTunggakan() {
        return billDAO.findAll().stream()
                .filter(b -> "belum_bayar".equals(b.getStatus()))
                .toList()
                .size();
    }

    public int getTotalPembayaranBulanIni() {
        LocalDate now = LocalDate.now();
        return paymentDAO.findAll().stream()
                .filter(p -> p.getCreatedAt() != null
                        && p.getCreatedAt().getMonth() == now.getMonth()
                        && p.getCreatedAt().getYear() == now.getYear())
                .toList()
                .size();
    }

    public BigDecimal getTotalPemasukanBulanIni() {
        LocalDate now = LocalDate.now();
        return paymentDAO.findAll().stream()
                .filter(p -> p.getCreatedAt() != null
                        && p.getCreatedAt().getMonth() == now.getMonth()
                        && p.getCreatedAt().getYear() == now.getYear()
                        && "berhasil".equals(p.getStatus()))
                .map(Payment::getNominalPembayaran)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<MonthlyReport> getMonthlyReport(int year) {
        List<MonthlyReport> reports = new ArrayList<>();
        for (int m = 1; m <= 12; m++) {
            Month month = Month.of(m);
            List<Payment> monthPayments = paymentDAO.findAll().stream()
                    .filter(p -> p.getCreatedAt() != null
                            && p.getCreatedAt().getMonth() == month
                            && p.getCreatedAt().getYear() == year)
                    .toList();
            BigDecimal total = monthPayments.stream()
                    .filter(p -> "berhasil".equals(p.getStatus()))
                    .map(Payment::getNominalPembayaran)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            reports.add(new MonthlyReport(m, month.toString(),
                    monthPayments.size(), total));
        }
        return reports;
    }

    public List<TuitionBill> getTunggakan() {
        return billDAO.findAll().stream()
                .filter(b -> "belum_bayar".equals(b.getStatus()))
                .toList();
    }

    public record MonthlyReport(int month, String monthName,
                                int count, BigDecimal total) {
    }
}
