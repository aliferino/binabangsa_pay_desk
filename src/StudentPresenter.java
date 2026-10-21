import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Presenter untuk data siswa.
 */
public class StudentPresenter {

    private final StudentDAO studentDAO = new StudentDAO();

    public interface StudentView {
        void showData(List<Student> students);
        void showMessage(String message, boolean error);
    }

    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }

    public List<Student> searchStudents(String keyword) {
        return studentDAO.search(keyword);
    }

    public boolean saveStudent(Student student) {
        return studentDAO.save(student);
    }

    public boolean updateStudent(Student student) {
        return studentDAO.update(student);
    }

    public Student findById(long idSiswa) {
        return studentDAO.findById(idSiswa);
    }
}
