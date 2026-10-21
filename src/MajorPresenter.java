import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Presenter untuk data jurusan.
 */
public class MajorPresenter {
    private final MajorDAO majorDAO = new MajorDAO();

    public List<Major> getAllMajors() {
        return majorDAO.findAll();
    }

    public Major findById(long id) {
        return majorDAO.findById(id);
    }

    public boolean save(Major major) {
        return majorDAO.save(major);
    }

    public boolean update(Major major) {
        return majorDAO.update(major);
    }
}
