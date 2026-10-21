import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;

/**
 * Presenter untuk tarif SPP.
 */
public class TuitionRatePresenter {
    private final TuitionRateDAO rateDAO = new TuitionRateDAO();

    public List<TuitionRate> getAllRates() {
        return rateDAO.findAll();
    }

    public boolean save(TuitionRate rate) {
        return rateDAO.save(rate);
    }

    public boolean update(TuitionRate rate) {
        return rateDAO.update(rate);
    }
}
