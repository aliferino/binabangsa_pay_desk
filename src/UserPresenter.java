import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Presenter untuk data pengguna.
 */
public class UserPresenter {
    private final UserDAO userDAO = new UserDAO();

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public boolean save(User user) {
        return userDAO.save(user);
    }

    public boolean update(User user) {
        return userDAO.update(user);
    }
}
