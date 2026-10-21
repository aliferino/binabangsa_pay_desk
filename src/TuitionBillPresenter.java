import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Presenter untuk tagihan SPP.
 */
public class TuitionBillPresenter {
    private final TuitionBillDAO billDAO = new TuitionBillDAO();

    public List<TuitionBill> getAllBills() {
        return billDAO.findAll();
    }

    public List<TuitionBill> getBillsByStudent(long idSiswa) {
        return billDAO.findByStudent(idSiswa);
    }

    public boolean save(TuitionBill bill) {
        return billDAO.save(bill);
    }

    public boolean updateStatus(long idTagihan, String status) {
        return billDAO.updateStatus(idTagihan, status);
    }
}
