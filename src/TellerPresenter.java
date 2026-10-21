import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Presenter untuk teller.
 * Mengelola pencarian siswa, tagihan, dan pencatatan pembayaran tunai dan QRIS.
 */
public class TellerPresenter {

    private final StudentDAO studentDAO = new StudentDAO();
    private final TuitionBillDAO billDAO = new TuitionBillDAO();
    private final PaymentDAO paymentDAO = new PaymentDAO();

    public List<Student> searchStudents(String keyword) {
        return studentDAO.search(keyword);
    }

    public Student findStudentById(long idSiswa) {
        return studentDAO.findById(idSiswa);
    }

    public List<TuitionBill> getBillsByStudent(long idSiswa) {
        return billDAO.findByStudent(idSiswa);
    }

    public List<Payment> getPaymentsByTeller(long userId) {
        return paymentDAO.findByTeller(userId);
    }

    public List<Payment> getAllTodayPayments() {
        return paymentDAO.findAll().stream()
                .filter(p -> p.getCreatedAt() != null
                        && p.getCreatedAt().toLocalDate().equals(LocalDate.now()))
                .toList();
    }

    /**
     * Catat pembayaran. Aturan:
     * - nomor_transaksi wajib unik.
     * - tagihan sudah lunas ditolak.
     * - user_id diisi id teller yang mencatat.
     * - Status tagihan diubah jadi lunas setelah pembayaran berhasil.
     */
    public PaymentResult recordPayment(long tagihanSppId, long siswaId,
                                       BigDecimal nominal, String metode,
                                       long userId, String nomorReferensi) {
        // Cek apakah nomor_transaksi sudah ada
        String nomorTransaksi = generateNomorTransaksi();
        if (paymentDAO.isNomorTransaksiExists(nomorTransaksi)) {
            return new PaymentResult(false, "Nomor transaksi sudah ada, coba lagi.", null);
        }

        // Cek tagihan
        TuitionBill bill = billDAO.findById(tagihanSppId);
        if (bill == null) {
            return new PaymentResult(false, "Tagihan tidak ditemukan.", null);
        }

        if ("lunas".equals(bill.getStatus())) {
            return new PaymentResult(false,
                    "Tagihan sudah lunas. Tidak bisa mencatat pembayaran kedua.", null);
        }

        // Simpan pembayaran
        Payment payment = new Payment();
        payment.setNomorTransaksi(nomorTransaksi);
        payment.setTagihanSppId(tagihanSppId);
        payment.setSiswaId(siswaId);
        payment.setNominalPembayaran(nominal);
        payment.setMetodePembayaran(metode);
        payment.setStatus("berhasil");
        payment.setUserId(userId);
        payment.setNomorReferensi(nomorReferensi);

        boolean saved = paymentDAO.save(payment);
        if (!saved) {
            return new PaymentResult(false, "Gagal menyimpan pembayaran.", null);
        }

        // Ubah status tagihan jadi lunas
        billDAO.updateStatus(tagihanSppId, "lunas");

        return new PaymentResult(true, "Pembayaran berhasil dicatat.", payment);
    }

    private String generateNomorTransaksi() {
        return "TRX" + System.currentTimeMillis();
    }

    public record PaymentResult(boolean success, String message, Payment payment) {
    }
}
