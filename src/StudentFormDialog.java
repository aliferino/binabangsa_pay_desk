import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

/**
 * Dialog form untuk tambah/ubah siswa.
 */
public class StudentFormDialog extends JDialog {

    private final StudentPresenter presenter = new StudentPresenter();
    private final ClassroomDAO classroomDAO = new ClassroomDAO();
    private final AcademicYearDAO yearDAO = new AcademicYearDAO();
    private final Student existing;
    private boolean saved = false;

    private JTextField nisField, nisnField, namaField, tempatLahirField,
            alamatField, noHpField, ortuField;
    private JComboBox<String> kelaminCombo, kelasCombo, tahunCombo, statusCombo;

    public StudentFormDialog(JFrame parent, String title, Student existing) {
        super(parent, title, true);
        this.existing = existing;
        initUI();
        if (existing != null) {
            fillForm(existing);
        }
    }

    private void initUI() {
        setSize(450, 500);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int y = 0;
        gbc.gridy = y; gbc.gridx = 0; form.add(new JLabel("NIS:"), gbc);
        gbc.gridx = 1; nisField = new JTextField(15); form.add(nisField, gbc);
        y++;
        gbc.gridy = y; gbc.gridx = 0; form.add(new JLabel("NISN:"), gbc);
        gbc.gridx = 1; nisnField = new JTextField(15); form.add(nisnField, gbc);
        y++;
        gbc.gridy = y; gbc.gridx = 0; form.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1; namaField = new JTextField(20); form.add(namaField, gbc);
        y++;
        gbc.gridy = y; gbc.gridx = 0; form.add(new JLabel("Jenis Kelamin:"), gbc);
        gbc.gridx = 1; kelaminCombo = new JComboBox<>(new String[]{"L", "P"}); form.add(kelaminCombo, gbc);
        y++;
        gbc.gridy = y; gbc.gridx = 0; form.add(new JLabel("Tempat Lahir:"), gbc);
        gbc.gridx = 1; tempatLahirField = new JTextField(15); form.add(tempatLahirField, gbc);
        y++;
        gbc.gridy = y; gbc.gridx = 0; form.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1; alamatField = new JTextField(20); form.add(alamatField, gbc);
        y++;
        gbc.gridy = y; gbc.gridx = 0; form.add(new JLabel("No HP:"), gbc);
        gbc.gridx = 1; noHpField = new JTextField(15); form.add(noHpField, gbc);
        y++;
        gbc.gridy = y; gbc.gridx = 0; form.add(new JLabel("Nama Orang Tua:"), gbc);
        gbc.gridx = 1; ortuField = new JTextField(20); form.add(ortuField, gbc);
        y++;
        gbc.gridy = y; gbc.gridx = 0; form.add(new JLabel("Kelas:"), gbc);
        gbc.gridx = 1;
        kelasCombo = new JComboBox<>();
        for (Classroom c : classroomDAO.findAll()) {
            kelasCombo.addItem(c.getIdKelas() + " - " + c.getNamaKelas());
        }
        form.add(kelasCombo, gbc);
        y++;
        gbc.gridy = y; gbc.gridx = 0; form.add(new JLabel("Tahun Ajaran:"), gbc);
        gbc.gridx = 1;
        tahunCombo = new JComboBox<>();
        for (AcademicYear a : yearDAO.findAll()) {
            tahunCombo.addItem(a.getIdTahunAjaran() + " - " + a.getTahunAjaran());
        }
        form.add(tahunCombo, gbc);
        y++;
        gbc.gridy = y; gbc.gridx = 0; form.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; statusCombo = new JComboBox<>(new String[]{"aktif", "nonaktif"}); form.add(statusCombo, gbc);

        add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveBtn = new JButton("Simpan");
        saveBtn.addActionListener(this::onSave);
        JButton cancelBtn = new JButton("Batal");
        cancelBtn.addActionListener(e -> dispose());
        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void fillForm(Student s) {
        nisField.setText(s.getNis());
        nisnField.setText(s.getNisn());
        namaField.setText(s.getNama());
        kelaminCombo.setSelectedItem(s.getJenisKelamin());
        tempatLahirField.setText(s.getTempatLahir());
        alamatField.setText(s.getAlamat());
        noHpField.setText(s.getNoHp());
        ortuField.setText(s.getNamaOrangTua());
        statusCombo.setSelectedItem(s.getStatus());
    }

    private void onSave(ActionEvent e) {
        Student s = (existing != null) ? existing : new Student();
        s.setNis(nisField.getText().trim());
        s.setNisn(nisnField.getText().trim());
        s.setNama(namaField.getText().trim());
        s.setJenisKelamin((String) kelaminCombo.getSelectedItem());
        s.setTempatLahir(tempatLahirField.getText().trim());
        s.setAlamat(alamatField.getText().trim());
        s.setNoHp(noHpField.getText().trim());
        s.setNamaOrangTua(ortuField.getText().trim());

        String kelasStr = (String) kelasCombo.getSelectedItem();
        if (kelasStr != null) {
            s.setIdKelas(Long.parseLong(kelasStr.split(" - ")[0]));
        }
        String tahunStr = (String) tahunCombo.getSelectedItem();
        if (tahunStr != null) {
            s.setIdTahunAjaran(Long.parseLong(tahunStr.split(" - ")[0]));
        }
        s.setStatus((String) statusCombo.getSelectedItem());

        boolean ok;
        if (existing != null) {
            ok = presenter.updateStudent(s);
        } else {
            ok = presenter.saveStudent(s);
        }

        if (ok) {
            saved = true;
            JOptionPane.showMessageDialog(this, "Data tersimpan.",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data.",
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() { return saved; }
}
