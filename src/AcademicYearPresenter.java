import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

/**
 * Presenter untuk data tahun ajaran.
 */
public class AcademicYearPresenter {
    private final AcademicYearDAO yearDAO = new AcademicYearDAO();

    public List<AcademicYear> getAllYears() {
        return yearDAO.findAll();
    }

    public boolean save(AcademicYear year) {
        return yearDAO.save(year);
    }

    public boolean update(AcademicYear year) {
        return yearDAO.update(year);
    }
}
