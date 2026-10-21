import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Presenter untuk data kelas.
 */
public class ClassroomPresenter {
    private final ClassroomDAO classroomDAO = new ClassroomDAO();

    public List<Classroom> getAllClassrooms() {
        return classroomDAO.findAll();
    }

    public boolean save(Classroom classroom) {
        return classroomDAO.save(classroom);
    }

    public boolean update(Classroom classroom) {
        return classroomDAO.update(classroom);
    }
}
